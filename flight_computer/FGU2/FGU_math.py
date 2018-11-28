import math
from objdict import ObjDict

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

  
def analyze_3d(data_list):
  data = ObjDict()
  data.values = data_list
  data.magnitude = magnitude(data_list)
  data.angles = absolute_angles(data_list)
  return data