package org.github.helpers

import org.github.model.*

fun assertHeader(header: Header, expected: List<String>) {
    assert(header.msgId.toString() == expected[0]) { "msgId is not the same!" }
    assert(header.operation == expected[1]) { "operation is not the same!" }
    assert(header.type == expected[2]) { "type is not the same!" }
    assert(header.timestamp.toString() == expected[3]) { "timestamp is not the same!" }
}

fun assertEvent(event: Event, expected: List<String>) {
    assert(event.eventId == expected[0]) { "eventId is not the same!" }
    assert(event.category == expected[1]) { "category is not the same!" }
    assert(event.subCategory == expected[2]) { "subCategory is not the same!" }
    assert(event.name == expected[3]) { "name is not the same!" }
    assert(event.startTime == expected[4]) { "startTime is not the same!" }
    assert(event.displayed == (expected[5] == "1")) { "displayed is not the same!" }
    assert(event.suspended == (expected[6] == "1")) { "suspended is not the same!" }
}

fun assertMarket(market: Market, expected: List<String>) {
    assert(market.eventId == expected[0]) { "eventId is not the same!" }
    assert(market.marketId == expected[1]) { "marketId is not the same!" }
    assert(market.name == expected[2]) { "name is not the same!" }
    assert(market.displayed == (expected[3] == "1")) { "displayed is not the same!" }
    assert(market.suspended == (expected[4] == "1")) { "suspended is not the same!" }
}

fun assertOutcome(outcome: Outcome, expected: List<String>) {
    assert(outcome.marketId == expected[0]) { "eventId is not the same!" }
    assert(outcome.outcomeId == expected[1]) { "category is not the same!" }
    assert(outcome.name == expected[2]) { "subCategory is not the same!" }
    assert(outcome.price == expected[3]) { "name is not the same!" }
    assert(outcome.displayed == (expected[4] == "1")) { "displayed is not the same!" }
    assert(outcome.suspended == (expected[5] == "1")) { "suspended is not the same!" }
}

fun assertObject(types: Types, expected: List<String>) {
    when (types) {
        is Event -> {
            assertHeader(types.header, expected.take(4))
            assertEvent(types, expected.drop(4))
        }
        is Market -> {
            assertHeader(types.header, expected.take(4))
            assertMarket(types, expected.drop(4))
        }
        is Outcome -> {
            assertHeader(types.header, expected.take(4))
            assertOutcome(types, expected.drop(4))
        }
        else -> {
            assert(types is None) { "Should be an unknown object!" }
        }
    }
}
