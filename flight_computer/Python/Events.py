### Last Edited 10.31.2018

class Event_Handler():
  def __init__(self):
    self.target_fcts = []
  
  def subscribe(self, target_fct):
    self.target_fcts.append(target_fct)
  
  def event(self):
    for target_fct in self.target_fcts:
      target_fct()