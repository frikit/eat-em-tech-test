package org.github.consumer

import org.github.provider.Provider

class StreamConsumer(private val from: Provider) : Consumer {
    @Volatile
    var run: Boolean = true

    override fun consume() {
        val reader = from.getProvider()

        while (run) {
            val line = reader.readLine() ?: ""
            if (line == "") {
                run = false
                break
            }

            println(line)
        }
    }


}
