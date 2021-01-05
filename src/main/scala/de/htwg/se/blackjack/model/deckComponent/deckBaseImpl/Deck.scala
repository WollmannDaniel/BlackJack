package de.htwg.se.blackjack.model.deckComponent.deckBaseImpl

import de.htwg.se.blackjack.model.deckComponent.{ICard, IDeck}
import de.htwg.se.blackjack.model.deckComponent.Rank._
import de.htwg.se.blackjack.model.deckComponent.Suit._

import scala.util.Random

case class Deck(cards: Vector[ICard]) extends IDeck {
    def this() = this(Vector[ICard]())

    def drawCards(num: Int): (IDeck, Vector[Option[ICard]]) = {
        var drawnCards = Vector[Option[ICard]]()

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

    def initDeck(): Vector[ICard] = {
        for {
            suit <- Vector(Heart, Diamond, Spade, Club)
            rank <- Vector(Ace, King, Queen, Jack, Ten, Nine, Eight, Seven, Six, Five, Four, Three, Two)
        }
            yield Card(suit, rank)
    }

    def resetDeck(): IDeck = {
        copy(Random.shuffle(initDeck()))
    }
}
