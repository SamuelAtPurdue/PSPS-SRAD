import time
import RPi.GPIO as GPIO
import _thread as thread
GPIO.setmode(GPIO.BCM)

_output_pin = 0
_input_pin = 1
_is_setup = False
_do_loop = False
_time_elapsed = 0

_counter = 0

def is_setup():
  return _is_setup
  
def setup(output_pin, input_pin):
  global _output_pin, _input_pin, _is_setup
  _output_pin = output_pin
  _input_pin = input_pin
  _is_setup = True
  
def _timer_loop():
  global _counter, _time_elapsed
  start_time = time.time()
  last_time = start_time
  _counter = 0
  while _do_loop:
    time.sleep(0.01)
    GPIO.wait_for_edge(_input_pin, GPIO.RISING)
    if _do_loop:
      _counter += 1
      last_time = time.time()
  _time_elapsed = last_time - start_time


def test(time_delay):
  if _is_setup == False:
    raise AttributeError('Benchmark IO pins have not been setup.')
    return
  else:
    global _do_loop, _time_elapsed
    GPIO.output(_output_pin, GPIO.HIGH)
    time.sleep(0.1)
    _do_loop = True
    thread.start_new_thread(_timer_loop,())
    time.sleep(time_delay)
    _do_loop = False
    time.sleep(0.1)  
    print('Timer benchmarked at {} cycles over {} seconds'.format(
          _counter, _time_elapsed))
