import FGU
import board
import busio
import adafruit_bmp280
import time
from objdict import ObjDict

### Functions applied to instance ###

def _update(self):
  self.data = ObjDict()
  self.data.type = FGU.packet_type.BMP280
  
  self.data.timestamp = FGU.get_timestamp()
  
  self.data.temp = self.temperature
  self.data.pres = self.pressure
  self.data.alt = self.altitude

setattr(adafruit_bmp280.Adafruit_BMP280_I2C, 'update', _update)

def _get(self, new = 0):
  if new == 1:
    self.update()
  return self.data

setattr(adafruit_bmp280.Adafruit_BMP280_I2C, 'get', _get)

def _setup(self, flight_rate, standby_rate = None):

  if standby_rate == None:
    standby_rate = flight_rate
  self.rate = [flight_rate, standby_rate]

  bmp.flight_ready = True
  print('bmp is flight ready with flight rate of {} Hz and a standby rate of {} Hz.'.format(
  self.sampling.flight_rate, self.sampling.standby_rate))

setattr(adafruit_bmp280.Adafruit_BMP280_I2C, 'setup', _setup)

i2c = busio.I2C(board.SCL, board.SDA)
bmp = adafruit_bmp280.Adafruit_BMP280_I2C(i2c)
bmp.flight_ready = False
bmp.name = 'bmp'

print("BMP280 is object 'bmp'")
