package de.htwg.se.blackjack.model.gameConfigComponent

import de.htwg.se.blackjack.model.deckComponent._
import de.htwg.se.blackjack.model.playerComponent._

import scala.util.{Failure, Success, Try}

trait IGameConfig {
    def createPlayer(playerName: String = ""): IGameConfig
    def unpackOption(cards: Vector[Option[ICard]]): Vector[ICard]
    def setPlayerName(playerName: String, playerIndex: Int): IGameConfig
    def updatePlayerAtIndex(newPlayer: IPlayer, index: Int, newDeck: IDeck): IGameConfig
    def incrementActivePlayerIndex(): IGameConfig
    def resetActivePlayerIndex(): IGameConfig
    def getActivePlayerName: String
    def getActivePlayer: IPlayer
    def initDealer(): IGameConfig
    def getAllPlayerAndDealerHandsAsString: String
    def resetGameConfig(): IGameConfig
    def addWinner(winner: IPlayer): IGameConfig
    def setWinner(winner: IPlayer): IGameConfig
    def getAllWinnerAsString: String
    def getPlayerAtIndex(index: Int): IPlayer
    def getPlayers(): Vector[IPlayer]
    def getDeck(): IDeck
    def getActivePlayerIndex(): Int
    def getDealer(): IPlayer
    def getWinners(): Vector[IPlayer]
}

trait IDrawStrategy {
    def drawDealerHand(gameConfig: IGameConfig): Try[IGameConfig]
    def drawPlayerHand(gameConfig: IGameConfig): Try[IGameConfig]
    def strategy(callback:(IGameConfig) => (Try[IGameConfig]), gameConfig: IGameConfig)
}
