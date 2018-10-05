import gps

# Listen on port 2947 (gpsd) of localhost
session = gps.gps("localhost", "2947")
session.stream(gps.WATCH_ENABLE | gps.WATCH_NEWSTYLE)

def _get():
	return session.next()

setattr(gps, 'get', _get)

print("Ultimate GPS is object 'gps'")
