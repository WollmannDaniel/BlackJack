package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.model.Rank.Ace

case class Hand(cards: Vector[Card]) {
    def this() = this(Vector(Deck.drawCard(), Deck.drawCard()))

    def drawCard(): Hand = {
        copy(cards :+ Deck.drawCard())
    }

    def calculateHandValue(): Int = {
        var sum = cards.map(card => card.mapCardValue()).sum
        val count: Int = cards.count(_.rank == Ace)

        if(count >= 1 && sum > 21){
            for( _ <- 1 to count){
                if(sum > 21){
                    sum = sum - 10
                }
            }
        }
        sum
    }

    override def toString: String = {
        val builder = new StringBuilder("[")
        cards foreach(x => builder.append(x))
        builder.append("]");
        val hand = builder.toString().patch(builder.toString().lastIndexOf(','), "", 1)
        val handWithValue = hand + " = " + this.calculateHandValue() + "\n"
        handWithValue
    }

    def toStringDealer: String = {
        val builder = new StringBuilder("[")
        builder.append(cards(0).toString);
        builder.append(" ?]\n");
        builder.toString
    }
}
