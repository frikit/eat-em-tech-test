package org.github.model

sealed class Types

object None : Types()

data class Event(
    val header: Header,
    val eventId: String,
    val category: String,
    val subCategory: String,
    val name: String,
    val startTime: String,
    val displayed: Boolean,
    val suspended: Boolean,
    var markets: MutableList<Market> = mutableListOf()
) : Types() {

    companion object {
        fun fromString(header: Header, body: List<String>): Event {
            require(body.size == 7) { "Event body can contain only 7 fields!" }
            val eventId: String = body[0]
            val category: String = body[1]
            val subCategory: String = body[2]
            val name: String = body[3]
            val startTime: String = body[4]
            val displayed: Boolean = body[5] == "1"
            val suspended: Boolean = body[6] == "1"
            return Event(header, eventId, category, subCategory, name, startTime, displayed, suspended)
        }
    }
}

data class Market(
    val header: Header,
    val eventId: String,
    val marketId: String,
    val name: String,
    val displayed: Boolean,
    val suspended: Boolean,
    val outcomes: MutableList<Outcome> = mutableListOf()
) : Types() {

    companion object {
        fun fromString(header: Header, body: List<String>): Market {
            require(body.size == 5) { "Market body can contain only 5 fields!" }
            val eventId: String = body[0]
            val marketId: String = body[1]
            val name: String = body[2]
            val displayed: Boolean = body[3] == "1"
            val suspended: Boolean = body[4] == "1"
            return Market(header, eventId, marketId, name, displayed, suspended)
        }
    }
}

data class Outcome(
    val header: Header,
    val marketId: String,
    val outcomeId: String,
    val name: String,
    val price: String,
    val displayed: Boolean,
    val suspended: Boolean
) : Types() {

    companion object {
        fun fromString(header: Header, body: List<String>): Outcome {
            require(body.size == 6) { "Outcome body can contain only 6 fields!" }
            val marketId: String = body[0]
            val outcomeId: String = body[1]
            val name: String = body[2]
            val price: String = body[3]
            val displayed: Boolean = body[4] == "1"
            val suspended: Boolean = body[5] == "1"
            return Outcome(header, marketId, outcomeId, name, price, displayed, suspended)
        }
    }
}
