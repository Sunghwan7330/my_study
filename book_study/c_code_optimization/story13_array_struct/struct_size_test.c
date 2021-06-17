#include <stdio.h>

struct Test1 {
  char a;
  int b;
  char c;
  int d;
};

struct Test2 {
  char a;
  char c;
  int b;
  int d;
};

void main() {
  printf("Test1 size : %d\n", sizeof(struct Test1));
  printf("Test2 size : %d\n", sizeof(struct Test2));
}
