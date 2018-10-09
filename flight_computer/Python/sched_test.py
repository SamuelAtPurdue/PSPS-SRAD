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

 


class controller():
  INTERVAL = 1    ## time in s
  RATE = 1        ## Hz
  
  members = []
  poll_rates = []
  _poll_id = 0
  
  
  def set_rate(rate):
    controller.RATE = rate
    controller.INTERVAL = 1 / rate
  
  def add_member(new_member, poll_rate):
    controller.members.append(new_member)
    controller.poll_rates.append(poll_rate)
    
  def poll()
    controller._poll_id += 1
    
    for member, poll_rate in zip(controller.members, controller.poll_rates):
      if poll_rate / controller.RATE * controller._poll_id % 1 == 0:
        thread.start_new_thread(member.get,())
    if(controller._poll_id > controller.RATE):
      controller._poll_id = 0
    
class imu():
  def get():
    print('imu:', datetime.datetime.now())

class bmp():
  def get():
    print('bmp:', datetime.datetime.now())
    
    
controller.set_rate(10)
controller.add_member(imu, 10)
controller.add_member(bmp, 1)

ps = Periodic_Scheduler()
ps.setup(controller.INTERVAL, periodic_event)    
    
    
    
    
    
    
    
    
    
    
    
def measure(fct, t):
  t0 = time.time()
  fct(t)
  tf = time.time()
  print('------------')
  print(tf - t0)
  print((tf - t0) / t)
  
def baseline(t):
  time.sleep(10)
  
  


