
/***********************************************************************************************************
* Project: PSPS System Two Altimeter
* File: main.cpp
* Created On: 1/7/2019
* Description: This is a redesign for the old integrated Flight Computer System providing only the funciton
* of the Altimeter. This new redesign Uses the BMP280 Altitude Sensor and the Catalex MicroSD Adapter. This
* code is designed to run on either an Arduino Uno or an Arduino Nano. This new design is meant to be
* expandable and should allow for an I2C connection to a master Device to connect multiple similar
* devices for Acceleration, GPS, or other systems.
* Programmers: Samuel Hild          shild@purdue.edu
************************************************************************************************************/

// External Libraries
#include <Arduino.h>
#include <SD.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_BMP280.h>        //  Note: this particular Library (Adafruit_BMP280) must be downloaded Directly from adafruit's github,
                                    //  The version platformio installs does not work
#include <Time.h>
#include <SPI.h>
#include <Wire.h>

#include "main.h"

//Define Preprocessor Constants
#define BMP_CS 10                   // CS/SS Pin for the BMP280
#define SD_CS 4                     // CS/SS Pin for the SDcard

// Note: for all other spi pins the Default hardware spi values are used, For a typical Arduino these are:
// SCK: 13
// MISO/SDO: 12
// MOSI/SD1: 11

#define ERROR_LED 9
#define LOOP_LED 8
#define LOOP_STOP 7

#define DROUGE_FET 2
#define DROUGE_CONT 5
#define MAIN_FET 3
#define MAIN_CONT 6

#define STANDARD_PRESSURE 1013.25
#define ERRANT_PRESSURE_READING 60373.94

#define DATA_FILENAME "DATA.TXT"
#define LOG_FILENAME "LOG.TXT"

#define halt for(;;)

/***********************************************************************************************************
* Class: BmpAltimeter
* Created On: 1/12/2019
* Description: Implementation of the Altimeter Abstract Class for the Bmp280 Sensor
************************************************************************************************************/
class BmpAltimeter : public Altimeter
{
private:
  float seaLevelPressure;
  float velocityDelay = 0;

  // This is an unfortanate workaround to get the compiler to allow me to keep the bmp instance private to this class
  // even though this is not the recommended use I actually think it works pretty well
  // Oh the things you'll do for oop
  Adafruit_BMP280 *bmp;

  void flushData()
  {
    bmp->readPressure();
    bmp->readTemperature();
    bmp->readAltitude(seaLevelPressure);
  }

  bool verifyData()
  {
    return (readPressure() != ERRANT_PRESSURE_READING || readAltitude() != NAN);
  }

  bool testSensor()
  {
    float testPressure = readPressure();
    return (verifyData() && testPressure > (seaLevelPressure*1.0005) && testPressure < (seaLevelPressure*0.9995));
  }

public:
  BmpAltimeter (int cs, float seaLevel = STANDARD_PRESSURE)
  {
    seaLevelPressure = seaLevel;
    bmp = new Adafruit_BMP280(cs);
  }

  bool begin()
  {
    if (!bmp->begin())
      return false;

    while(!testSensor())
    {
      error("Initial Value Error, waiting for sensor to start");
      delay(150);
    }
    return true;
  }

  float readPressure()
  {
    flushData();
    return bmp->readPressure();
  }

  float readAltitude()
  {
    flushData();
    return bmp->readAltitude(seaLevelPressure);
  }

  float readTemperature()
  {
    flushData();
    return bmp->readTemperature();
  }

  float calculateVelocity()
  {
    float deltaAltitude = readAltitude();
    float deltaTime = millis();

    delay (velocityDelay);

    deltaAltitude += readAltitude() - deltaAltitude;
    deltaTime = (millis() - deltaTime)/1000;

    return deltaAltitude/deltaTime;
  }

  void configSensor(AltimeterConfig config)
  {
    switch (config)
    {
      case HIGH_SPEED:
        bmp->setSampling(Adafruit_BMP280::MODE_NORMAL,
                        Adafruit_BMP280::SAMPLING_X1,
                        Adafruit_BMP280::SAMPLING_X1,
                        Adafruit_BMP280::FILTER_OFF,
                        Adafruit_BMP280::STANDBY_MS_1);
        velocityDelay = 5;
        break;

      case BALANCED:
        bmp->setSampling(Adafruit_BMP280::MODE_NORMAL,
                        Adafruit_BMP280::SAMPLING_X2,
                        Adafruit_BMP280::SAMPLING_X8,
                        Adafruit_BMP280::FILTER_X8,
                        Adafruit_BMP280::STANDBY_MS_1);
        velocityDelay = 10;
        break;

      case HIGH_PERCISION:
        bmp->setSampling(Adafruit_BMP280::MODE_NORMAL,
                        Adafruit_BMP280::SAMPLING_X2,
                        Adafruit_BMP280::SAMPLING_X16,
                        Adafruit_BMP280::FILTER_X16,
                        Adafruit_BMP280::STANDBY_MS_63);

        velocityDelay = 15;
        break;
      default:
        fatal("unknown altimeter setting");
        break;
    }
  }
};

class SdStorage : public Storage
{
private:
  File workingFile;
  bool active = false;

  void writeHeader()
  {
    workingFile.println("time (ms),Altitude (m),Pressure (pa),Tempurature (C),Vertical Velocity (m/s), Main continuity (1/0), Drouge continuity (1/0), Free Ram (bytes)");
  }

public:
  SdStorage(int cs)
  {
    pinMode(cs, OUTPUT);

    if (!SD.begin(cs))
      fatal("failed to open sd card");
    else
      active = true;
  }

