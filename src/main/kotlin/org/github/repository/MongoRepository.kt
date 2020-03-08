package org.github.repository

import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import org.github.model.Event
import org.github.model.Market
import org.github.model.Outcome
import org.github.model.Types
import org.litote.kmongo.*

object MongoRepository {

    private val client: MongoClient = KMongo.createClient() //get com.mongodb.MongoClient new instance
    private val database: MongoDatabase = client.getDatabase("feedme") //normal java driver usage

    fun saveUpdate(type: Types) {
        when (type) {
            is Event ->
                saveUpdateEvent(type)
            is Market ->
                saveUpdateMarket(type)
            is Outcome ->
                saveUpdateOutcome(type)
        }
    }

    fun saveUpdateEvent(event: Event) {
        val collection = database.getCollection<Event>("events") //KMongo extension method

        val eventDb = collection.findOne(Event::eventId eq event.eventId)

        if (eventDb == null) {
            println("Insert event [${event.eventId}]")
            collection.insertOne(event)
        } else {
            println("Update event [${event.eventId}]")
            collection.replaceOne(Event::eventId eq event.eventId, event, replaceUpsert())
        }
    }

    fun saveUpdateMarket(market: Market) {
        val collectionEvent = database.getCollection<Event>("events") //KMongo extension method
        val collectionMarket = database.getCollection<Market>("markets") //KMongo extension method

        val eventDb = collectionEvent.findOne(Event::eventId eq market.eventId)

        if (eventDb == null) {
            //TODO to leave like this?
            //TODO cannot find base event for this market should skip or what to do?
            println("Event cannot be found in database! Skip")
        } else {
            println("Insert market in database!")
            collectionMarket.insertOne(market)
        }
    }

    fun saveUpdateOutcome(outcome: Outcome) {
        val collectionMarket = database.getCollection<Market>("markets") //KMongo extension method
        val collectionOutcome = database.getCollection<Outcome>("outcomes") //KMongo extension method

        val marketDb = collectionMarket.findOne(Market::marketId eq outcome.marketId)

        if (marketDb == null) {
            //TODO to leave like this?
            //TODO cannot find base market for this moutcomearket should skip or what to do?
            println("Market cannot be found in database! Skip")
        } else {
            println("Insert outcome in database!")
            collectionOutcome.insertOne(outcome)
        }
    }
}
