#ifndef LED_DRIVER_HEADER
#define LED_DRIVER_HEADER

#include <stdio.h>
#include <stdint.h>
#include <stdbool.h>

void LedDriver_Create(uint16_t * address);
void LedDriver_Destroy(void);
void LedDriver_TurnOn(int ledNumber);
void LedDriver_TurnOff(int ledNumber);
void LedDriver_TurnAllOn();
bool LedDriver_IsOn(int ledNumber);
bool LedDriver_IsOff(int ledNumber);

#endif