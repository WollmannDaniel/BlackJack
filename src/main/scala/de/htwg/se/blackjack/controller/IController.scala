package de.htwg.se.blackjack.controller

import de.htwg.se.blackjack.controller.GameState.{GameState, WELCOME}
import de.htwg.se.blackjack.model.gameConfigComponent.IGameConfig

import scala.swing.Publisher

/**
 * Interface of Controller that has the following methods and var's.
 */
trait IController extends Publisher {
    var gameConfig: IGameConfig
    var gameState: GameState = WELCOME

    /**
     * Performs doStep on UndoManager and publishes
     * @param playerAmount is number of players
     */
    def performInitGame(playerAmount: Int): Unit

    /**
     * Create dealer and players
     * @param playerAmount is number of players
     */
    def initGame(playerAmount: Int): Unit

    /**
     * Returns name of active player
     * @return name of active player
     */
    def getActivePlayerName: String

    /**
     * Returns "Please enter Playername" with activePlayerIndex for input in ui
     * @return input request
     */
    def getPlayerName: String

    /**
     * Sets playername of activePlayer
     * @param playerName player name
     */
    def setPlayerName(playerName: String): Unit

    /**
     * Performs doStep on UndoManager and publishes
     * @param playerName player name
     */
    def performSetPlayerName(playerName: String): Unit

    /**
     * draws card for activePlayer
     */
    def playerHits(): Unit

    /**
     * changes turn to next player
     */
    def playerStands(): Unit

    /**
     * changes turn to next player or to dealer if it was last players turn
     */
    def nextPlayer(): Unit

    /**
     * draws cards for dealer until his card value is over 16
     */
    def manageDealerLogic(): Unit

    /**
     * checks who is winner of game
     */
    def checkWinner(): Unit

    /**
     * start new game
     */
    def newGame(): Unit

    /**
     * prints state of game - running or no game is running
     */
    def getState(): Unit

    /**
     * constructs string for tui
     * @return string for tui
     */
    def gameStateToString: String

    /**
     * quits the game
     */
    def quitGame(): Unit

    /**
     * help method for scala tests
     */
    def testNotify(): Unit

    /**
     * performs undo of UndoManager
     */
    def undo: Unit

    /**
     * performs redo of UndoManager
     */
    def redo: Unit

    /**
     * returns list of image names of hand cards
     * @param hideDealerCards flag to hide one dealer hand card
     * @param isDealer flag to return dealer hand cards
     * @param playerIndex index of target player
     * @return list of image names of handcards
     */
    def mapSymbolToChar(hideDealerCards: Boolean, isDealer: Boolean, playerIndex: Int): List[String]

    /**
     * save game to xml or json
     */
    def save: Unit

    /**
     * load game from xml or json
     */
    def load: Unit
}

object GameState extends Enumeration {
    type GameState = Value
    val WELCOME, NAME_CREATION, PLAYER_TURN, PLAYER_HITS, PLAYER_STANDS, PLAYER_LOST, EVALUATION, DEALERS_TURN, DEALER_WON, DRAW, PLAYER_WON, NEW_GAME_STARTED, IDLE, WRONG_CMD, END_GAME, EMPTY_DECK = Value
}
