char f1 (char a) {
  return a + 1;
}

short f2 (short b) {
  return b + 1;
}

int f3 (int c) {
  return c + 1;
}

void main() {
  char a;
  short b;
  int c;

  a = f1(1);
  b = f2(2);
  c = f3(3);
}
