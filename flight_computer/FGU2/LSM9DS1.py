import FGU, FGU_math, board, busio, adafruit_lsm9ds1, time
from objdict import ObjDict

class LSM9DS1_Sensor(FGU.Sensor_I2C):
  def update(self):
    self.data = ObjDict()
    self.data.timestamp = FGU.get_timestamp()
    self.data.type = 'LSM9DS1'
    
    self.data.accelerometer = FGU_math.analyze_3d(list(self._sensor.accelerometer))
    self.data.gyroscope = FGU_math.analyze_3d(list(self._sensor.gyroscope))
    self.data.magnetometer = FGU_math.analyze_3d(list(self._sensor.magnetometer))
    self.data.temperature = self._sensor.temperature
    return self.data
  
  def get(self):
    return self.data


i2c = busio.I2C(board.SCL, board.SDA)
lsm9ds1_i2c_object = adafruit_lsm9ds1.LSM9DS1_I2C(i2c)
lsm9ds1 = LSM9DS1_Sensor(lsm9ds1_i2c_object)
