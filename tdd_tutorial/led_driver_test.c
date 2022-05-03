#include <stdarg.h>
#include <stddef.h>
#include <setjmp.h>
#include <cmocka.h>

#include <stdint.h>

#include "led_driver.h"
#include "RuntimeErrorStub.h"

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

void UpperAndLowerBounds (void** state) {
  LedDriver_TurnOn(1);
  LedDriver_TurnOn(16);
  assert_int_equal(0x8001, virtualLeds);
}

void OutOfBoundsChangesNothing (void** state) {
  LedDriver_TurnOn(-1);
  LedDriver_TurnOn(0);
  LedDriver_TurnOn(1);
  LedDriver_TurnOn(2);
  LedDriver_TurnOn(17);
  LedDriver_TurnOn(33);
  LedDriver_TurnOn(3141);
  assert_int_equal(3, virtualLeds);
}

void OutOfBoundsTurnOffDoesNoHarm (void** state) {
  LedDriver_TurnAllOn();
  LedDriver_TurnOff(-1);
  LedDriver_TurnOff(0);
  LedDriver_TurnOff(17);
  LedDriver_TurnOff(3141);
  assert_int_equal(0xffff, virtualLeds);
}

void OutOfBoundsProducesRuntimeError (void** state) {
  LedDriver_TurnOn(-1);
  assert_string_equal("LED Driver: out-of-bounds LED", RuntimeErrorStub_GetLastError());
  assert_int_equal(-1, RuntimeErrorStub_GetLastParameter());
}

void IsOn(void** state) {
  assert_int_equal(0, (LedDriver_IsOn(11)));
  LedDriver_TurnOn(11);
  assert_int_equal(1, (LedDriver_IsOn(11)));
}

void OutOfBoundsLedsAreAlwaysOff (void** state) {
	assert_int_equal(1, LedDriver_IsOff(0));
	assert_int_equal(1, LedDriver_IsOff(17));
	assert_int_equal(0, LedDriver_IsOn(0));
	assert_int_equal(0, LedDriver_IsOn(17));
}

void IsOff (void** state) {
  assert_int_equal(1, LedDriver_IsOff(12));
  LedDriver_TurnOn(12);
  assert_int_equal(0, LedDriver_IsOff(12));
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
	  cmocka_unit_test_setup_teardown (UpperAndLowerBounds, setup, teardown),
	  cmocka_unit_test_setup_teardown (OutOfBoundsChangesNothing, setup, teardown),
	  cmocka_unit_test_setup_teardown (OutOfBoundsTurnOffDoesNoHarm, setup, teardown),
	  cmocka_unit_test_setup_teardown (OutOfBoundsProducesRuntimeError, setup, teardown),
	  cmocka_unit_test_setup_teardown (IsOn, setup, teardown),
	  cmocka_unit_test_setup_teardown (OutOfBoundsLedsAreAlwaysOff, setup, teardown),
	  cmocka_unit_test_setup_teardown (IsOff, setup, teardown),
    };

  /* If setup and teardown functions are not
    needed, then NULL may be passed instead */

  int count_fail_tests = cmocka_run_group_tests (tests, setup, teardown);

  return count_fail_tests;
}