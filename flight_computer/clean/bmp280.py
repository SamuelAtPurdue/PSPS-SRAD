import FGU
import board
import busio
import adafruit_bmp280
from objdict import ObjDict

### Functions applied to instance ###

def _update(self):
  self.data = ObjDict()
  self.data.type = FGU.packet_type.BMP280
  
  self.data.timestamp = time.time() - FGU.t0
  
  self.data.temp = self.temperature
  self.data.pres = self.pressure
  self.data.alt = self.altitude

setattr(adafruit_bmp280.Adafruit_BMP280_I2C, 'update', _update)

def _get(self):
  self.update()
  return self.data

i2c = busio.I2C(board.SCL, board.SDA)
bmp = adafruit_bmp280.Adafruit_BMP280_I2C(i2c)

print("BMP280 is object 'bmp'")
