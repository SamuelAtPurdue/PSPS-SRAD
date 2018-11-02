### Last Edited 11.1.2018

class Event_Handler():
  def __init__(self):
    self.target_fcts = []
  
  def add(self, target_fct):
    self.target_fcts.append(target_fct)
  
  def event(self):
    for target_fct in self.target_fcts:
      target_fct()