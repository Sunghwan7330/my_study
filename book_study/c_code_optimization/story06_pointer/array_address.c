#include<stdio.h>

int main() {
  int i;
  int a[3] = {1, 2, 3};
  int p, q;

  for(i=0; i<3; i++)  printf("a[%d] address : %p\n", i, &a[i]);
 
  printf("\n1) a = %p, &a : %p\n", a, &a);
  printf("2) a+1 = %p, &a+1 = %p\n\n", a+1, &a+1);

  p = &a+1;
  q = &a;
  printf("(&a+1) - a = %d\n", p-q);
  printf("sizeof(a) : %d\n", sizeof(a));

 
  return 0;
}
