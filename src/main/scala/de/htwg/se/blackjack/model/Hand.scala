package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.model.Rank.Ace

case class Hand(cards: Vector[Card2]) {
    def this(deck: Deck2) {
        this()
    }

    def calculateHandValue(): Int = {
        var sum = cards.map(card => card.rank.id).sum
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
}
