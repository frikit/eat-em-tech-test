package org.github

import org.github.consumer.StreamConsumer
import org.github.provider.SocketProvider

fun main() {
    println("Start application!")

    val host = "localhost"
    val port = 8282

    val provider = SocketProvider(host, port)

    try {
        StreamConsumer(provider).consume()
    } finally {
        provider.closeQuietly()
    }

    println("BB!")
}
