import board
import busio
import adafruit_bmp280
i2c = busio.I2C(board.SCL, board.SDA)
bmp = adafruit_bmp280.Adafruit_BMP280_I2C(i2c)

print("BMP280 is object 'bmp'")
