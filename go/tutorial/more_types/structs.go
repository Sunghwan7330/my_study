package main

import "fmt"

type Vertex struct {
	X int
	Y int
}

func main() {
	v := Vertex{1, 2}
	v.X = 4
	fmt.Println(v)
	fmt.Println(v.X)

	p := &v
	p.X = 1e9
	fmt.Println(v)

	v1 := Vertex{1, 2}
	v2 := Vertex{X : 1}
	v3 := Vertex{}

	fmt.Println(v1, v2, v3, p)
}
