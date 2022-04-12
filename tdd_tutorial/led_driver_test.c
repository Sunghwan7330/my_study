#include <stdarg.h>
#include <stddef.h>
#include <setjmp.h>
#include <cmocka.h>

#include <stdint.h>

#include "led_driver.h"

/* These functions will be used to initialize
   and clean resources up after each test run */
int setup (void ** state) {
  print_message("CMocka setup\n");
  return 0;
}

int teardown (void ** state) {
  print_message("CMocka teardown\n");
  return 0;
}

void LedsOffAfterCreate(void ** state) {
  uint16_t virtualLeds = 0xffff;
  LedDriver_Create(&virtualLeds);
  assert_int_equal(0, virtualLeds);
}


int main (void) {
    const struct CMUnitTest tests [] =
    {
        cmocka_unit_test (LedsOffAfterCreate),
    };

    /* If setup and teardown functions are not
       needed, then NULL may be passed instead */

    int count_fail_tests =
        cmocka_run_group_tests (tests, setup, teardown);

    return count_fail_tests;
}