
package de.htwg.se.blackjack.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.blackjack.controller.GameState._
import de.htwg.se.blackjack.controller._
import de.htwg.se.blackjack.model.gameConfigComponent.IGameConfig
import de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl.{DrawStrategy}
import de.htwg.se.blackjack.util.UndoManager

import scala.swing.Publisher
import scala.util.{Failure, Success, Try}

class Controller(var gameConfig: IGameConfig) extends IController with Publisher {
    var gameState = WELCOME
    var running: State = IsNotRunning()
    private val undoManager = new UndoManager

    def getState() = {
        val (state, output) = running.handle(running)
        running = state
        println(output)
    }

    def performInitGame(playerAmount: Int): Unit = {
        undoManager.doStep(new PlayerAmountCommand(this, playerAmount))
        publish(new InitGame)
    }

    def initGame(playerAmount: Int): Unit = {
        val dealerConfig = Try(gameConfig.initDealer())
        dealerConfig match {
            case Success(value) => gameConfig = value
            case Failure(exception) => {
                gameState = EMPTY_DECK
                publish(new RefreshData)
            }
        }

        for (_ <- 1 to playerAmount) {
            val playerConfig = Try(gameConfig.createPlayer())
            playerConfig match {
                case Success(value) => gameConfig = value
                case Failure(exception) => {
                    gameState = EMPTY_DECK
                    publish(new RefreshData)
                }
            }
        }

        gameState = NAME_CREATION
        running = IsRunning()
    }

    def getActivePlayerName: String = gameConfig.getActivePlayerName

    def getPlayerName: String = {
        s"Please enter Playername ${gameConfig.getActivePlayerIndex() + 1}:"
    }

    def setPlayerName(playerName: String): Unit = {
        gameConfig = gameConfig.setPlayerName(playerName, gameConfig.getActivePlayerIndex())
        gameConfig = gameConfig.incrementActivePlayerIndex()

        if(gameConfig.getActivePlayerIndex() >= gameConfig.getPlayers().size){
            gameConfig = gameConfig.resetActivePlayerIndex()
            gameState = PLAYER_TURN
        }
    }

    def performSetPlayerName(playerName: String): Unit = {
        undoManager.doStep(new NameCommand(this, playerName))
        if (gameState == PLAYER_TURN) {
            publish(new StartGame)
        } else{
            publish(new RefreshData)
        }
    }

    def playerHits(): Unit = {
        val config = DrawStrategy.strategy(DrawStrategy.drawPlayerHand, gameConfig)
        config match {
            case Success(value) => {
                gameConfig = value
                val playerHandValue = gameConfig.getActivePlayer.getHand().calculateHandValue()
                if (playerHandValue > 21) {
                    gameState = PLAYER_LOST
                    publish(new PlayerWentOver)
                    nextPlayer()
                } else {
                    gameState = PLAYER_TURN
                    publish(new RefreshData)
                }
            }
            case Failure(exception) => {
                gameState = EMPTY_DECK
                publish(new RefreshData)
            }
        }
    }

    def playerStands(): Unit = {
        nextPlayer()
    }

    def nextPlayer(): Unit ={
        gameConfig = gameConfig.incrementActivePlayerIndex()

        if(gameConfig.getActivePlayerIndex() >= gameConfig.getPlayers().size){
            gameState = DEALERS_TURN
            manageDealerLogic()
            gameState = EVALUATION
            checkWinner()
        } else {
            gameState = PLAYER_TURN
            publish(new RefreshData)
        }
    }

    def manageDealerLogic(): Unit = {
        val config = DrawStrategy.strategy(DrawStrategy.drawDealerHand, gameConfig)
        config match {
            case Success(value) => {
                gameConfig = value
                publish(new DealersTurn)
            }
            case Failure(exception) => {
                gameState = EMPTY_DECK
                publish(new RefreshData)
            }
        }
    }

