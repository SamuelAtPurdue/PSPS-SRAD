
typedef struct t  {
    unsigned long tStart;
    unsigned long tTimeout;
};

t pin_on = {0, 100};
t pin_off = {50, 100};

bool tCheck (struct t *T ) {
  if (millis() > T->tStart + T->tTimeout) return true;
  else return false;    
}

void tRun (struct t *T) {
    T->tStart = millis();
}

bool do_loop = false;
int ms_delay = 100;

void start_cycler() {
  pin_on.tTimeout = ms_delay;
  pin_off.tTimeout = ms_delay;
  pin_on.tStart = millis();
  pin_off.tStart = millis() + ms_delay / 2;

}




void setup() {
  // put your setup code here, to run once:

  pinMode(1, OUTPUT);
  pinMode(4, INPUT);
  digitalWrite(1, HIGH);
  delay(1000);
  digitalWrite(1, LOW);
}

void loop() {
  // put your main code here, to run repeatedly:
  if(digitalRead(4) && do_loop == false) {
    start_cycler();
    do_loop = true;
  }

  if(digitalRead(4) == 0 && do_loop) {
    do_loop = false;
    digitalWrite(1, LOW);
  }

  if(do_loop && tCheck(&pin_on)) {
    digitalWrite(1, HIGH);
    delayMicroseconds(2000);
    tRun(&pin_on);
  }
  
  if(do_loop && tCheck(&pin_off)) {
    digitalWrite(1, LOW);
    delayMicroseconds(2460);
    tRun(&pin_off);
  }
}
