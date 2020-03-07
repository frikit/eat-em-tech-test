package org.github.provider

import java.io.BufferedReader

interface Provider {

    fun getProvider(): BufferedReader

    fun closeQuietly()
}
