class Event_Handler():
  def __init__(self):
    self.listeners = []
  
  def subscribe(self, listener):
    if listener in self.listeners:
      print('Warning: Already subscribed.')
      return
    else:
      self.listeners.append(listener)
      return
  
  def unsubscribe(self, listener):
    if listener in self.listener:
      self.listeners.remove(listener)
      return
    else:
      print('Warning: Can\'t unsubscribe listener because it',
            'is not subscribed.')
      return
    
  def event(self):
    for listener in self.listeners:
      listener.handle()
  
class Event_Listener():
  def __init__(self, target_handler, target_fct):
    self.target_fct = target_fct
    target_handler.subscribe(self)
    
    