#ifndef RUNTIME_HEADER
#define RUNTIME_HEADER

#include "RuntimeErrorStub.h"

#define RUNTIME_ERROR(description, parameter)\
 RuntimeError(description, parameter, __FILE__, __LINE__)

#endif