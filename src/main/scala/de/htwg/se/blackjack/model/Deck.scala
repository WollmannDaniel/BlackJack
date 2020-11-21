package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.model.Rank._
import de.htwg.se.blackjack.model.Suit._

import scala.util.Random

case class Deck(cards: Vector[Card]) {
    def this() = this(Vector[Card]())

    def drawCards(num: Int): (Deck, Vector[Card]) = {
        var drawedCards = Vector[Card]()

        val from = cards.size - num
        val bis = cards.size - 1

        for (i <- (from to bis)) {
            drawedCards = drawedCards :+ cards(i)
        }
        val newDeck = copy(cards.dropRight(num))
        (newDeck, drawedCards)
    }

    def initDeck(): Vector[Card] = {
        for {
            suit <- Vector(Heart, Diamond, Spade, Club)
            rank <- Vector(Ace, King, Queen, Jack, Ten, Nine, Eight, Seven, Six, Five, Four, Three, Two)
        }
        yield Card(suit, rank)
    }

    def resetDeck(): Deck = {
        copy(Random.shuffle(initDeck()))
    }
}

