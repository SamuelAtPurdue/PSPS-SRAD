
/***********************************************************************************************************
* Project: PSP Blazing Fast Altimeter
* File: main.h
* Created On: 1/12/2019
* Description: This is the header file for main.cpp which contains the function prototypes and class
* abstractions.
* Programmers: Samuel Hild          shild@purdue.edu
************************************************************************************************************/

// runmodes
enum Runmode {SETUP, PREFLIGHT, BURN, ASCENT, DESCENT , POSTFLIGHT};

/***********************************************************************************************************
* Class: Altimeter
* Created On: 1/12/2019
* Description: Abstract class used to create a common interface for altitude sensors.
************************************************************************************************************/
class Altimeter
{
private:
  void flushData();               // flushes stored memory data from altitude sensor so an accurate altitude value can be obtained
  bool verifyData();              // Verifies data to ensure that the sensor is working properly
  bool testSensor();              // runs full ground test of the sensor

public:
  enum AltimeterConfig {HIGH_SPEED, BALANCED, HIGH_PERCISION};

  bool begin();                   // begins the altimeter for pressure readings
  void configSensor(AltimeterConfig); // allows for configuration of the sensor

  float readPressure();           // Reads pressure data from the sensor
  float readTemperature();        // Reads tempurature data from the sensor
  float readAltitude();           // Reads altitude data from the sensor
  float calculateVelocity();      // calculates velocity from altitude readings, may be slow
};

/***********************************************************************************************************
* Class: Altimeter
* Created On: 1/12/2019
* Description: Abstract class for all storage devices
************************************************************************************************************/
class Storage
{
private:
  void writeHeader();             // Writes the header string to the file

public:
  void open(const char *);        // opens a file
  void write(const char *);       // writes data to the file
  void close();                   // closes a file
  bool isActive();                // checks to see if the Storage device is active
};

// hardware setup functions
void setupDisplay();              // sets up the led pins to display important information
void setupChutes();               // sets up the pins used for reading charge continuity and deploying the chutes
Altimeter *setupAltimeter();      // sets up the altitude sensor, this should be abstracted to support different altimeter hardware
Storage *setupPrimaryStorage();   // sets up the primary storage unit which is typically larger but may not be as reliable
Storage *setupSecondaryStorage(); // sets up the secondary storage unit which stores data if the primary storage unit fails
void finallizeSetup();            // finishes the setup for the entire device and runs tests to ensure data is collected and stored properly

// I2c Communication Functions
bool setupSlave();                // attempts to setup the altimeter as an I2c slave device, this is not required for operation
bool getConfig();                 // if setupSlave() is successful, config info is received from the master device. these functions should be temporally linked

// utility functions
void readData();                  // reads data from the sensors
void writeData(float, float, float, float); // attempts to write data to primary storage and writes it to secondary storage if that fails
void log(const char *);           // logs data to a seperate file
Runmode runmodeAction(Runmode);      // Performs runmode specific action

// Error Functions
void error(const char *);         // handles a nonfatal error
void fatal(const char *);         // handles a fatal error and halts opperation
