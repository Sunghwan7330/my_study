#include <stdarg.h>
#include <stddef.h>
#include <setjmp.h>
#include <cmocka.h>

#include <stdint.h>

#include "led_driver.h"

uint16_t virtualLeds;

/* These functions will be used to initialize
   and clean resources up after each test run */
int setup (void ** state) {
  LedDriver_Create(&virtualLeds);
  return 0;
}

int teardown (void ** state) {
  return 0;
}

void LedsOffAfterCreate(void ** state) {
  virtualLeds = 0xffff;
  LedDriver_Create(&virtualLeds);
  assert_int_equal(0, virtualLeds);
}

void TurnOnLedOne(void ** state) {
  LedDriver_TurnOn(1);
  assert_int_equal(1, virtualLeds);
}

void TurnOnLedOff(void ** state) {
  LedDriver_TurnOn(1);
  LedDriver_TurnOff(1);
  assert_int_equal(0, virtualLeds);
}

void TurnOnMultipleLeds (void ** state) {
  LedDriver_TurnOn(9);
  LedDriver_TurnOn(8);
  assert_int_equal(0x180, virtualLeds);
}

void TurnOffAnyLed (void ** state) {
  LedDriver_TurnAllOn();
  LedDriver_TurnOff(8);
  assert_int_equal(0xff7f, virtualLeds);
}

void AllOn (void ** state) {
  LedDriver_TurnAllOn();
  assert_int_equal(0xffff, virtualLeds);
}

void LedMemoryIsNotReadable (void ** state) {
  virtualLeds = 0xffff;
  LedDriver_TurnOn(8);
  assert_int_equal(0x80, virtualLeds);
}

int main (void) {
  const struct CMUnitTest tests [] =
    {
      cmocka_unit_test_setup_teardown (LedsOffAfterCreate, setup, teardown),
      cmocka_unit_test_setup_teardown (TurnOnLedOne, setup, teardown),
	  cmocka_unit_test_setup_teardown (TurnOnLedOff, setup, teardown),
	  cmocka_unit_test_setup_teardown (TurnOnMultipleLeds, setup, teardown),
	  cmocka_unit_test_setup_teardown (TurnOffAnyLed, setup, teardown),
	  cmocka_unit_test_setup_teardown (AllOn, setup, teardown),
	  cmocka_unit_test_setup_teardown (LedMemoryIsNotReadable, setup, teardown),
    };

  /* If setup and teardown functions are not
    needed, then NULL may be passed instead */

  int count_fail_tests = cmocka_run_group_tests (tests, setup, teardown);

  return count_fail_tests;
}