  void open(const char *filename)
  {
    workingFile = SD.open(filename, FILE_WRITE);

    if (!workingFile)
    {
      fatal("failed to open file");
      active = false;
    }
    else
      writeHeader();
  }

  void write (const char *data)
  {
    if(active)
      workingFile.println(data);
    else
      Serial.println("[@@] warn: inactive sd card");
  }

  void close ()
  {
    workingFile.close();
  }

  bool isActive()
  {
    return active;
  }
};

class SolidStateStorage : public Storage{
private:
  // TODO when I have all my parts
public:
};

class I2cMaster{
private:
  int slaveAddress;
};

// global raw sensor data and system information
float altitude;
float pressure;
float tempurature;
float velocity;

// global hardware objects
Altimeter *altimeter;
Storage *primaryStorage;
Storage *secondaryStorage;

// value for current runmode
Runmode activeRunmode;

/***********************************************************************************************************
* Function: setup()
* Created On: 1/7/2019
* Description: setup function for arduino, sets up all initial sensor, display, flight controls and storage
* devices to prepare for flight. finalizes by verifying that every device is operating properly.
************************************************************************************************************/
void setup()
{
  Serial.begin(9600);
}

/***********************************************************************************************************
* Function: loop()
* Created On: 1/7/2019
* Description: loop function for arduino, contains the majority of the flight code. Operations in the Loop
* function are controlled by the Runmode enum which affects which operations are performed each loop. this
* prevents improper actions from being performed at the wrong times such as chute deployment on the ground.
************************************************************************************************************/
void loop()
{
  digitalWrite(LOOP_LED, HIGH);

  altitude = altimeter->readAltitude();
  pressure = altimeter->readPressure();
  tempurature = altimeter->readTemperature();
  velocity = altimeter->calculateVelocity();

  runmodeAction(activeRunmode);

  writeData(altitude, pressure, tempurature, velocity);

  if (digitalRead(LOOP_STOP))
  {
    Serial.println("[@@] halt signal received, halting opperation.");
    halt;
  }
}

/***********************************************************************************************************
* Function: loop()
* Created On: 1/14/2019
* Description:
************************************************************************************************************/
Runmode runmodeAction(Runmode activeRunmode)
{
  switch (activeRunmode)
  {
    case SETUP:
      fatal("setup improperly finalized!");
      break;

    case PREFLIGHT:
      // TODO Check for takeoff conditions
      break;

    case BURN:
      break;              // Left empty to run at the highest sample rate

    case ASCENT:
      // TODO Apoogee & descent detection
      break;

    case DESCENT:
      // TODO Deploy DROUGE & detect 1500 ft and deploy main at 1000 ft
      break;

    case POSTFLIGHT:
      // Loop led is blinked to make for a safer recovery
      // This allows the recovery team to know that the charges are not armed
      // It also significantly reduces the sample rate which allows the system
      // to save power post flight

      // Disables all deployment charges
      digitalWrite(DROUGE_FET, LOW);
      digitalWrite(MAIN_FET, LOW);

      //blinks led
      digitalWrite(LOOP_LED, LOW);
      delay(500);

      digitalWrite(LOOP_LED, HIGH);
      delay(500);
      break;

    default:
      fatal("Runmode Error: unknown runmode");
      break;
  }

  return activeRunmode;
}

/***********************************************************************************************************
* Function: setupAltimeter()
* Created On: 1/13/2019
* Description: Creates and returns a new instance of BmpAltimeter fully configured for flight.
************************************************************************************************************/
Altimeter *setupAltimeter()
{
  pinMode(BMP_CS, OUTPUT);
  Altimeter *newBmp = new BmpAltimeter(BMP_CS, STANDARD_PRESSURE);

  if(!(newBmp->begin()))
    fatal("Failed to Start Altimeter");

  newBmp->configSensor(BmpAltimeter::HIGH_SPEED);

  return newBmp;
}

/***********************************************************************************************************
* Function: setupPrimaryStorage()
* Created On: 1/14/2019
* Description: Creates and returns a new instance of SdStorage fully configured for flight.
************************************************************************************************************/
Storage *setupPrimaryStorage()
{
  Storage *newPrimary = new SdStorage(SD_CS);

  if (newPrimary->isActive())
    newPrimary->open(DATA_FILENAME);

  return newPrimary;
}

/***********************************************************************************************************
* Function: error()
* Created On: 1/13/2019
* Description: displays a nonfatal error message.
************************************************************************************************************/
void error(const char *message)
{
  Serial.print("[!!] err:");
  Serial.println(message);

  digitalWrite(ERROR_LED, HIGH);
  delay (100);
  digitalWrite(ERROR_LED, LOW);
}

/***********************************************************************************************************
* Function: fatal()
* Created On: 1/13/2019
* Description: displays a fatal error message and halts opperation until loop button is pressed.
************************************************************************************************************/
void fatal(const char *message)
{
    Serial.print("[!!] fatal: ");
    Serial.println(message);
    Serial.println("[!!] halting operation!");

    digitalWrite(ERROR_LED, HIGH);
    digitalWrite(LOOP_LED, LOW);

    delay (1000);

    halt
    {
      if (digitalRead(LOOP_STOP))
      {
        Serial.println("[@@] interrupt detected: resuming operation");
        digitalWrite(ERROR_LED, HIGH);
        break;
      }
    }
}
