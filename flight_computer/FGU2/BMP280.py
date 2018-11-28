import FGU, board, busio, adafruit_bmp280, time
from objdict import ObjDict

class BMP280_Sensor(FGU.Sensor):
  def update(self):
    self.data = ObjDict()
    self.data.timestamp = FGU.get_timestamp()
    self.data.type = 'BMP280'
    
    self.data.temperature = self._sensor.temperature
    self.data.pressure = self._sensor.pressure
    self.data.altitude = self._sensor.altitude
    return self.data

  def flight_readiness_check(self):
    return_string = '         BMP280 is ready for launch.'
    if not self._is_setup:
      return_string = 'CAUTION: BMP280 is NOT ready for launch. (NOT SETUP)'
    return self._is_setup, return_string

i2c = busio.I2C(board.SCL, board.SDA)
bmp280_i2c_object = adafruit_bmp280.Adafruit_BMP280_I2C(i2c)
bmp280 = BMP280_Sensor(bmp280_i2c_object)
