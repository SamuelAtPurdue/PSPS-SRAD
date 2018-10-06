import gps as UGPS

def _update(self):
  self.data = self.next()

setattr(UGPS, 'update', _update)

def _get(self, new = 0):
  if new == 1:
    self.update()
    return self.data()

setattr(UGPS, 'get', _get)

# Listen on port 2947 (gpsd) of localhost
gps = UGPS.gps("localhost", "2947")
gps.stream(UGPS.WATCH_ENABLE | UGPS.WATCH_NEWSTYLE)

print("Ultimate GPS is object 'gps'")
