package org.github.provider

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket

class SocketProvider(private val host: String, private val port: Int) : Provider {

    private var reader: BufferedReader? = null

    override fun getProvider(): BufferedReader {
        if (reader == null) {
            synchronized(this) {
                if (reader == null) {
                    val socket = Socket(host, port)

                    reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                }
            }
        }

        return reader!!
    }

    override fun closeQuietly() {
        if (reader != null) {
            synchronized(this) {
                if (reader != null) {
                    reader?.close()
                    reader = null
                }
            }
        }
    }

}
