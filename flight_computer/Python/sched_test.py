import sched, time, datetime

class Periodic_Scheduler():
  def __init__(self):
    self.scheduler = sched.scheduler(time.time, time.sleep)
  
  def setup(self, interval, action, action_args=()):
    action(*action_args)
    self.scheduler.enter(interval, 1, self.setup,
            (interval, action, action_args))
  
  def run(self):
    self.scheduler.run()
    
def periodic_event():
  print(datetime.datetime.now())
  

INTERVAL = 1
ps = Periodic_Scheduler()
###ps.setup(INTERVAL, periodic_event)
###ps.run()