CC = gcc

LIB_CMOCKA=-lcmocka

LED_DRIVER=led_driver
LED_TEST=led_driver_test

all : $(LED_TEST)

$(LED_DRIVER).o:
	$(CC) -c $(LED_DRIVER).c

$(LED_TEST) : $(LED_DRIVER).o
	$(CC) -g  -o $(LED_TEST) $(LED_DRIVER).o $(LED_TEST).c $(LIB_CMOCKA)
 
clean :
	rm -f $(LED_TEST) $(LED_DRIVER).o