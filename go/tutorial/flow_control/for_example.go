package main

import "fmt"

func sum1() {
	sum := 0
	for i:= 0; i<10; i++ {
		sum += i
	}
	fmt.Println(sum)

}

func sum2() {
	sum := 1
	for ; sum < 1000; {
		sum += sum
	}
	fmt.Println(sum)
}

func main() {
	sum1()
	sum2()
}
