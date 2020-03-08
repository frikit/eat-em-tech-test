package org.github.helpers.provider

import org.github.provider.Provider
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class StringProvider : Provider {
    override fun getProvider(): BufferedReader {
        var file: File? = null
        File(".")
            .walkBottomUp()
            .maxDepth(10)
            .forEach {
                if (it.name == "test.data") {
                    println("Found file 'test.data'")
                    file = it
                }
            }
        return BufferedReader(FileReader(file!!))
    }

    override fun closeQuietly() {
    }
}
