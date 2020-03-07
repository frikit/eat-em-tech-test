package org.github

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.github.consumer.StreamConsumer
import org.github.provider.SocketProvider
import org.github.queue.BufferList
import java.util.concurrent.atomic.AtomicInteger

fun main() {
    println("Start application!")

    GlobalScope.launch {
        println("Start reader thread!")
        val host = "localhost"
        val port = 8282

        val provider = SocketProvider(host, port)

        try {
            StreamConsumer(provider).consume()
        } finally {
            provider.closeQuietly()
        }
    }

    GlobalScope.launch {
        println("Start queue consumer thread!")
        while (true) {
            val times = AtomicInteger()
            val element = BufferList.getAndRemove()
            if (element == null && times.get() >= 3) {
                Thread.sleep(100)
                times.set(0)
            }

            if (element != null) println(element)
        }
    }

    //keep coroutines running
    Thread.sleep(25_000)

    println("BB!")
    println(BufferList.list.size)
    println(BufferList.list.take(10))
}
