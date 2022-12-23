package main

import "fmt"

var flag bool = true
var str1, str2 string = "string1", "string2"

func main() {
	var i int = 1
	var c, python, java = true, false, "no!"

	fmt.Println(flag)
	fmt.Println(str1, str2)
	fmt.Println(i, c, python, java)

	a, b, c := 1, true, "no"
	fmt.Println(a, b, c)
}
