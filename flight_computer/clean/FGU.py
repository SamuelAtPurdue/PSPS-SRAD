import time
import datetime
import json
import math
import _thread as thread

import RPi.GPIO as GPIO
GPIO.setmode(GPIO.BCM)

# Data Packet Types
class packet_type():
  LSM9DS1 = 'lsm9ds1'
  BMP280  = 'bmp280'
  UGPS    = 'ugps'

class mode():
  standby = 1
  flight = 0
  
  mode = 0
  do_broadcast = False
  do_save = True
  
def get_mode():
  return mode.mode
# 

class timer_control():
  tick_counter = 0
  members = []
  
  def add_member(member):
    timer_control.members.append(member)

  def tick():
    for member in timer_control.members:
      if timer_control.tick_counter % member.get_rate() == 0:
        data = member.get(new = 1)
        if mode.do_broadcast:
          broadcast(data)
        if mode.do_save:
          save(data)
    timer_control.tick_counter += 1
    print('tick')
    
    
    
def broadcast(data):
  pass
def save(data):
  print('save')
  print(data)
  

_activate_target = None  
def activate():
  if _activate_target == None:
    print('Error: Activation target not set')
    return
  else:
    _activate_target()

_deactivate_target = None
def deactivate():
  if _deactivate_target == None:
    print('Error: Deactivation target not set')
    return
  else:
    _deactivate_target()

def import_timer(timer):
  global _activate_target, _deactivate_target
  _activate_target = timer.activate
  _deactivate_target = timer.deactivate

def import_timer_fcts(activate_fct, deactivate_fct):
  global _activate_target, _deactivate_target
  _activate_target = activate_fct
  _deactivate_target = deactivate_fct

  
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

