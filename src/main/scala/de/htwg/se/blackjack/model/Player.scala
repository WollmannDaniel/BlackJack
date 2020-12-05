package de.htwg.se.blackjack.model

case class Player(name: String, hand: Hand) {
    def drawCard(deck: Deck): (Player, Deck) = {
        val (newHand, newDeck) = hand.drawCard(deck)
        (copy(name, newHand), newDeck)
    }

    def setName(newName: String): Player = {
        copy(newName, hand)
    }

    override def toString: String = {
        val builder = new StringBuilder(name)
        builder.append(" ").append(hand.toString);
        builder.toString
    }
}
