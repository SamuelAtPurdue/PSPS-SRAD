import FGU
import time

from lsm9ds1 import *
from bmp280 import *
from ugps import *

print()

imu.setup(flight_rate = 1, standby_rate = 10)
bmp.setup(flight_rate = 10)

m = ms(10)
m.add_member(imu)
m.add_member(bmp)

def test(t):
  FGU.activate()
  time.sleep(t)
  FGU.deactivate()
