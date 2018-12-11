import board, busio, adafruit_lsm9ds1, time, json


i2c = busio.I2C(board.SCL, board.SDA)
lsm9ds1_i2c_object = adafruit_lsm9ds1.LSM9DS1_I2C(i2c)


list_of_data = []

def get():
  global list_of_data
  data = {}
  data['acc'] = list(lsm9ds1_i2c_object.accelerometer)
  data['gyr'] = list(lsm9ds1_i2c_object.gyroscope)
  list_of_data.append(data)
  print(data)


def save(file_name):
  with open(file_name, 'w') as outfile:
    json.dump(list_of_data, outfile)

def take(number_of_measurements, delay = 10):
  for i in range(number_of_measurements):
    get()
    time.sleep(delay)
