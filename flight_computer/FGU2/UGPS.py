import FGU, gps, time
from objdict import ObjDict

ugps_uart_object = gps.gps("localhost", "2947")
ugps_uart_object.stream(gps.WATCH_ENABLE | gps.WATCH_NEWSTYLE)

class UGPS_Sensor():
  def update():
    UGPS_Sensor.raw_packet = ugps_uart_object.next()
    UGPS_Sensor.data = ObjDict()
    UGPS_Sensor.data = FGU.get_timestamp()




ugps = UGPS_Sensor()

### Redo everything with adafruit_gps