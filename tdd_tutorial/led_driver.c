#include "led_driver.h"
#include "RuntimeError.h"

enum {ALL_LEDS_ON = ~0, ALL_LEDS_OFF = ~ALL_LEDS_ON};
enum {FIRST_LED = 1, LAST_LED = 16};

uint16_t * ledsAddress;
static uint16_t ledsImage;

static bool IsLedOutOfBounds(int ledNumber) {
  return (ledNumber < FIRST_LED) || (ledNumber > LAST_LED);
}

static void updateHardware(void) {
  *ledsAddress = ledsImage;
}

void LedDriver_Create(uint16_t * address) {
  ledsAddress = address;
  ledsImage = ALL_LEDS_OFF;
  updateHardware();
}

void LedDriver_Destroy(void) {
}

static uint16_t convertLedNumberToBit(int ledNumber) {
  return 1 << (ledNumber - 1);
}

static void setLedImageBit(int ledNumber) {
  ledsImage |= convertLedNumberToBit(ledNumber);
}

static void clearLedImageBit(int ledNumber) {
  ledsImage &= ~convertLedNumberToBit(ledNumber);
}

void LedDriver_TurnOn(int ledNumber){
  if (IsLedOutOfBounds(ledNumber)) {
    RUNTIME_ERROR("LED Driver: out-of-bounds LED", -1);
    return;
  }

  setLedImageBit(ledNumber);
  updateHardware();
}

void LedDriver_TurnOff(int ledNumber) {
  if (IsLedOutOfBounds(ledNumber)) {
    RUNTIME_ERROR("LED Driver: out-of-bounds LED", -1);
    return;
  }

  clearLedImageBit(ledNumber);
  updateHardware();
}

void LedDriver_TurnAllOn(void) {
  ledsImage = ALL_LEDS_ON;
  updateHardware();
}
bool LedDriver_IsOn(int ledNumber) {
  if (IsLedOutOfBounds(ledNumber))  return false;
  
  return ledsImage & (convertLedNumberToBit(ledNumber));
}

bool LedDriver_IsOff(int ledNumber) {
  return !LedDriver_IsOn(ledNumber);
}