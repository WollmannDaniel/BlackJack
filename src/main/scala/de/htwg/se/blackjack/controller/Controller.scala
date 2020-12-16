
package de.htwg.se.blackjack.controller

import de.htwg.se.blackjack.model.{Card, Deck, GameConfig, Hand, Player}
import de.htwg.se.blackjack.model.DrawStrategy
import de.htwg.se.blackjack.util.{Observable, UndoManager}

import scala.util.{Failure, Success, Try}

object GameState extends Enumeration {
    type GameState = Value
    val WELCOME, NAME_CREATION, PLAYER_TURN, PLAYER_HITS, PLAYER_STANDS, PLAYER_LOST, EVALUATION, DEALERS_TURN, DEALER_WON, DRAW, PLAYER_WON, NEW_GAME_STARTED, IDLE, WRONG_CMD, END_GAME, EMPTY_DECK = Value
}

import GameState._

class Controller(var deck: Deck) extends Observable {
    var gameState = WELCOME
    var running: State = IsNotRunning()
    var gameConfig = GameConfig(Vector[Player](), Player("Dealer", Hand(Vector[Card]())), deck.resetDeck(), 0, Vector[Player]())
    private val undoManager = new UndoManager

    def getState() = {
        val (state, output) = running.handle(running)
        running = state
        println(output)
    }

    def performInitGame(playerAmount: Int): Unit = {
        undoManager.doStep(new PlayerAmountCommand(this, playerAmount))
        notifyObservers
    }

    def initGame(playerAmount: Int): Unit = {
        val dealerConfig = Try(gameConfig.initDealer())
        dealerConfig match {
            case Success(value) => gameConfig = value
            case Failure(exception) => {
                gameState = EMPTY_DECK
                notifyObservers
            }
        }

        for (_ <- 1 to playerAmount) {
            val playerConfig = Try(gameConfig.createPlayer())
            playerConfig match {
                case Success(value) => gameConfig = value
                case Failure(exception) => {
                    gameState = EMPTY_DECK
                    notifyObservers
                }
            }
        }

        gameState = NAME_CREATION
        running = IsRunning()
    }

    def getActivePlayerName: String = gameConfig.getActivePlayerName

    def getPlayerName: String = {
        s"Please enter Playername ${gameConfig.activePlayerIndex + 1}:"
    }

    def setPlayerName(playerName: String): Unit = {
        gameConfig = gameConfig.setPlayerName(playerName, gameConfig.activePlayerIndex)
        gameConfig = gameConfig.incrementActivePlayerIndex()

        if(gameConfig.activePlayerIndex >= gameConfig.players.size){
            gameConfig = gameConfig.resetActivePlayerIndex()
            gameState = PLAYER_TURN
        }
    }

    def performSetPlayerName(playerName: String): Unit = {
        undoManager.doStep(new NameCommand(this, playerName))
        notifyObservers
    }

    def playerHits(): Unit = {
        val config = DrawStrategy.strategy(DrawStrategy.drawPlayerHand, gameConfig)
        config match {
            case Success(value) => {
                gameConfig = value
                val playerHandValue = gameConfig.getActivePlayer.hand.calculateHandValue()
                if (playerHandValue > 21) {
                    gameState = PLAYER_LOST
                    notifyObservers
                    nextPlayer()
                } else {
                    gameState = PLAYER_TURN
                    notifyObservers
                }
            }
            case Failure(exception) => {
                gameState = EMPTY_DECK
                notifyObservers
            }
        }
    }

    def playerStands(): Unit = {
        nextPlayer()
    }

    def nextPlayer(): Unit ={
        gameConfig = gameConfig.incrementActivePlayerIndex()

        if(gameConfig.activePlayerIndex >= gameConfig.players.size){
            gameState = DEALERS_TURN
            manageDealerLogic()
            gameState = EVALUATION
            checkWinner()
        } else {
            gameState = PLAYER_TURN
            notifyObservers
        }
    }

    def manageDealerLogic(): Unit = {
        val config = DrawStrategy.strategy(DrawStrategy.drawDealerHand, gameConfig)
        config match {
            case Success(value) => {
                gameConfig = value
                notifyObservers
            }
            case Failure(exception) => {
                gameState = EMPTY_DECK
                notifyObservers
            }
        }
    }

    def checkWinner(): Unit = {
        val dealerHandValue = gameConfig.dealer.hand.calculateHandValue()

        if(dealerHandValue > 21){
            //alle Spieler die <= 21 sind haben gewonnen
            for (i <- 0 until gameConfig.players.size) {
                val handValue = gameConfig.players(i).hand.calculateHandValue()
                if (handValue <= 21) {
                    gameConfig = gameConfig.addWinner(gameConfig.players(i))
                }
            }
            gameState = PLAYER_WON
        } else {
            //der wo am nähesten an 21 kommt hat gewonnen
            var closestValue = 0
            for (i <- 0 until gameConfig.players.size) {
                val handValue = gameConfig.players(i).hand.calculateHandValue()
                if (handValue <= 21 && handValue > closestValue) {
                    closestValue = handValue
                }
            }

            for (i <- 0 until gameConfig.players.size) {
                val handValue = gameConfig.players(i).hand.calculateHandValue()
                if (handValue == closestValue) {
                    gameConfig = gameConfig.addWinner(gameConfig.players(i))
                }
            }

            if (dealerHandValue > closestValue) {
                gameState = DEALER_WON
                gameConfig = gameConfig.setWinner(gameConfig.dealer)
            } else if(dealerHandValue == closestValue){
                gameState = DRAW
                gameConfig = gameConfig.addWinner(gameConfig.dealer)
            } else {
                gameState = PLAYER_WON
            }
        }

        notifyObservers
        gameState = IDLE
        notifyObservers
        running = IsNotRunning()
    }

    def newGame(): Unit ={
        if (gameState != IDLE) {
            gameState = WRONG_CMD
            notifyObservers
            gameState = PLAYER_TURN
            notifyObservers
        } else {
            gameState = NEW_GAME_STARTED
            notifyObservers
            gameState = PLAYER_TURN
            gameConfig = gameConfig.resetGameConfig()
            notifyObservers

            running = IsRunning()
        }
    }

    def gameStateToString: String = {
        gameState match {
            case PLAYER_TURN | PLAYER_LOST => gameConfig.players(gameConfig.activePlayerIndex).toString + gameConfig.dealer.toStringDealer
            case PLAYER_WON | DEALER_WON | DRAW => gameConfig.getAllWinnerAsString
            case _ => gameConfig.getAllPlayerAndDealerHandsAsString
        }
    }

    def quitGame(): Unit = {
        gameState = END_GAME
        notifyObservers
    }

    def testNotify(): Unit = {
        notifyObservers
    }

    def undo: Unit = {
        undoManager.undoStep
        notifyObservers
    }

    def redo: Unit = {
        undoManager.redoStep
        notifyObservers
    }
}
