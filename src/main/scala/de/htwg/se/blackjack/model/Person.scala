package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.Blackjack

case class Person(name: String, cards: Vector[Card]) {
    def addCard(card: Card): Person = {
        copy(name, cards :+ card)
    }

    def calculateHandValue(): Int = {
        var sum = cards.map(card => Blackjack.cardValues(card.rank)).sum
        val count: Int = cards.count(_.rank == "Ace")

        if(count >= 1 && sum > 21){
            for( i <- 1 to count){
                if(sum > 21){
                    sum = sum - 10
                }
            }
        }
        sum
    }

    override def toString: String = {
        var output = s"$name's hand: ["
        for (card <- cards) {
            output += card
            if(card != cards.last){
                output += ", "
            }
        }
        output += "] = " + calculateHandValue()
        output
    }

    def toStringDealer: String = {
        var output = s"$name's hand: ["
        output += cards(0).toString
        output += ", ?]"
        output
    }
}
