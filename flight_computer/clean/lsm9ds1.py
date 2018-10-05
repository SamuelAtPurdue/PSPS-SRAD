import FGU
import board
import busio
import adafruit_lsm9ds1
import time
from objdict import ObjDict

### resources ###

# List vector component indices
x = 0
y = 1
z = 2

### Functions applied to instance ###

def _update(self):
  self.data = ObjDict()
  self.data.type = FGU.packet_type.LSM9DS1
  
  self.data.timestamp = FGU.get_timestamp()
  
  self.data.accel = list(self.accelerometer)
  self.data.accel_angles = FGU.absolute_angles(self.data.accel)
  self.data.accel_mgtd = FGU.magnitude(self.data.accel)
  self.data.gyro = list(self.gyroscope)
  self.data.gyro_angles = FGU.absolute_angles(self.data.gyro)
  self.data.gyro_mgtd = FGU.magnitude(self.data.gyro)
  self.data.mag = list(self.magnetometer)
  self.data.mag_angles = FGU.absolute_angles(self.data.mag)
  self.data.mag_mgtd = FGU.magnitude(self.data.mag)
  self.data.temp = self.temperature


setattr(adafruit_lsm9ds1.LSM9DS1_I2C, 'update', _update)

def _get(self, new = 0):
  if new == 1:
    self.update()
  return self.data
  
setattr(adafruit_lsm9ds1.LSM9DS1_I2C, 'get', _get)

def _setup(self, flight_rate, standby_rate = None):
  self.sampling = FGU.sampling(self, flight_rate, standby_rate)
  imu.flight_ready = True
  print('imu is flight ready with flight rate of {} Hz and a standby rate of {} Hz'.format(
  flight_rate, standby_rate))

setattr(adafruit_lsm9ds1.LSM9DS1_I2C, 'setup', _setup)


i2c = busio.I2C(board.SCL, board.SDA)
imu = adafruit_lsm9ds1.LSM9DS1_I2C(i2c)
imu.flight_ready = False

print("LSM9DS1 is object 'imu'")

