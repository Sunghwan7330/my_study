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
  print_message("CMocka setup\n");
  return 0;
}

int teardown (void ** state) {
  print_message("CMocka teardown\n");
  return 0;
}

void LedsOffAfterCreate(void ** state) {
  virtualLeds = 0xffff;
  LedDriver_Create(&virtualLeds);
  assert_int_equal(0, virtualLeds);
}

void TurnOnLedOn(void ** state) {
  LedDriver_TurnOn(1);
  assert_int_equal(1, virtualLeds);
}

void TurnOnLedOff(void ** state) {
  LedDriver_TurnOn(1);
  LedDriver_TurnOff(1);
  assert_int_equal(0, virtualLeds);
}

int main (void) {
  const struct CMUnitTest tests [] =
    {
      cmocka_unit_test (LedsOffAfterCreate),
      cmocka_unit_test (TurnOnLedOn),
	  cmocka_unit_test (TurnOnLedOff),
    };

  /* If setup and teardown functions are not
    needed, then NULL may be passed instead */

  int count_fail_tests = cmocka_run_group_tests (tests, setup, teardown);

  return count_fail_tests;
}