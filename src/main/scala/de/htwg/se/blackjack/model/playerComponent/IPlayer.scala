package de.htwg.se.blackjack.model.playerComponent

import de.htwg.se.blackjack.model.deckComponent.{ICard, IDeck}

import scala.util.Try

trait IPlayer {
    def setName(newName: String): IPlayer
    def toStringDealer: String
    def getName(): String
    def getHand(): IHand
}

trait IHand {
    def drawCard(deck: IDeck): Try[(IHand, IDeck)]
    def addCard(card: ICard): IHand
    def calculateHandValue(): Int
    def toStringDealer: String
    def getCards(): Vector[ICard]
}
