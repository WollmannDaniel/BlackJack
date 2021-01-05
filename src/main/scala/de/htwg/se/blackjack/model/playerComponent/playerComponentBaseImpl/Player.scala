package de.htwg.se.blackjack.model.playerComponent.playerComponentBaseImpl

import de.htwg.se.blackjack.model.deckComponent.IDeck
import de.htwg.se.blackjack.model.playerComponent.{IHand, IPlayer}

case class Player(name: String, hand: IHand) extends IPlayer {
    def drawCard(deck: IDeck): (Player, IDeck) = {
        val (newHand, newDeck) = hand.drawCard(deck)
        (copy(name, newHand), newDeck)
    }

    def setName(newName: String): IPlayer = {
        copy(newName, hand)
    }

    override def toString: String = {
        val builder = new StringBuilder(name)
        builder.append(" ").append(hand.toString);
        builder.toString
    }

    def toStringDealer: String = {
        val builder = new StringBuilder(name)
        builder.append(" ").append(hand.toStringDealer);
        builder.toString
    }

    override def getName(): String = name

    override def getHand(): IHand = hand
}
