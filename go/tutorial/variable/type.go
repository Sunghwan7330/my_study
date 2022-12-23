package main

import (
	"fmt"
	"math/cmplx"
	"math"
)

var (
	ToBe bool = false
	MaxInt uint64 = 1 << 64 - 1
	z complex128 = cmplx.Sqrt(-5 + 12i)

)

func main() {
	fmt.Printf("Type : %T Value : %v\n", ToBe, ToBe)
	fmt.Printf("Type : %T Value : %v\n", MaxInt, MaxInt)
	fmt.Printf("Type : %T Value : %v\n", z, z)

	// Casting
	var f float64 = math.Sqrt(float64(3*3 + 4*4))
	fmt.Printf("Type : %T Value : %v\n", f, f)
	fmt.Printf("Type : %T Value : %v\n", int(f), int(f))
}
