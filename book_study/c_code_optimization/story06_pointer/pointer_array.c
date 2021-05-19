#include<stdio.h>

int main() {
  int a[] = {1, 2, 3};
  int c = 100;
  int *b = &c;
  a = b;
  return 0;
}
