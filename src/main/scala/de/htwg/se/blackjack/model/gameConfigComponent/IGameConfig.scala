package de.htwg.se.blackjack.model.gameConfigComponent

import de.htwg.se.blackjack.model.deckComponent._
import de.htwg.se.blackjack.model.playerComponent._

import scala.util.{Failure, Success, Try}

/**
 * Interface of GameConfig that has the following methods.
 */
trait IGameConfig {
    /**
     * creates new player with playername and new hand
     * @param playerName name of player
     * @return new GameConfig
     */
    def createPlayer(playerName: String = ""): IGameConfig

    /**
     * unpacks option of list of cards
     * @param cards list of cards as option
     * @return list of cards
     */
    def unpackOption(cards: Vector[Option[ICard]]): Vector[ICard]

    /**
     * sets playername from given index
     * @param playerName new name of player
     * @param playerIndex index of player
     * @return new GameConfig
     */
    def setPlayerName(playerName: String, playerIndex: Int): IGameConfig

    /**
     * updates player at index
     * @param newPlayer new player
     * @param index index of player
     * @param newDeck new deck of GameConfig
     * @return new GameConfig
     */
    def updatePlayerAtIndex(newPlayer: IPlayer, index: Int, newDeck: IDeck): IGameConfig

    /**
     * increments activePlayerIndex
     * @return new GameConfig
     */
    def incrementActivePlayerIndex(): IGameConfig

    /**
     * resets activePlayerIndex to 0
     * @return new GameConfig
     */
    def resetActivePlayerIndex(): IGameConfig

    /**
     * returns player name of active player
     * @return player name
     */
    def getActivePlayerName: String

    /**
     * returns active player
     * @return active player
     */
    def getActivePlayer: IPlayer

    /**
     * creates a new dealer with new hand
     * @return new GameConfig
     */
    def initDealer(): IGameConfig

    /**
     * constructs a string with all player and dealer hands
     * @return
     */
    def getAllPlayerAndDealerHandsAsString: String

    /**
     * resets game config
     * @return reseted GameConfig
     */
    def resetGameConfig(): IGameConfig

    /**
     * adds winner to winners list
     * @param winner new winner
     * @return new GameConfig
     */
    def addWinner(winner: IPlayer): IGameConfig

    /**
     * reset current winner list and add new winner
     * @param winner new winner
     * @return new GameConfig
     */
    def setWinner(winner: IPlayer): IGameConfig

    /**
     * construct string of all winner from winner list
     * @return all winners as string
     */
    def getAllWinnerAsString: String

    /**
     * return player at index
     * @param index index of player
     * @return player
     */
    def getPlayerAtIndex(index: Int): IPlayer

    /**
     * return all players
     * @return all players as list
     */
    def getPlayers(): Vector[IPlayer]

    /**
     * return deck
     * @return deck
     */
    def getDeck(): IDeck

    /**
     * return active player index
     * @return index of active player
     */
    def getActivePlayerIndex(): Int

    /**
     * returns dealer
     * @return dealer
     */
    def getDealer(): IPlayer

    /**
     * return all winners
     * @return all winners as list
     */
    def getWinners(): Vector[IPlayer]
}

/**
 * Interface of DrawStrategy that has the following methods.
 */
trait IDrawStrategy {
    /**
     * strategy of dealer to draw cards until hand card value is over 16
     * @param gameConfig game config of game
     * @return try of gameconfig
     */
    def drawDealerHand(gameConfig: IGameConfig): Try[IGameConfig]

    /**
     * strategy of player to draw a card
     * @param gameConfig game config of game
     * @return try of gameconfig
     */
    def drawPlayerHand(gameConfig: IGameConfig): Try[IGameConfig]

    /**
     * generic method to call given strategy
     * @param callback given strategy
     * @param gameConfig game config of game
     */
    def strategy(callback:(IGameConfig) => (Try[IGameConfig]), gameConfig: IGameConfig): Unit
}
