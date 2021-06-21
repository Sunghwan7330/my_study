#include <stdio.h>

void main(){
  int x, y, z, k;
  x = z = 10, y = k = 50;
  y %= 12; // y와 k는 모두 12로 나눈 나머지 값을 갖는다
  k &= 3;
  x /= 8; // x와 Z는 모두 8로 나눈 몫을 값으로 갖는다.
  z >>= 3;
  printf("x=%d, z=%d, y=%d, k=%d \n", x, z, y, k);
}
