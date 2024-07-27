package org.jh.batchprocess

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BatchProcessApplication

fun main(args: Array<String>) {
    runApplication<BatchProcessApplication>(*args)
}
