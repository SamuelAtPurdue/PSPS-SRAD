_save_fct = None
_broadcast_fct = None

class Data_Manager():
  save = {'standby': True, 'flight': True}
  broadcast = {'standby': True, 'flight': True}
  output = {'standby': True, 'flight': True}
  
  def __init__(self, save = {}, broadcast = {}, output = {}):
    ### Update save parameters from default
    for key, value in save.items():
      if key in self.save:
        self.save[key] = value
      else:
        raise AttributeError('Invalid key in save:', key)
    
    ### Update broadcast parameters from default
    for key, value in broadcast.items():
      if key in self.broadcast:
        self.broadcast[key] = value
      else:
        raise AttributeError('Invalid key in broadcast:', key)
    
    ### Update output parameters from default
    for key, value in output.items():
      if key in self.output:
        self.output[key] = value
      else:
        raise AttributeError('Invalid key in output:', key)
  
  def process_data(self, mode, data):
    if self.save[mode]:
      save(data)
    if self.broadcast[mode]:
      broadcast(data)
    if self.output[mode]:
      output(data)
    
    
def set_save_fct(save_fct):
  global _save_fct
  _save_fct = save_fct

def save_check(raise_error = False):
  if _save_fct == None:
    if raise_error:
      raise AttributeError('Data Manager does not have a save function set.')
    return False
  
  return True
  
def save(data):
  if save_check(raise_error = True):
    _save_fct(data)

  
def set_broadcast_fct(broadcast_fct):
  global _broadcast_fct
  _broadcast_fct = broadcast_fct

def broadcast_check(raise_error = False):
  if _broadcast_fct == None:
    if raise_error:
      raise AttributeError('Data Manager does not have a broadcast function set.')
    return False
    
  return True

def broadcast(data):
  if broadcast_check(raise_error) == True:
    _broadcast_fct(data)

def output(data):
  print(data)

def do_nothing(data):
  pass
