import math
from objdict import ObjDict

x = 0
y = 1
z = 2
_unit = [
	[1,0,0],
	[0,1,0],
	[0,0,1]
]

R = [
 [[1, 0, 0],
	[0, 0, 0],
	[0, 0, 0]],

 [[0, 0, 0],
	[0, 1, 0],
	[0, 0, 0]],

 [[0, 0, 0],
  [0, 0, 0],
  [0, 0, 1]]
]

def populate_rotation_matrices(angle_differences_vector):
	R[x][y][y] =  math.cos(angle_differences_vector[x])
	R[x][y][z] = -math.sin(angle_differences_vector[x])
	R[x][z][y] =  math.sin(angle_differences_vector[x])
	R[x][z][z] =  math.cos(angle_differences_vector[x])

	R[y][x][x] =  math.cos(angle_differences_vector[y])
	R[y][x][z] =  math.sin(angle_differences_vector[y])
	R[y][z][x] = -math.sin(angle_differences_vector[y])
	R[y][z][z] =  math.cos(angle_differences_vector[y])

	R[z][x][x] =  math.cos(angle_differences_vector[z])
	R[z][x][y] = -math.sin(angle_differences_vector[z])
	R[z][y][x] =  math.sin(angle_differences_vector[z])
	R[z][y][y] =  math.cos(angle_differences_vector[z])

# Finds the magnitude of a 3D vector
def magnitude(vector):
	total = vector[x] ** 2 + vector[y] ** 2 + vector[z] ** 2
	return math.sqrt(total)

# Gives the vector a lenght of 1
def normalize(vector):
	length = magnitude(vector)
	return scale(1 / length, vector)

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

# component of b onto a
def component(vector_a, vector_b):
	return dot(vector_a, vector_b) / magnitude(vector_a);

# project b onto a
def project(vector_a, vector_b):
	scalar = component(vector_a, vector_b) / magnitude(vector_a)
	return scale(scalar, vector_a)

# cross product a x b
def cross(vector_a, vector_b):
	result = [0, 0, 0]
	result[x] = vector_a[y] * vector_b[z] - vector_a[z] * vector_b[y]
	result[y] = vector_a[z] * vector_b[x] - vector_a[x] * vector_b[z]
	result[z] = vector_a[x] * vector_b[y] - vector_a[y] * vector_b[x]


# Finds the angle between two 3D vectors
def angle_between(vector_a, vector_b):
	dots = dot(vector_a, vector_b)
	mags = magnitude(vector_a) * magnitude(vector_b)
	combined = dots / mags
	return math.acos(combined)

def absolute_angles(vector):
	return angles_from_axes(vector)



# Finds the angles between a 3D vector and the unit vectors
# Returns a signed 3D vector
def angles_from_axes(vector):
	angles = [0, 0, 0]
	angles[x] = math.copysign(angle_between(vector, _unit[x]), vector[x])
	angles[y] = math.copysign(angle_between(vector, _unit[y]), vector[y])
	angles[z] = math.copysign(angle_between(vector, _unit[z]), vector[z])
	return angles

def subtract_vectors(vector_a, vector_b):
	result = [0,0,0]
	result[x] = vector_a[x] - vector_b[x]
	result[y] = vector_a[y] - vector_b[y]
	result[z] = vector_a[z] - vector_b[z]
	return result

def calculate_rotation(vector_ref, vector_raw):
	angles = subtract_vectors(angles_from_axes(vector_ref), angles_from_axes(vector_raw))
	populate_rotation_matrices(angles)


def transform_to_ground(vector):
	def matrix_mult(Matrix, Vector):
		result = [0,0,0]
		for i in range(3):
			for j in range(3):
				result[i] += Matrix[i][j] * Vector[j]
		return result

	for i in range(3):
		vector = matrix_mult(R[i], vector)

	return vector



# Not needed  
def analyze_3d(data_list):
  data = ObjDict()
  data.values = data_list
  data.magnitude = magnitude(data_list)
  data.angles = angles_from_axes(data_list)
  return data