import sched, time, datetime
import _thread as thread

class Periodic_Scheduler():
  def __init__(self):
    self.scheduler = sched.scheduler(time.time, time.sleep)
    self.do_loop = False
  
  def setup(self, interval, action, action_args=()):
    self.scheduler.enter(interval, 1, self.loop,
            (interval, action, action_args))
    action(*action_args)
    
  def _start_action_thread(self, action, action_args):
    thread.start_new_thread(action, action_args)
    
  def loop(self, interval, action, action_args=()):
    self.t_last = time.time()
    if self.do_loop == False:
      return
    
    self.scheduler.enter(self.t_last + interval - time.time(), 1, self.loop,
            (interval, action, action_args))
    self._start_action_thread(action, action_args)
    
  
  def start(self):
    self.do_loop = True
    self.t_last = time.time()
    thread.start_new_thread(self.scheduler.run,())
    
    
  def stop(self):
    self.do_loop = False
  
  def run_for(self, t):
    self.start()
    print('started')
    time.sleep(t)
    self.stop()
    print('stopped')
  
def periodic_event():
  print(datetime.datetime.now())
  time.sleep(3)
 




 
def measure(fct, t):
  t0 = time.time()
  fct(t)
  tf = time.time()
  print('------------')
  print(tf - t0)
  print((tf - t0) / t)
  
def baseline(t):
  time.sleep(10)
  
  
INTERVAL = 1
ps = Periodic_Scheduler()
ps.setup(INTERVAL, periodic_event)

