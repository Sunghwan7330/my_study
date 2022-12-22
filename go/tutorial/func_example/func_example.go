package main

import "fmt"

func add(a int, b int) int {
	return a + b
}

func add2(a, b int) int {
	return a + b
}

func main() {
	fmt.Println(add(12, 34))
	fmt.Println(add2(12, 34))
}
