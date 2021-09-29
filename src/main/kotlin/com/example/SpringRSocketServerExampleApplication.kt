package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringRSocketServerExampleApplication

fun main(args: Array<String>) {
    runApplication<SpringRSocketServerExampleApplication>(*args)
}
