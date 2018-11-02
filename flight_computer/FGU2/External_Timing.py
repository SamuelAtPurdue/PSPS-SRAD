import time
import _thread as thread
import RPi.GPIO as GPIO
GPIO.setmode(GPIO.BCM)

_is_setup = False
_output_pin = None
_input_pin = None
_target_fct = None

_do_loop = False
_tick_counter = 0

_loop_thread_is_live = False

def setup(output_pin, input_pin, target_fct):
  ### Importing global variables
  global _is_setup, _output_pin, _input_pin, _target_fct
  
  ### Setting up output
  _output_pin = output_pin
  GPIO.setup(output_pin, GPIO.OUT)
  GPIO.output(output_pin, GPIO.LOW)
  
  ### Setting up input
  _input_pin = input_pin
  GPIO.setup(input_pin, GPIO.IN)
  
  ### Setting target function to be called
  _target_fct = target_fct
  
  ### Deeming everything as good to go
  _is_setup = True
  
def setup_check():
  if _is_setup == False:
    raise AttributeError('External Timing has not been setup yet!')
    return False
  
  return True
  
def _function_loop():
  if setup_check() == False:
    return
  
  global _loop_thread_is_live, _tick_counter
  _loop_thread_is_live = True
  
  while _do_loop:
    time.sleep(0.01)
    GPIO.wait_for_edge(_input_pin, GPIO.RISING)
    if _do_loop:
      _tick_counter += 1
      _target_fct()
  
  _loop_thread_is_live = False

def get_tick_count():
  return _tick_counter

def activate():
  if _loop_thread_is_live:
    raise RuntimeError('External Timing loop is already active.')
    return 
    
  global _do_loop
  _do_loop = True
  thread.start_new_thread(_function_loop,())
  time.sleep(0.1)
  GPIO.output(_output_pin, GPIO.HIGH)
  print('External Timing loop has started.')

def deactivate():
  if _loop_thread_is_live == False:
    raise RuntimeError('External Timing loop is not already active.')
    return
  
  global _do_loop, _tick_counter
  _do_loop = False
  print('External Timing loop has ended.')
  time.sleep(0.2)
  GPIO.output(_output_pin, GPIO.LOW)
  _tick_counter = 0