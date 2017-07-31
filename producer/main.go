package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	emitLogs()
}

func emitLogs() {
	randomTimeToSleep := time.Duration(rand.Intn(1000))

	for {
		fmt.Println(NewLogLine())
		time.Sleep(randomTimeToSleep * time.Millisecond)
	}
}
