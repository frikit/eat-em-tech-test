package org.github

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.github.consumer.StreamConsumer
import org.github.provider.SocketProvider
import org.github.queue.BufferList
import org.github.repository.MongoRepository
import org.github.service.JsonTransformerService
import org.github.service.TypesTransformerService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.exitProcess


fun main() {
    val executorService = Executors.newFixedThreadPool(10)
    println("Start application!")

    val job = GlobalScope.launch {
        startReaderLogic()
    }

    executorService.submit {
        startConsumerLogic()
    }

    if (!job.isActive) {
        println("BB!")
        println(BufferList.list.size)
        println(BufferList.list.take(10))
        exitProcess(0)
    }
}

private fun startConsumerLogic() {
    println("Start queue consumer thread!")
    while (true) {
        val rawLine = BufferList.getAndRemove()

        //backpressure
        val times = AtomicInteger()
        if (rawLine == null && times.get() >= 3) {
            Thread.sleep(100)
            times.set(0)
        }

        if (rawLine != null) {
            println(rawLine)
            val dataObject = TypesTransformerService.transform(rawLine)
            val json = JsonTransformerService.transformToJson(dataObject)
            println(json)

            MongoRepository.saveUpdate(dataObject)

//            if (dataObject is Event) {
//                println(MongoRepository.getEvent(dataObject.eventId))
//            }
        }
    }
}

private fun startReaderLogic() {
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
