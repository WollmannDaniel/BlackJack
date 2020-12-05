
package de.htwg.se.blackjack.controller

import de.htwg.se.blackjack.model.{Card, Deck, Hand, GameConfig, Player}
import de.htwg.se.blackjack.model.StrategyContext
import de.htwg.se.blackjack.util.Observable

object GameState extends Enumeration {
    type GameState = Value
    val WELCOME, NAME_CREATION, PLAYER_TURN, DealersTurn, FirstRound, Idle, PlayerWon, PlayerLost, Draw, BlackJack, WrongCmd, EndGame = Value
}

import GameState._

class Controller(var deck: Deck) extends Observable {
    var gameState = WELCOME

    var playerHand = Hand(Vector[Card]())
    var dealerHand = Hand(Vector[Card]())

    var running: State = IsNotRunning()

    var gameConfig = GameConfig(Vector[Player](), deck)

    def getState() = {
        val (state, output) = running.handle(running)
        running = state
        println(output)
    }

    def initGame(playerAmount: Int): Unit = {

        for (i <- 0 until playerAmount) {
            gameConfig = gameConfig.createPlayer()
        }

        gameState = NAME_CREATION
        notifyObservers


        /*
        running = IsRunning()
        deck = deck.resetDeck()
        val (newDeck, cards) = deck.drawCards(4)
        deck = newDeck
        val playerHandCards = Vector(cards(0), cards(1))
        val dealerHandCards = Vector(cards(2), cards(3))
        playerHand = Hand(playerHandCards)
        dealerHand = Hand(dealerHandCards)
         */
    }

    def getActivePlayerName: String = gameConfig.getActivePlayerName()

    def getPlayerName: String = {
        s"Please enter Playername ${gameConfig.activePlayerIndex + 1}:"
    }

    def setPlayerName(playerName: String): Unit = {
        gameConfig = gameConfig.setPlayerName(playerName, gameConfig.activePlayerIndex)
        gameConfig = gameConfig.incrementActivePlayerIndex()

        if(gameConfig.activePlayerIndex > gameConfig.players.size){
            gameConfig = gameConfig.resetActivePlayerIndex()
            gameState = PLAYER_TURN
        }
        notifyObservers
    }

    def playerHits(): Unit = {
        val (newPlayerHand, newDeck) = StrategyContext.strategy(StrategyContext.drawPlayerHand, deck, playerHand)
        playerHand = newPlayerHand
        deck = newDeck

        val playerHandValue = playerHand.calculateHandValue()
        if (playerHandValue > 21) {
            checkWinner()
        } else {
            gameState = PLAYER_TURN
            notifyObservers
        }
    }

    def playerStands(): Unit = {
        gameState = DealersTurn
        manageDealerLogic()
        checkWinner()
    }

    def manageDealerLogic(): Unit = {
        val (newDealerHand, newDeck) = StrategyContext.strategy(StrategyContext.drawDealerHand, deck, dealerHand)
        dealerHand = newDealerHand
        deck = newDeck
        notifyObservers
    }

    def checkWinner(): Unit ={
        val playerHandValue = playerHand.calculateHandValue()
        val dealerHandValue = dealerHand.calculateHandValue()

        if(playerHandValue == 21 && playerHand.cards.size == 2) {
            gameState = BlackJack
        } else if(playerHandValue > 21){
            gameState = PlayerLost
        } else if (dealerHandValue > 21) {
            gameState = PlayerWon
        } else if (playerHandValue < dealerHandValue) {
            gameState = PlayerLost
        } else if (playerHandValue > dealerHandValue) {
            gameState = PlayerWon
        } else {
            gameState = Draw
        }

        notifyObservers
        gameState = Idle
        notifyObservers
        running = IsNotRunning()
    }

    def newGame(): Unit ={
        if (gameState != Idle) {
            gameState = WrongCmd
            notifyObservers
            //gameState = PlayersTurn
            notifyObservers
        } else {
            gameState = FirstRound
            //initGame()
            notifyObservers
        }
    }

    def gameStateToString: String = {
        gameState match {
            case PLAYER_TURN => "Your hand: " + playerHand.toString + "Dealer hand: " + dealerHand.toStringDealer
            case _ => "Player hand: " + playerHand.toString + "Dealer hand: " + dealerHand.toString //todo
        }
    }

    def quitGame(): Unit = {
        gameState = EndGame
        notifyObservers
    }

    def testNotify(): Unit = {
        notifyObservers
    }
}
