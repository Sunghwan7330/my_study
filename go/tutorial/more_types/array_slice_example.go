package main

import (
	"fmt"
)

func printSlice(s []int) {
	fmt.Printf("len : %d, cap : %d, %v\n", len(s), cap(s), s)
}

func slice_ex1() {
	primes := [6]int{2, 3, 5, 7, 11, 13}

	var s []int = primes[1:4]
	fmt.Println(s)
}

func slice_ex2() {
	names := [4]string {
		"John",
		"Paul",
		"George",
		"Ringo",
	}
	fmt.Println(names)

	a := names[0:2]
	b := names[1:3]
	fmt.Println(a, b)

	b[0] = "xxx"
	fmt.Println(a, b)
	fmt.Println(names)
}

func slice_ex3() {
	q := []int{2, 3, 5, 7, 11, 13}
	fmt.Println(q)

	r := []bool{true, false, true, false}
	fmt.Println(r)

	s := []struct {
		i int
		b bool
	} {
		{2, true}, 
		{3, false}, 
		{5, true},
	}
	fmt.Println(s)
}

func slice_ex4() {
	s := []int{2, 3, 5, 7, 11, 13}
	printSlice(s)

	s = s[:0]
	printSlice(s)

	s = s[:4]
	printSlice(s)
	
	s = s[2:]
	printSlice(s)

	s = s[2:4]
	printSlice(s)

}

func nil_slice() {
	var s []int
	fmt.Println(s, len(s), cap(s))

	if s == nil {
		fmt.Println("nil!")
	}
}

func main() {
	// slice_ex4()
	nil_slice()
}
