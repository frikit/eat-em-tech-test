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

    private fun saveUpdateEvent(event: Event) {
        val collection = database.getCollection<Event>("events") //KMongo extension method

        val eventDb = collection.findOne(Event::eventId eq event.eventId)

        if (eventDb == null) {
            println("Create event [${event.eventId}]")
            collection.insertOne(event)
        } else {
            println("Update event [${event.eventId}]")
            collection.replaceOne(Event::eventId eq event.eventId, event, replaceUpsert())
        }
    }

    private fun saveUpdateMarket(market: Market) {
        val collectionEvent = database.getCollection<Event>("events") //KMongo extension method
        val collectionMarket = database.getCollection<Market>("markets") //KMongo extension method

        val eventDb = collectionEvent.findOne(Event::eventId eq market.eventId)

        if (eventDb == null) {
            //TODO to leave like this?
            //TODO cannot find base event for this market should skip or what to do?
            println("Event cannot be found in database! Skip")
        } else {
            val marketDb = collectionEvent.findOne(Market::marketId eq market.marketId)

            if (marketDb == null) {
                println("Create market! [${market.marketId}]")
                collectionMarket.insertOne(market)
            } else {
                println("Update market! [${market.marketId}]")
                collectionMarket.replaceOne(Market::marketId eq market.marketId, market, replaceUpsert())
            }
        }
    }

    private fun saveUpdateOutcome(outcome: Outcome) {
        val collectionMarket = database.getCollection<Market>("markets") //KMongo extension method
        val collectionOutcome = database.getCollection<Outcome>("outcomes") //KMongo extension method

        val marketDb = collectionMarket.findOne(Market::marketId eq outcome.marketId)

        if (marketDb == null) {
            //TODO to leave like this?
            //TODO cannot find base market for this moutcomearket should skip or what to do?
            println("Market cannot be found in database! Skip")
        } else {
            val outcomeDb = collectionOutcome.findOne(Outcome::outcomeId eq outcome.outcomeId)

            if (outcomeDb == null) {
                println("Create outcome! [${outcome.outcomeId}]")
                collectionOutcome.insertOne(outcome)
            } else {
                println("Update outcome! [${outcome.outcomeId}]")
                collectionOutcome.replaceOne(Outcome::outcomeId eq outcome.outcomeId, outcome, replaceUpsert())
            }

        }
    }

    fun getEvent(eventId: String): Event? {
        val markets = mutableListOf<Market>()

        val collectionEvent = database.getCollection<Event>("events") //KMongo extension method

        val eventDb = collectionEvent.findOne(Event::eventId eq eventId)

        if (eventDb == null) {
            println("Can't find such event in database!")
        } else {
            val collectionMarket = database.getCollection<Market>("markets") //KMongo extension method

            val marketDb = collectionMarket.find(Market::eventId eq eventDb.eventId).iterator()
            while (marketDb.hasNext()) {
                val market = marketDb.next()
                val outcomes = getOutcomes(market)

                market.outcomes.addAll(outcomes)
                markets.add(market)
            }
        }

        eventDb?.markets?.addAll(markets)
        return eventDb
    }

    private fun getOutcomes(market: Market): MutableList<Outcome> {
        val collectionOutcome = database.getCollection<Outcome>("outcomes") //KMongo extension method
        val outcomeDb = collectionOutcome.find(Outcome::marketId eq market.marketId).iterator()
        val outcomes = mutableListOf<Outcome>()
        val iterator = outcomeDb.iterator()
        while (iterator.hasNext()) {
            val outcome = iterator.next()
            outcomes.add(outcome)
        }
        return outcomes
    }
}
