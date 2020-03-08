package org.github.service

import org.github.helpers.assertObject
import org.junit.jupiter.api.Test

internal class TypesTransformerServiceTest {

    //valid
    @Test
    fun `transform valid line into event`() {
        val line =
            "|18960|update|event|1583598486052|9b91bcf4-f72f-474e-aa31-a0159950727c|Tennis|US Open|\\|Novak Dojokvic\\| vs \\|Roger Federer\\||1583598486050|1|0|\n"
        val actual = TypesTransformerService.transform(line)
        val expected = TypesTransformerService.split(line)

        assertObject(actual, expected)
    }

    @Test
    fun `transform valid line into market`() {
        val line =
            "|54|create|market|1583658261189|ff93ed9b-0e55-47df-ab3f-bcf26275f03e|f6c1d69c-aeaa-4bc3-b4f3-2076cc9b736f|Goal Handicap (+1)|0|1|\n"
        val actual = TypesTransformerService.transform(line)
        val expected = TypesTransformerService.split(line)

        assertObject(actual, expected)
    }

    @Test
    fun `transform valid line into outcome`() {
        val line =
            "|55|create|outcome|1583658261189|f6c1d69c-aeaa-4bc3-b4f3-2076cc9b736f|81066831-5635-4530-b6c5-b4e8e69a8939|\\|Forest Green\\| +1|1/500|0|1|\n"
        val actual = TypesTransformerService.transform(line)
        val expected = TypesTransformerService.split(line)

        assertObject(actual, expected)
    }

    //invalid
    @Test
    fun `transform invalid line into none`() {
        val line =
            "|55|create|outcome_market|1583658261189|f6c1d69c-aeaa-4bc3-b4f3-2076cc9b736f|81066831-5635-4530-b6c5-b4e8e69a8939|\\|Forest Green\\| +1|1/500|0|1|\n"
        val actual = TypesTransformerService.transform(line)
        val expected = TypesTransformerService.split(line)

        assertObject(actual, expected)
    }
}
