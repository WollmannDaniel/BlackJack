package de.htwg.se.blackjack.controller

import de.htwg.se.blackjack.model.{Hand, Deck}
import de.htwg.se.blackjack.util.Observable

object GameState extends Enumeration {
    type GameState = Value
    val PlayersTurn, DealersTurn, FirstRound, Idle, PlayerWon, PlayerLost, Draw, BlackJack, WrongCmd, EndGame = Value
}

import GameState._

class Controller(var playerHand: Hand, var dealerHand: Hand) extends Observable {
    var gameState = FirstRound

    def playerHits(): Unit = {
        playerHand = playerHand.drawCard()

        val playerHandValue = playerHand.calculateHandValue()
        if (playerHandValue > 21) {
            checkWinner()
        } else {
            gameState = PlayersTurn
            notifyObservers
        }
    }

    def playerStands(): Unit = {
        gameState = DealersTurn
        manageDealerLogic()
        checkWinner()
    }

    def manageDealerLogic(): Unit = {
        while(dealerHand.calculateHandValue() < 17) {
            dealerHand = dealerHand.drawCard()
        }
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
    }

    def newGame(): Unit ={
        if (gameState != Idle) {
            gameState = WrongCmd
            notifyObservers
            gameState = PlayersTurn
            notifyObservers
        } else {
            gameState = FirstRound
            Deck.resetDeck()
            playerHand = new Hand()
            dealerHand = new Hand()
            notifyObservers
        }
    }

    def gameStateToString: String = {
        gameState match {
            case PlayersTurn | FirstRound => "Player hand: " + playerHand.toString + "Dealer hand: " + dealerHand.toStringDealer
            case _ => "Player hand: " + playerHand.toString + "Dealer hand: " + dealerHand.toString
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
