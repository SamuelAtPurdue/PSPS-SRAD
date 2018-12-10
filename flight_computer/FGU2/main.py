import FGU, time
#import SDCARD
import Data_Manager as DM
from LSM9DS1 import lsm9ds1
from BMP280 import bmp280
from UGPS import ultimateGPS

FGU.setup_External_Timing(output_pin = 17, input_pin = 27)
FGU.mode = FGU.MODE.STANDBY

###DM.set_save_fct(SDCARD.save)
DM.set_save_fct(DM.do_nothing)
DM.set_broadcast_fct(DM.do_nothing)

exit()

FGU.activate()
time.sleep(10)
FGU.deactivate()
