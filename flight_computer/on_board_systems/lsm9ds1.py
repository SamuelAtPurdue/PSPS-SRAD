import board
import busio
import adafruit_lsm9ds1
import time
import math

### resources ###

# List vector component indices
x = 0
y = 1
z = 2

# Unit Vectors
_i_hat = [1, 0, 0]
_j_hat = [0, 1, 0]
_k_hat = [0, 0, 1]
_unit = [_i_hat, _j_hat, _k_hat]

# Finds the magnitude of a 3D vector
def _magnitude(vector):
	total = vector[x] ** 2 + vector[y] ** 2 + vector[z] ** 2
	return math.sqrt(total)


# Multiplies a 3D vector by a scalar
def _scale(scalar, vector):
	result = [0, 0, 0]
	result[x] = scalar * vector[x]
	result[y] = scalar * vector[y]
	result[z] = scalar * vector[z]
	return result


# Performs a dot product on two 3D vectors
def _dot(vector_a, vector_b):
	total =  vector_a[x] * vector_b[x]
	total += vector_a[y] * vector_b[y]
	total += vector_a[z] * vector_b[z]
	return total


# Finds the angle between two 3D vectors
def _angle_between(vector_a, vector_b):
	dots = _dot(vector_a, vector_b)
	mags = _magnitude(vector_a) * _magnitude(vector_b)
	combined = dots / mags
	return math.acos(combined)


# Finds the angles between a 3D vector and the unit vectors
# Returns a signed 3D vector
def _absolute_angles(vector):
	angles = [0, 0, 0]
	angles[x] = math.copysign(_angle_between(vector, _unit[x]), vector[x])
	angles[y] = math.copysign(_angle_between(vector, _unit[y]), vector[y])
	angles[z] = math.copysign(_angle_between(vector, _unit[z]), vector[z])
	return angles


### Functions applied to instance ###

def _update(self):
	self.accel = list(self.accelerometer)
	self.accel_angles = _absolute_angles(self.accel)
	self.accel_mgtd = _magnitude(self.accel)
	self.gyro = list(self.gyroscope)
	self.gyro_angles = _absolute_angles(self.gyro)
	self.gyro_mgtd = _magnitude(self.mag)
	self.mag = list(self.magnetometer)
	self.mag_angles = _absolute_angles(self.mag)
	self.mag_mgtd = _magnitude(self.mag)
	self.temp = self.temperature


setattr(adafruit_lsm9ds1.LSM9DS1_I2C, 'update', _update)

def _setup(self):
	pass

setattr(adafruit_lsm9ds1.LSM9DS1_I2C, 'setup', _setup)


i2c = busio.I2C(board.SCL, board.SDA)
imu = adafruit_lsm9ds1.LSM9DS1_I2C(i2c)

imu.setup()



print("LSM9DS1 is object 'imu'")

