import time
import _thread as thread

import RPi.GPIO as GPIO
GPIO.setmode(GPIO.BCM)

counter = 0
do_loop = False
is_setup = False
_output_pin = 0
_input_pin = 0

_target_fct = default_target_fct

def default_target_fct():
  print("'default_target_fct' output")

def setup_check():
  if is_setup == False:
    print('Error: External Timer not setup')
    return False
  else:
    return True

def irpt_loop():
  if setup_check() == False:
    return
    
  global counter
  while do_loop:
    GPIO.wait_for_edge(_input_pin, GPIO.RISING)
    counter += 1
    time.sleep(0.01)
  return


def activate():
  if setup_check() == False:
    return
  
  global do_loop
  do_loop = True
  thread.start_new_thread(irpt_loop,())
  print('started')
  time.sleep(0.1)
  GPIO.output(_output_pin, GPIO.HIGH)

def deactivate():
  if setup_check() == False:
    return
  
  global do_loop
  do_loop = False
  time.sleep(0.2)
  GPIO.output(_output_pin, GPIO.LOW)

def test(t):
  if setup_check() == False:
    return
  
  global counter
  counter = 0
  activate()
  time.sleep(t)
  deactivate()
  print(counter)

def setup(output_pin, input_pin, target_fct = default_target_fct):
  global _output_pin, _input_pin
  _output_pin = output_pin
  _input_pin = input_pin
  GPIO.setup(output_pin, GPIO.OUT)
  GPIO.output(output_pin, GPIO.LOW)
  GPIO.setup(input_pin, GPIO.IN)
  
  FGU.import_timer_fcts(activate, deactivate)