package org.github.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.github.model.Types

object JsonTransformerService {
    val gson: Gson = GsonBuilder().serializeNulls().create()

    fun transformToJson(dataObject: Types): String {
        return gson.toJson(dataObject)
    }

    inline fun <reified T : Any> transformFromJson(dataObject: String): T {
        return gson.fromJson(dataObject, T::class.java)
    }
}
