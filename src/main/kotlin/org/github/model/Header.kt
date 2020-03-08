package org.github.model

data class Header(
    val msgId: Long,
    val operation: String,
    val type: String,
    val timestamp: Long
) {

    companion object {
        fun fromString(dataSplit: List<String>): Header {
            require(dataSplit.size == 4) { "Header can contain only 4 fields!" }

            val msgId = dataSplit[0].toLong()
            val operation = dataSplit[1]
            val type = dataSplit[2]
            val timestamp = dataSplit[3].toLong()
            return Header(msgId, operation, type, timestamp)
        }
    }
}
