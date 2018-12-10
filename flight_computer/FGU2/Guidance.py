import FGU
from objdict import ObjDict

class Guidance_Class():
  _initialized = False
  _ref_mag_vector = None
  _ref_imu_coords = None
  _ref_gps_coords = None

  def initialize(self, lsm9ds1_data, ultimategps_data):
    if lsm9ds1_data.type != 'LSM9DS1':
      error_string = '\'LSM9DS1\' packet is of invalid type.'
      raise TypeError(error_string)
      return

    if ultimategps_data.type != 'Ultimate GPS':
      error_string = '\'Ultimate GPS\' packet is of invalid type.'
      raise TypeError(error_string)
      return

    _ref_mag_vector = lsm9ds1_data.magnetometer.angles 
    _ref_imu_coords = [0,0,0]
    _ref_gps_coords = [
          ultimategps_data.data.latitude, 
          ultimategps_data.data.longitude,
          ultimategps_data.data.altitude]


  def integration(self,):
    pass



Guidance = Guidance_Class()