package main

import (
	"fmt"
	"runtime"
)

func printOsInfo() {
	fmt.Print("Go runs on ")
	switch os := runtime.GOOS; os {
	case "darwin":
		fmt.Println("OS X")
	case "linux":
		fmt.Println("Linux.")
	default:
		// etc
		fmt.Printf("%s.\n", os)
	}

}

func main() {
	printOsInfo()
}
