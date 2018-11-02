class limited_value():
  def __init__(self, value, min = None, max = None):
    self.value = value
    self.min = min
    self.max = max
  
  def __get__(self, obj, objtype):
    return self.value
  
  def __set__(self, obj, value):
    if self.min is not None and value < self.min:
      self.build_error_message(value)
      raise ValueError(self.error_message)
      return
    elif self.max is not None and value > self.max:
      self.build_error_message(value)
      raise ValueError(self.error_message)
      return
    else:
      self.value = value

  
  def build_error_message(self, value):
    tab = '             '
    self.error_message = ''
    self.error_message += 'Attempted value is out of the bounds of this variable.\n'
    self.error_message += tab + '  Minimum allowed value: {}\n'.format(self.min)
    self.error_message += tab + '  Maximum allowed value: {}\n'.format(self.max)
    self.error_message += tab + '        Attempted value: {}\n'.format(value)


class MyClass():
  x = limited_value(12, min = 3, max = 20)
  
  
a = MyClass()