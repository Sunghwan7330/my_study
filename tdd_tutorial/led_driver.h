#ifndef LED_DRIVER_HEADER
#define LED_DRIVER_HEADER

#include <stdio.h>
#include <stdint.h>

void LedDriver_Create(uint16_t * address);
void LedDriver_Destroy(void);

#endif