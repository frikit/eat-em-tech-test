package org.github.queue

import java.util.concurrent.ArrayBlockingQueue

//TODO rewrite with in memory queue of some kind or real message queue
object BufferList {
    @Volatile
    var list: ArrayBlockingQueue<String> = ArrayBlockingQueue(9999)


    fun add(element: String) {
        list.add(element)
    }

    fun getAndRemove(): String? {
        return try {
            val element: String? = list.element()
            if (element != null) list.remove(element)
            element
        } catch (exp: NoSuchElementException) {
            null
        }
    }
}
