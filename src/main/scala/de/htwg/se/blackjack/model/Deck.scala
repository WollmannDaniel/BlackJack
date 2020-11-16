package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.model.Rank._
import de.htwg.se.blackjack.model.Suit._

import scala.util.Random

object Deck {
    var cards = collection.mutable.ListBuffer() ++ Random.shuffle(initDeck())

    def drawCard(): Card = {
        val card = cards.head
        cards -= card
        card
    }

    def initDeck(): Vector[Card] = {
        for {
            suit <- Vector(Heart, Diamond, Spade, Club)
            rank <- Vector(Ace, King, Queen, Jack, Ten, Nine, Eight, Seven, Six, Five, Four, Three, Two)
        }
        yield Card(suit, rank)
    }

    def resetDeck(): Unit = {
        cards.clear()
        cards = collection.mutable.ListBuffer() ++ Random.shuffle(initDeck())
    }
}

