#ifndef RUNTIME_ERROR_STUB_HEADER
#define RUNTIME_ERROR_STUB_HEADER

void RuntimeErrorStub_Reset(void); 
const char * RuntimeErrorStub_GetLastError(void); 
int RuntimeErrorStub_GetLastParameter(void);
void RuntimeError(const char * m, int p, const char * f, int l);

#endif