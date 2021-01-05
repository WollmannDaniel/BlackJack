package de.htwg.se.blackjack.model.playerComponent.playerComponentBaseImpl

import de.htwg.se.blackjack.model.deckComponent.{ICard, IDeck}
import de.htwg.se.blackjack.model.deckComponent.Rank.Ace
import de.htwg.se.blackjack.model.playerComponent.IHand

case class Hand(cards: Vector[ICard]) extends IHand {

    def drawCard(deck: IDeck): (IHand, IDeck) = {
        val (newDeck, drawedCard) = deck.drawCards(1)

        drawedCard(0) match {
            case Some(card) => (copy(cards :+ card), newDeck)
            case None => throw new NullPointerException("Deck doesn't have enough cards.")
        }
    }

    def addCard(card: ICard): IHand = {
        copy(cards :+ card)
    }

    def calculateHandValue(): Int = {
        var sum = cards.map(card => card.mapCardValue()).sum
        val count: Int = cards.count(_.getRank() == Ace)

        if (count >= 1 && sum > 21) {
            for (_ <- 1 to count) {
                if (sum > 21) {
                    sum = sum - 10
                }
            }
        }
        sum
    }

    override def toString: String = {
        val builder = new StringBuilder("[")
        cards foreach (x => builder.append(x))
        builder.append("]")
        val hand = builder.toString().patch(builder.toString().lastIndexOf(','), "", 1)
        val handWithValue = hand + " = " + this.calculateHandValue() + "\n"
        handWithValue
    }

    def toStringDealer: String = {
        val builder = new StringBuilder("[")
        builder.append(cards(0).toString)
        builder.append(" ?]\n")
        builder.toString
    }

    override def getCards(): Vector[ICard] = cards
}
