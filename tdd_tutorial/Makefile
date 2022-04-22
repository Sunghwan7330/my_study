CC = gcc

LIB_CMOCKA=-lcmocka

LED_DRIVER=led_driver
LED_TEST=led_driver_test
ERROR_ETUB=RuntimeErrorStub

all : $(LED_TEST)

$(LED_DRIVER).o:
	$(CC) -c $(LED_DRIVER).c

$(ERROR_ETUB).o:
	$(CC) -c $(ERROR_ETUB).c
	
$(LED_TEST) : $(LED_DRIVER).o $(ERROR_ETUB).o
	$(CC) -g  -o $(LED_TEST) $(LED_DRIVER).o $(ERROR_ETUB).o $(LED_TEST).c $(LIB_CMOCKA)



clean :
	rm -f $(LED_TEST) *.o
