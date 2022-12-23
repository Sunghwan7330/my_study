package main

import "fmt"

const PI = 3.14

func main() {
	const world = "world"
	fmt.Println("hello", world)
	fmt.Println("Happy", PI, "day")

	const truth  = true
	fmt.Println("Fo rules?", truth)
}
