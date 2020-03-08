package org.github.consumer

import org.github.helpers.provider.StringProvider
import org.github.queue.BufferList
import org.junit.jupiter.api.Test

internal class StreamConsumerTest {

    val provider = StringProvider()

    @Test
    fun `test all messages is consumed`() {
        StreamConsumer(provider).consume()

        val expected = 5
        val actual = BufferList.list.size
        assert(actual == expected) { "In git ignore should be $expected lines but found $actual" }

        val actuals = mutableListOf<String?>()
        while (BufferList.list.isNotEmpty()) {
            actuals.add(BufferList.getAndRemove())
        }

        assert(actuals.size == expected) { "In git ignore should be $expected lines but found $actual" }
    }
}
