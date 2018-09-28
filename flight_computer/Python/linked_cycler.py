class linked_cycler():
  head = None
  
  def __init__(length):
    self.head = linked_element(None)
    prev = self.head
    
    for i in range(length - 1):
      prev = linked_element(prev)
      
    head.prev = prev
    # END OF __init__(length)
    
  
  class linked_element():
    def __init__(self, prev):
      self.prev = prev
      
      
      