int argTest1(int a, int b, int c, int d) {
  return a+b+c+d;
}

int argTest2 (int a, int b, int c, int d, int e, int f) {
  return a+b+c+d+e+f;
}
 
void main() {
  int s = 0;
  s = argTest1 (1, 2, 3, 4);
  s = argTest2 (1, 2, 3, 4, 5, 6);
}
