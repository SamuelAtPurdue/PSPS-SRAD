import FGU, board, busio, digitalio, adafruit_sdcard, storage, os.path, json
from objdict import ObjDict

spi = busio.SPI(board.SCK, MOSI = board.MOSI, MISO = board.MISO)
cs = digitalio.DigitalInOut(board.D10)
sdcard = adafruit_sdcard.SDCard(spi, cs)
vfs = storage.VfsFat(sdcard)
storage.mount(vfs, "/sd")

_file_ext = '.json'
def save(data):
  add_timestamp = not os.path.isfile(FGU.get_epoch_string() + _file_ext)
  with open('/sd/' + FGU.get_epoch_string() + _file_ext, 'a') as f:
    if add_timestamp:
      f.write(FGU.get_epoch())  
      f.write('\n')
    if type(data) == ObjDict:
      f.write(data.dumps())
      f.write('\n')
    else:
      error_string = "Parameter 'data' in save(data) is an invalid type.\n"
      error_string += "  'data' was " + str(type(data)) + '\n'
      error_string += "  instead of " + str(type({})) + ',\n'
      error_string += "          or " + str(type(ObjDict)) + '.\n'
      
      raise TypeError(error_string)
    f.close()
