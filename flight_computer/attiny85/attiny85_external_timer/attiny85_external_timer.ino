#include <TinyWireS.h>
#define enable_pin 3
#define output_pin 4

// Last updated 11/6/2018

long trigger_time = 0;
long half_wait = 100;
bool is_high = false;
void setup() {
  // put your setup code here, to run once:
  pinMode(enable_pin, INPUT);
  pinMode(output_pin, OUTPUT);
  digitalWrite(output_pin, LOW);
}

void loop() {
  // put your main code here, to run repeatedly:
  if(digitalRead(enable_pin) == HIGH && millis() > trigger_time) {
    if(is_high)
      digitalWrite(output_pin, LOW);
    else
      digitalWrite(output_pin, HIGH);
    is_high = !is_high;

    trigger_time += half_wait;
  }
}
