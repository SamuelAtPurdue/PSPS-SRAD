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
  ET.deactivate

### Abstract Class
class Sensor_I2C(ABC):
  _is_setup = False
  ticks = {'standby': 10, 'flight': 1}
  
  def __init__(self, sensor, ticks = {}):
    self._sensor = sensor
    
    for key, value in ticks.items():
      if key in self.ticks:
        self.ticks[key] = value
      else:
        raise AttributeError('Invalid key in ticks:' key)
    
    self.data_manager = DM.Data_Manager()
    
    Tick_Handler.add(self.handle_tick)
    super().__init__()
  
  @abstractmethod
  def update(self):
    pass
  
  @abstractmethod
  def get(self):
    pass
  
  def handle_tick(self):
    tick_count = ET.get_tick_count()
    if tick_count % self.ticks[mode] == 0:
      self.data_manager.process_data(mode, self.update())
  
  def setup(self, ticks_per_measurement):
    self._ticks_per_measurement = ticks_per_measurement
    self._ticks_counter = ticks_per_measurement
    self._is_setup = True
  

# Starts Epoch
def start_epoch():
  global t0, t0_datetime
  t0 = time.time()
  t0_datetime = datetime.datetime.fromtimestamp(t0).strftime('%Y-%m-%d_%H-%M-%S')
  print('Epoch Started at:', t0_datetime)
  
# Get current timestamp relative to the epoch
def get_timestamp():
	return time.time() - t0
  
start_epoch()