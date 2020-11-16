package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.model.Rank._
import de.htwg.se.blackjack.model.Suit._

import scala.util.Random

case class Deck2(cards: Vector[Card2]) {
    def this() = this(Random.shuffle(initList()))

    def drawCard(hand: Hand): Card2 = {
        val card = cards(0)
        cards.
        card
    }

    def initList(): Vector[Card2] = {
        for {
            suit <- Vector(Heart, Diamond, Spade, Club)
            rank <- Vector(Ace, King, Queen, Jack, Ten, Nine, Eight, Seven, Six, Five, Four, Three, Two)
        }
            yield Card2(suit, rank)
    }
}

