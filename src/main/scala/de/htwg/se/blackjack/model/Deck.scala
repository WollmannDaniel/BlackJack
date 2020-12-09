package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.model.Rank._
import de.htwg.se.blackjack.model.Suit._

import scala.util.Random

case class Deck(cards: Vector[Card]) {
    def this() = this(Vector[Card]())

    def drawCards(num: Int): (Deck, Vector[Option[Card]]) = {
        var drawnCards = Vector[Option[Card]]()

        var from = cards.size - num
        var bis = cards.size - 1
        if (from < 0) {
            from = 0
            bis = cards.size
        }

        var newDeck = cards

        for (i <- (from to bis)) {
            if (newDeck.nonEmpty) {
                drawnCards = drawnCards :+ Some(cards(i))
                newDeck = newDeck.dropRight(1)
            } else {
                drawnCards = drawnCards :+ None
            }
        }
        (copy(newDeck), drawnCards)
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

