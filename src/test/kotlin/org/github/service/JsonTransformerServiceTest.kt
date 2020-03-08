package org.github.service

import org.github.model.Header
import org.github.model.Market
import org.junit.jupiter.api.Test

internal class JsonTransformerServiceTest {

    val header = Header(112L, "undo", "types", 1234567890L)
    val market = Market(header, "121", "122", "market lala", true, false)

    val json = """
            {"header":{"msgId":112,"operation":"undo","type":"types","timestamp":1234567890},"eventId":"121","marketId":"122","name":"market lala","displayed":true,"suspended":false}
    """.trimIndent()

    @Test
    fun `map valid object to json`() {
        val actual = JsonTransformerService.transformToJson(market)
        println(json)
        println(actual)

        assert(actual == json) { "Json are not equal!" }
    }

    @Test
    fun `map valid json to object`() {
        val actual = JsonTransformerService.transformFromJson<Market>(json)

        assert(actual == market) { "Objects are not equal!" }
    }
}
