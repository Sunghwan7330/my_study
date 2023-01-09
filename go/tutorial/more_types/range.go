package main

import "fmt"

var pow = []int{1, 2, 4, 8, 16, 32, 64, 128}

func range_example() {
	for i, v := range pow {
		fmt.Printf("2**%d = %d\n", i, v)
	}
}

func range_continued() {
	pow := make([]int, 10)

	for i := range pow {
		pow[i] = 1 << uint(i)
	}

	for _, value := range pow {
		fmt.Printf("%d\n", value)
	}
}

func main() {
	//range_example()
	range_continued()
}
