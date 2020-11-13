package de.htwg.se.blackjack.model

import scala.util.Random

case class Deck(cards: List[Card]) {
    def drawCard(): Deck = copy(cards.dropRight(1))

    def getDrawedCard(oldDeck: Deck): Card = {
        val card = oldDeck.cards filterNot cards.contains
        card.head
    }
}
