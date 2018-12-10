

def get_raw():
  accel_x = 0
  accel_y = 0
  accel_z = 0
  gyro_x = 0
  gyro_y = 0
  gyro_z = 0
  mag_x = 0
  mag_y = 0
  mag_z = 0

  accel = [accel_x, accel_y, accel_z]
  gyro = [gyro_x, gyro_y, gyro_z]
  mag = [mag_x, mag_y, mag_z]
  return accel, gyro, mag
