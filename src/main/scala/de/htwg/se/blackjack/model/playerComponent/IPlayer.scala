package de.htwg.se.blackjack.model.playerComponent

import de.htwg.se.blackjack.model.deckComponent.{ICard, IDeck}

import scala.util.Try

/**
 * Interface of Player that has the following methods.
 */
trait IPlayer {
    /**
     * sets name of player
     * @param newName new name
     * @return new player
     */
    def setName(newName: String): IPlayer

    /**
     * constructs string of dealers hand with one hidden card
     * @return constructed string
     */
    def toStringDealer: String

    /**
     * returns name of player
     * @return name of player
     */
    def getName(): String

    /**
     * returns hand of player
     * @return hand of player
     */
    def getHand(): IHand
}

/**
 * Interface of Hand that has the following methods.
 */
trait IHand {
    /**
     * draws a card from deck
     * @param deck deck to draw
     * @return new hand and new deck
     */
    def drawCard(deck: IDeck): Try[(IHand, IDeck)]

    /**
     * adds a card to hand
     * @param card card to add
     * @return new hand
     */
    def addCard(card: ICard): IHand

    /**
     * calculate hand card value
     * @return hand card value
     */
    def calculateHandValue(): Int

    /**
     * constructs a string of hand with one hidden card
     * @return constructed string
     */
    def toStringDealer: String

    /**
     * returns card of hand
     * @return hand cards
     */
    def getCards(): Vector[ICard]
}
