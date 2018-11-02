import FGU, FGU_math, board, busio, adafruit_bmp280, time
from objdict import ObjDict

class BMP280_Sensor(FGU.Sensor_I2C):
  def update(self):
    self.data = ObjDict()
    self.data.timestamp = FGU.get_timestamp()
    self.data.type = 'BMP280'
    
    self.data.temperature = self._sensor.temperature
    self.data.pressure = self._sensor.pressure
    self.data.altitude = self._sensor.altitude
    return self.data
    
  def get(self):
    return self.data


i2c = busio.I2C(board.SCL, board.SDA)
bmp280_i2c_object = adafruit_bmp280.Adafruit_BMP280_I2C(i2c)
bmp280 = BMP280_Sensor(bmp280_i2c_object)
