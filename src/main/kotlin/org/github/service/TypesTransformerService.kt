package org.github.service

import org.github.model.*
import java.util.regex.Pattern

object TypesTransformerService {

    private val pattern: Regex = Pattern.compile("(?<!\\\\)\\|").toRegex()

    fun split(rawLine: String): List<String> {
        return rawLine.split(pattern).dropLast(1).drop(1)
    }

    fun transform(rawLine: String): Types {
        val dataSplit = split(rawLine)
        val header = Header.fromString(dataSplit.take(4))
        val body = dataSplit.drop(4)

        return when (header.type) {
            "event" ->
                Event.fromString(header, body)
            "market" ->
                Market.fromString(header, body)
            "outcome" ->
                Outcome.fromString(header, body)
            else ->
                None
        }
    }
}
