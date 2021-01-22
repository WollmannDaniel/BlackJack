package de.htwg.se.blackjack.model.deckComponent

import de.htwg.se.blackjack.model.deckComponent.Rank.Rank
import de.htwg.se.blackjack.model.deckComponent.Suit.Suit

/**
 * Interface of Deck that has the following methods.
 */
trait IDeck {
    /**
     * draws a specific number of cards
     * @param num number of cards to draw
     * @return new deck and the drawn cards
     */
    def drawCards(num: Int): (IDeck, Vector[Option[ICard]])

    /**
     * return new list of cards
     * @return list of cards
     */
    def initDeck(): Vector[ICard]

    /**
     * create new deck and shuffels it
     * @return new deck
     */
    def resetDeck(): IDeck

    /**
     * returns current deck cards
     * @return current deck cards
     */
    def getCards(): Vector[ICard]
}

/**
 * Interface of Card that has the following methods.
 */
trait ICard {

    /**
     * maps rank to string
     * @return string of rank
     */
    def mapCardRank(): String

    /**
     * maps rank to value
     * @return value of rank
     */
    def mapCardValue(): Int

    /**
     * maps suit to char
     * @return char of suit
     */
    def mapCardSymbol(): String

    /**
     * returns rank
     * @return rank
     */
    def getRank(): Rank

    /**
     * returns suit
     * @return suit
     */
    def getSuit(): Suit
}
