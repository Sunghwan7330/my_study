#include <stdio.h>

void test1() {
  int a, b, c, d, x = 0;
  a = b = 3;
  c = 5;
  d = 6;
  if(((a*c)+b)/d == 0)
    x = ((a*c)+b)/d + 5;
  else if (((a*c)+b)/d == 1)
    x = ((a*c)+b)/d + 10;
  else if (((a*c)+b)/d == 2)
    x = ((a*c)+b)/d + 15;
  else if (((a*c)+b)/d == 3)
    x = ((a*c)+b)/d + 20;
  else if (((a*c)+b)/d == 4)
    x = ((a*c)+b)/d + 25;
  else
    x = 0;
  printf("%d\n", x);
}

void test2() {
  int a, b, c, d, x = 0, t;
  a = b = 3;
  c = 5;
  d = 6;
  t = ((a*c)+b)/d;

  if(t == 0)
    x = t + 5;
  else if (t == 1)
    x = t + 10;
  else if (t == 2)
    x = t + 15;
  else if (t == 3)
    x = t + 20;
  else if (t == 4)
    x = t + 25;
  else
    x = 0;
  printf("%d\n", x);
}

void test3() {
  int a, b, c, d, x = 0, t;
  a = b = 3;
  c = 5;
  d = 6;
  t = ((a*c)+b)/d;
  
  switch(t) {
    case 0:
	  x = t + 5;
	  break;
    case 1:
	  x = t + 10;
	  break;
    case 2:
	  x = t + 15;
	  break;
    case 3:
	  x = t + 20;
	  break;
    case 4:
	  x = t + 25;
	  break;
    default:
	  x = 0;
	  break;
  }
  printf("%d\n", x);
}

void main() {
  test1();
  test2();
  test3();
}
