import time
import _thread as thread

import RPi.GPIO as GPIO
GPIO.setmode(GPIO.BCM)

GPIO.setup(24, GPIO.OUT)
GPIO.output(24, GPIO.LOW)
GPIO.setup(23, GPIO.IN)

counter = 0
do_loop = False


def irpt_loop():
  global counter
  while do_loop:
    GPIO.wait_for_edge(23, GPIO.RISING)
    counter += 1
    time.sleep(0.01)
  return


def start():
  global do_loop
  do_loop = True
  thread.start_new_thread(irpt_loop,())
  print('started')
  time.sleep(0.1)
  GPIO.output(24, GPIO.HIGH)

def stop():
  global do_loop
  do_loop = False
  time.sleep(0.2)
  GPIO.output(24, GPIO.LOW)

def test(t):
  global counter
  counter = 0
  start()
  time.sleep(t)
  stop()
  print(counter)
