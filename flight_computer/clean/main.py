import FGU
import time

from lsm9ds1 import *
from bmp280 import *
from ugps import *

import external_timer as et

print()

imu.setup(flight_rate = 1, standby_rate = 10)
bmp.setup(flight_rate = 10)
et.setup(output_pin = 24, input_pin = 23)



def test(t):
  FGU.activate()
  time.sleep(t)
  FGU.deactivate()
