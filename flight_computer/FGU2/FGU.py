import time, datetime, json
from abc import ABC, abstractmethod
from Events import Event_Handler
import External_Timing as ET
import Data_Manager as DM

class MODE():
  STANDBY = 'standby'
  FLIGHT = 'flight'

mode = MODE.FLIGHT
  
Tick_Handler = Event_Handler()

def setup_External_Timing(output_pin, input_pin):
  ET.setup(output_pin, input_pin, Tick_Handler.event)

def activate():
  ET.activate()

def deactivate():
  ET.deactivate()

class Device_Manager():
  devices = []

  def check_in(new_device):
    Device_Manager.devices.append(new_device)

  def perform_flight_readiness_check(print_all = True):
    overall_flight_readiness = True

    for device in Device_Manager.devices:
      device_readiness, return_string = device.flight_readiness_check()
      overall_flight_readiness = overall_flight_readiness and device_readiness
      if print_all or not device_readiness:
        print(return_string)

    return overall_flight_readiness

class Launch_Controller():
  _launch_function = None
  _ready_for_launch = False
  
  def _dummy_launch_function():
    print('\n')
    print('---------------------')
    print('DUMMY LAUNCH FUNCTION')
    print('---------------------')

  def is_ready_for_launch():
    return Launch_Controller.is_ready_for_launch

  def ready():
    Launch_Controller._ready_for_launch = Device_Manager.perform_flight_readiness_check()
    if _ready_for_launch:
      print('\n')
      print('--------------------')
      print('FGU READY FOR LAUNCH')
      print('--------------------')
    else:
      print('\n')
      print('------------------------')
      print('FGU NOT READY FOR LAUNCH')
      print('------------------------')
    return Launch_Controller._ready_for_launch

  def launch():
    ### TO DO:
    ###   Save to the data file the timestamp of launch. 
    Launch_Controller._ready_for_launch = Device_Manager.perform_flight_readiness_check(print_all = False)

    if Launch_Controller._ready_for_launch:
      Launch_Controller._launch_function()

Launch_Controller._launch_function = Launch_Controller._dummy_launch_function

### Abstract Class
class Sensor(ABC):
  _is_setup = False
  ticks = {'standby': 10, 'flight': 1}
  
  def __init__(self, sensor, ticks = {}):
    self._sensor = sensor
    
    for key, value in ticks.items():
      if key in self.ticks:
        self.ticks[key] = value
      else:
        raise AttributeError('Invalid key in ticks:', key)
    
    self.data_manager = DM.Data_Manager()
    
    Tick_Handler.add(self.handle_tick)
    super().__init__()
  
  @abstractmethod
  def update(self):
    pass
  
  def get(self):
    return self.data
  
  def handle_tick(self):
    tick_count = ET.get_tick_count()
    if tick_count % self.ticks[mode] == 0:
      self.data_manager.process_data(mode, self.update())
  
  def setup(self, ticks_per_measurement):
    self._ticks_per_measurement = ticks_per_measurement
    self._ticks_counter = ticks_per_measurement
    self._is_setup = True
    Sensors_Manager.check_in(self)

  @abstractmethod
  def flight_readiness_check(self):
    pass

#  @abstractmethod
#  def activation_readiness_check(self):
#    pass
  

t0 = 0
t0_datetime = ''  
# Starts Epoch
def start_epoch():
  global t0, t0_datetime
  t0 = time.time()
  t0_datetime = datetime.datetime.fromtimestamp(t0).strftime('%Y-%m-%d_%H-%M-%S')
  print('Epoch Started at:', t0_datetime)
  
# Get current timestamp relative to the epoch
def get_timestamp():
	return time.time() - t0
def get_epoch():
  return t0
def get_epoch_string():
  return t0_datetime
  
start_epoch()
