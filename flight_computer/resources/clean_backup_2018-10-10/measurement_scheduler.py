import sched, time
import _thread as thread

class mode():
  flight = 0
  standby = 1


class measurement_scheduler():
  def __init__(self, rate_in_Hz = 10):
    self.rate = rate_in_Hz
    self.scheduler = sched.scheduler(time.time, time.sleep)
    self.do_loop = False
    self.members = []
    self.mode = mode.flight
    self.do_broadcast_data = False
    self.do_save_data = False
    
  def loop(self):
    t_last = time.time()
    
    if self.do_loop == False:
      return
    self.scheduler.enter((t_last - time.time()) * 2 + 1 / self.rate, 1, self.loop)
    
    thread.start_new_thread(self.get_all,())
  
  def start(self):
    self.member_tick_counter = []
    for member in self.members:
      self.member_tick_counter.append(0)
    
    self.do_loop = True
    self.scheduler.enter(0, 1, self.loop)
    thread.start_new_thread(self.scheduler.run,())
    self.counter = 0
  
  def stop(self):
    self.do_loop = False
  
  def test(self, t):
    self.t0 = time.time()
    self.start()
    print('started')
    time.sleep(t)
    self.stop()
    print('stopped')
    print(self.counter)
  
  def add_member(self, member):
    self.members.append()
  
  def broadcast_data(self, data):
    print(data)
    
  def save_data(self, data):
    pass
    
  def handle_data(self, data):
    if self.do_broadcast_data:
      self.broadcast_data(data)
    if self.do_save_data:
      self.save_data(data)
    return
  
  def get_all(self):
    for i in range(len(self.members)):
      self.member_tick_counter[i] += 1
      if self.member_tick_counter[i] >= self.members[i].rate[self.mode]:
        data = self.members[i].get(new = 1)
        thread.start_new_thread(self.handle_data,(data,))
        self.member_tick_counter[i] = 0
    
    return
    
m = measurement_scheduler(10)