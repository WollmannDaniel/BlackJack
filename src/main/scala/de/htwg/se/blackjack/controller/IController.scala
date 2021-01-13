package de.htwg.se.blackjack.controller

import de.htwg.se.blackjack.controller.GameState.{GameState, WELCOME}
import de.htwg.se.blackjack.model.gameConfigComponent.IGameConfig

import scala.swing.Publisher

trait IController extends Publisher {
    var gameConfig: IGameConfig
    var gameState: GameState = WELCOME

    def getState(): Unit
    def performInitGame(playerAmount: Int): Unit
    def initGame(playerAmount: Int): Unit
    def getActivePlayerName: String
    def getPlayerName: String
    def setPlayerName(playerName: String): Unit
    def performSetPlayerName(playerName: String): Unit
    def playerHits(): Unit
    def playerStands(): Unit
    def nextPlayer(): Unit
    def manageDealerLogic(): Unit
    def checkWinner(): Unit
    def newGame(): Unit
    def gameStateToString: String
    def quitGame(): Unit
    def testNotify(): Unit
    def undo: Unit
    def redo: Unit
    def mapSymbolToChar(hideDealerCards: Boolean, isDealer: Boolean, playerIndex: Int): List[String]
    def save: Unit
    def load: Unit
}

object GameState extends Enumeration {
    type GameState = Value
    val WELCOME, NAME_CREATION, PLAYER_TURN, PLAYER_HITS, PLAYER_STANDS, PLAYER_LOST, EVALUATION, DEALERS_TURN, DEALER_WON, DRAW, PLAYER_WON, NEW_GAME_STARTED, IDLE, WRONG_CMD, END_GAME, EMPTY_DECK = Value
}
