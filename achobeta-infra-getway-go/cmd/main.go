package main

import (
	_ "ab-getway/config"
	"time"
)

func main() {
	for {
		time.Sleep(1000 * time.Second)
	}
}
