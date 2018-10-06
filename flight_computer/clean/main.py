import FGU
import time

from lsm9ds1 import *
from bmp280 import *
from ugps import *

print()

imu.setup(flight_rate = 10, standby_rate = 1)
bmp.setup(flight_rate = 1)


def test(t):
  FGU.activate()
  time.sleep(t)
  FGU.deactivate()
