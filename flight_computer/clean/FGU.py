import time
import datetime
import json
import math

# Data Packet Types
class packet_type():
  LSM9DS1 = 0
  BMP280  = 1
  UGPS    = 2

class mode():
  standby = 0
  flight = 1
# 
class sampling_control():
  is_active = False
  from FGU import mode
  mode = mode.standby
  record = False
  
  members = []
  
  
  

class sampling():
  def __init__(self, parent, flight_rate, standby_rate = None):
    self.parent = parent
    self.flight_rate = flight_rate
    
    if standby_rate == None:
      self.has_standby = False
    else:
      self.standby_rate = standby_rate
      self.has_standby = True

  def loop(self):
    while FGU.sampling_control.is_active == True:
      parent.update()
      
# List vector component indices
x = 0
y = 1
z = 2

# Unit Vectors
_i_hat = [1, 0, 0]
_j_hat = [0, 1, 0]
_k_hat = [0, 0, 1]
_unit = [_i_hat, _j_hat, _k_hat]


# Starts Epoch
def start_epoch():
  global t0, t0_datetime
  t0 = time.time()
  t0_datetime = datetime.datetime.fromtimestamp(t0).strftime('%Y-%m-%d_%H-%M-%S')
  print('Epoch Started at:', t0_datetime)
  
# Get current timestamp relative to the epoch
def get_timestamp():
	return time.time() - t0


# Finds the magnitude of a 3D vector
def magnitude(vector):
	total = vector[x] ** 2 + vector[y] ** 2 + vector[z] ** 2
	return math.sqrt(total)


# Multiplies a 3D vector by a scalar
def scale(scalar, vector):
	result = [0, 0, 0]
	result[x] = scalar * vector[x]
	result[y] = scalar * vector[y]
	result[z] = scalar * vector[z]
	return result


# Performs a dot product on two 3D vectors
def dot(vector_a, vector_b):
	total =  vector_a[x] * vector_b[x]
	total += vector_a[y] * vector_b[y]
	total += vector_a[z] * vector_b[z]
	return total


# Finds the angle between two 3D vectors
def angle_between(vector_a, vector_b):
	dots = dot(vector_a, vector_b)
	mags = magnitude(vector_a) * magnitude(vector_b)
	combined = dots / mags
	return math.acos(combined)


# Finds the angles between a 3D vector and the unit vectors
# Returns a signed 3D vector
def absolute_angles(vector):
	angles = [0, 0, 0]
	angles[x] = math.copysign(angle_between(vector, _unit[x]), vector[x])
	angles[y] = math.copysign(angle_between(vector, _unit[y]), vector[y])
	angles[z] = math.copysign(angle_between(vector, _unit[z]), vector[z])
	return angles
  
start_epoch()
