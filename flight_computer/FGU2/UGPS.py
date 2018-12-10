import FGU, board, busio, adafruit_gps, time
from objdict import ObjDict

RX = board.RX
TX = board.TX

class ULTIMATEGPS_Sensor(FGU.Sensor):
    def update(self):
        self._sensor.update()

        self.data = ObjDict()
        self.data.timestamp = self._sensor.timestamp_utc
        self.data.type = 'Ultimate GPS'
        self.data.has_fix = self._sensor.has_fix

        if not self._sensor.has_fix:
          print('Warning: Ultimate GPS module does not have a fix.')
          print('         No GPS data is currently available.')

        else:
          self.data.latitude = self._sensor.latitude
          self.data.longitude = self._sensor.longitude
          self.data.altitude = self._sensor.altitude
          
          self.data.ground_velocity_knots = self._sensor.velocity_knots

          self.data.satellites = self._sensor.satellites
          self.data.fix_quality = self._sensor.fix_quality

    def flight_readiness_check(self):
      return_string =  '         Ultimate GPS is ready for launch.'
      if not self.data.has_fix:
        return_string = 'CAUTION: Ultimate GPS is NOT ready for launch. (NO FIX)'
      if not self._is_setup:
        return_string = 'CAUTION: Ultimate GPS is NOT ready for launch. (NOT SETUP)'
      return self.data.has_fix and self._is_setup, return_string

uart = busio.UART(TX, RX, baudrate = 9600, timeout = 3000)
ultimateGPS_uart_object = adafruit_gps.GPS(uart, debug = False)
ultimateGPS = ULTIMATEGPS_Sensor(ultimateGPS_uart_object)

ultimateGPS_uart_object.send_command(b'PMTK314,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0')
ultimateGPS_uart_object.send_command(b'PMTK220,1000')
