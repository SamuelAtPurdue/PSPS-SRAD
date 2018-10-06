import time
import datetime
import json
import math
import _thread as thread

# Data Packet Types
class packet_type():
  LSM9DS1 = 'lsm9ds1'
  BMP280  = 'bmp280'
  UGPS    = 'ugps'

class mode():
  standby = 0
  flight = 1
# 
class sampling_control():
  is_active = False
  from FGU import mode
  mode = mode.flight
  record = False
  
  members = []
  
  
  

class sampling():
  def __init__(self, parent, flight_rate, standby_rate = None):
    self.parent = parent
    self.flight_rate = flight_rate
    
    if standby_rate == None:
      self.standby_rate = flight_rate
    else:
      self.standby_rate = standby_rate
    sampling_control.members.append(parent)

  def loop(self):
    t1 = time.time()
    t2 = time.time()
    
    count = 0
    from FGU import mode
    while sampling_control.is_active == True:
      t1 = time.time()
      self.parent.update()
      count = count + 1
      #print(self.parent.get(),'\n')
      
      rate = self.flight_rate
      
      if sampling_control.mode == mode.standby:
        rate = self.standby_rate
        
      time.sleep(0.9 / rate - time.time() + t1)
      while(time.time() < t1 + 1 / rate):
        time.sleep(0.01)
        
    print(self.parent.name,":",count)
    print(time.time()-t2)
    
  def loop2(self):
    take_sample = True
    t1 = time.time()
    t2 = time.time()
    
    count = 0
    
    while sampling_control.is_active == True:
      if take_sample == True:
        t1 = time.time()
        self.parent.update()
        count = count + 1
        #print(self.parent.get(),'\n')
        take_sample = False
        continue
        
      else:
        rate = self.flight_rate
        
        from FGU import mode
        if sampling_control.mode == mode.standby:
          rate = self.standby_rate
        
        if time.time() - t1 > 1 / rate:
          take_sample = True
          continue
      
        time.sleep(1 / rate / 20)
        
    print(self.parent.name,":",count)

def activate():
  abort_reason = None
  for member in sampling_control.members:
    if member.flight_ready == False:
      abort_reason = member
      break
  
  if abort_reason != None:
    print('Activation aborted because {} is not flight ready.'.format(
    abort_reason.name))
    return
  
  sampling_control.is_active = True
  for member in sampling_control.members:
    thread.start_new_thread(member.sampling.loop,())
    

def deactivate():
  sampling_control.is_active = False
        
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

