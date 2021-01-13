package de.htwg.se.blackjack.model.deckComponent

import de.htwg.se.blackjack.model.deckComponent.Rank.Rank
import de.htwg.se.blackjack.model.deckComponent.Suit.Suit

trait IDeck {
    def drawCards(num: Int): (IDeck, Vector[Option[ICard]])
    def initDeck(): Vector[ICard]
    def resetDeck(): IDeck
    def getCards(): Vector[ICard]
}

trait ICard {
    def mapCardRank(): String
    def mapCardValue(): Int
    def mapCardSymbol(): String
    def getRank(): Rank
    def getSuit(): Suit
}