    def checkWinner(): Unit = {
        val dealerHandValue = gameConfig.getDealer().getHand().calculateHandValue()

        if(dealerHandValue > 21){
            //alle Spieler die <= 21 sind haben gewonnen
            for (i <- 0 until gameConfig.getPlayers().size) {
                val handValue = gameConfig.getPlayers()(i).getHand().calculateHandValue()
                if (handValue <= 21) {
                    gameConfig = gameConfig.addWinner(gameConfig.getPlayers()(i))
                }
            }
            gameState = PLAYER_WON
        } else {
            //der wo am nähesten an 21 kommt hat gewonnen
            var closestValue = 0
            for (i <- 0 until gameConfig.getPlayers().size) {
                val handValue = gameConfig.getPlayers()(i).getHand().calculateHandValue()
                if (handValue <= 21 && handValue > closestValue) {
                    closestValue = handValue
                }
            }

            for (i <- 0 until gameConfig.getPlayers().size) {
                val handValue = gameConfig.getPlayers()(i).getHand().calculateHandValue()
                if (handValue == closestValue) {
                    gameConfig = gameConfig.addWinner(gameConfig.getPlayers()(i))
                }
            }

            if (dealerHandValue > closestValue) {
                gameState = DEALER_WON
                gameConfig = gameConfig.setWinner(gameConfig.getDealer())
            } else if(dealerHandValue == closestValue){
                gameState = DRAW
                gameConfig = gameConfig.addWinner(gameConfig.getDealer())
            } else {
                gameState = PLAYER_WON
            }
        }

        publish(new ShowResults)
        gameState = IDLE
        publish(new ShowResults)
        running = IsNotRunning()
    }

    def newGame(): Unit ={
        if (gameState != IDLE) {
            gameState = WRONG_CMD
            publish(new RefreshData)
            gameState = PLAYER_TURN
            publish(new RefreshData)
        } else {
            gameState = NEW_GAME_STARTED
            publish(new NewGameStarted)
            gameState = PLAYER_TURN
            gameConfig = gameConfig.resetGameConfig()
            publish(new RefreshData)

            running = IsRunning()
        }
    }

    def gameStateToString: String = {
        gameState match {
            case PLAYER_TURN | PLAYER_LOST => gameConfig.getPlayers()(gameConfig.getActivePlayerIndex()).toString + gameConfig.getDealer().toStringDealer
            case PLAYER_WON | DEALER_WON | DRAW => gameConfig.getAllWinnerAsString
            case _ => gameConfig.getAllPlayerAndDealerHandsAsString
        }
    }

    def quitGame(): Unit = {
        gameState = END_GAME
        publish(new RefreshData)
    }

    def testNotify(): Unit = {
        publish(new RefreshData)
    }

    var jumpedToSetup = false

    def undo: Unit = {
        val oldState = gameState

        undoManager.undoStep
        if (oldState == PLAYER_TURN && gameState == NAME_CREATION) {
            publish(new SetupMenu)
            jumpedToSetup = true
        } else {
            publish(new RefreshData)
        }
    }

    def redo: Unit = {
        undoManager.redoStep
        if (jumpedToSetup && gameState == PLAYER_TURN) {
            jumpedToSetup = false
            publish(new StartGame)
        } else {
            publish(new RefreshData)
        }
    }

    def mapSymbolToChar(hideDealerCards: Boolean, isDealer: Boolean, playerIndex: Int): List[String] = {
        var cardImageNames = List[String]()

        if (isDealer) {
            for(card <- gameConfig.getDealer().getHand().getCards()) {
                if (hideDealerCards && card.equals(gameConfig.getDealer().getHand().getCards().last)) {
                    cardImageNames = cardImageNames :+ "red_back.png"
                } else {
                    cardImageNames = cardImageNames :+ (card.mapCardSymbol() + ".png")
                }
            }
        } else {
            var targetPlayer = gameConfig.getActivePlayer
            if(playerIndex != -1) {
                targetPlayer = gameConfig.getPlayers()(playerIndex)
            }

            for(card <- targetPlayer.getHand().getCards()) {
                cardImageNames = cardImageNames :+ (card.mapCardSymbol() + ".png")
            }
        }
        cardImageNames
    }
}
