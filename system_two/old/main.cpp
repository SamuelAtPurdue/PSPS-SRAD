/*
Altimeter Module
Created by: Samuel Hild
Created on: 12/09/18
*/

#include <Arduino.h>
#include <SPI.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_BMP280.h>
#include <SD.h>
#include <Time.h>

//Define pins
#define BMP_CS 10
#define SD_CS 4

#define ERROR_LED 9
#define LOOP_LED 8
#define LOOP_STOP 7

//Constants
const char * DATA_FILE = "DATA.TXT";
const char * LOG_FILE = "LOG.TXT";

//Global Vars
Adafruit_BMP280 bmp(BMP_CS);
File logFile;
File dataFile;

//Declared Global for simplicity and to minimize extranious variable declaration
float altitude;
float pressure;
float tempurature;
float velocity;

//Initial Pressure value retreived from ATIS/AWOS converted to hpa
float kollsman = 1013.25;     //Standard Pressure: 1013.25

//functions
float readVelocity ();
void write ();
void error (String);
void fatal ();
// void log(const char *);

//Runmodes
enum Runmodes{SETUP, PRELAUNCH, BURN, COAST, DROUGE, MAIN, RECOVERY};
Runmodes currentRunmode;

//setup
void setup(){
  currentRunmode = SETUP;

  //Activate Serial for Debug
  Serial.begin(9600);
  Serial.println("Begin Setup: ");

  //Activate GPIO pins
  Serial.println("Starting GPIO Pins");
  pinMode(ERROR_LED, OUTPUT);
  pinMode(LOOP_LED, OUTPUT);
  pinMode(LOOP_STOP, INPUT);

  Serial.print("RAM: ");
  Serial.println(FreeRam());

  //Starting Sensors
  Serial.println("Starting BMP Sensor");
  pinMode(BMP_CS, OUTPUT);
  if (!bmp.begin()){
    Serial.println("[!!] err: failed to open BMP Sensor");
    fatal ();
  }

  bmp.setSampling(Adafruit_BMP280::MODE_NORMAL,   //MODE
                  Adafruit_BMP280::SAMPLING_X2,   //SAMPLING TEMP
                  Adafruit_BMP280::SAMPLING_X16,  //SAMPLING PRES
                  Adafruit_BMP280::FILTER_X16,    //FILTER
                  Adafruit_BMP280::STANDBY_MS_63);

  Serial.print("RAM: ");
  Serial.println(FreeRam());

  Serial.println("Starting SD Card");
  pinMode(SD_CS, OUTPUT);

  if (!SD.begin(SD_CS)){
    Serial.println("[!!] err: SD card failed to open");
    fatal ();
  }


  Serial.print("Openning: ");
  Serial.println(DATA_FILE);
  Serial.print("RAM: ");
  Serial.println(FreeRam());
  dataFile = SD.open (DATA_FILE, FILE_WRITE);
  if (!dataFile)
    fatal();

  dataFile.println("time (ms),Altitude (m),Pressure (pa),Tempurature (C),Vertical Velocity (m/s),Free Ram (bytes)");

  //Logging
  Serial.println("Startup successful");
  Serial.println("Switching to onboard Error Logging");

  //Log inital setup
  // write("[++] successful startup at", logFile);
  // write("[@@] beginning preflight at", logFile);
  // write("[@@] initial presure set to: " + String (kollsman), logFile);
  float initialPressure =  bmp.readPressure();
  // write("[@@] initial altitude reading: " + String (initialAltitude), logFile);

  //Test for errant Altitude readings
  while (initialPressure > (kollsman*1.1) && initialPressure < (kollsman*0.9)) {
     error("[!!] inital altitude error: " + String (initialPressure) + "\n[!!] resetting...");
     delay(10);
  }

  //log successful preflight
  // write("[++] successful preflight", logFile);
  // write("[@@] beginning main loop()", logFile);
  currentRunmode = PRELAUNCH;
  digitalWrite(LOOP_LED, HIGH);
}

//main loop
void loop(){
  bmp.readAltitude(kollsman);
  altitude = bmp.readAltitude(kollsman);
  bmp.readPressure();
  pressure = bmp.readPressure();
  bmp.readTemperature();
  tempurature = bmp.readTemperature();

  if (pressure == 60373.94 || altitude == NAN)
    error("[!!] altitude error: " + String(altitude));

  if (digitalRead(LOOP_STOP) == 1){
    // write("[@@] shutdown signal received", logFile);
    digitalWrite(LOOP_LED, LOW);
    dataFile.close();
    // logFile.close();
    Serial.println("Main Loop Halted\nSafe To Remove SD card");
    while(1);
  }

  write();
}

//calculate velocity from Altitude
float readVelocity() {
  float startTime = millis();
  float velocityReading = bmp.readAltitude(kollsman);
  delay(15);   //Adjust for greater percision at the sacrifice of speed
  velocityReading = bmp.readAltitude(kollsman) - velocityReading;
  return (velocityReading/((millis() - startTime)/1000));
}

//write
void write(){
  dataFile.print(millis());
  dataFile.print(",");
  dataFile.print(altitude);
  dataFile.print(",");
  dataFile.print(pressure);
  dataFile.print(",");
  dataFile.print(tempurature);
  dataFile.print(",");
  dataFile.print(velocity);
  dataFile.print(",");
  dataFile.println(FreeRam());
}

//error
void error(String message){
  char * buffer;
  message.toCharArray(buffer, message.length());
  //log(buffer);
  Serial.println(message);
  digitalWrite(ERROR_LED, HIGH);
  delay (100);
  digitalWrite(ERROR_LED, LOW);
}

//fatal error
void fatal(){
  Serial.println("[!!] FATAL ERROR");
  Serial.println("[!!] HALTING OPERATION AT");
  digitalWrite(ERROR_LED, HIGH);
  digitalWrite(LOOP_LED, LOW);
  while (1);
}

/*
log()
Used for logging data in dataFile
because of the limitted memory on the arduino, the data file must be closed before information can be logged in a separate file.
This function:
Closes the dataFile
opens the LOG_FILE
writes the message to the LOG_FILE
Closes the log File
reopens the data file for recording
This is how we get around the limited memory in the arduino while still keeping the dataFile readable in microsoft excel
*/
// void log (const char * message){
//   dataFile.close();
//
//   logFile = SD.open(LOG_FILE, FILE_WRITE);
//   if (!logFile)
//     fatal();
//
//   logFile.println(message);
//   logFile.close();
//
//   dataFile = SD.open(DATA_FILE, FILE_WRITE);
//   if (!dataFile)
//     fatal();
// }
