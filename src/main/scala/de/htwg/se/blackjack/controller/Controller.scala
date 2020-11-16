package de.htwg.se.blackjack.controller

import de.htwg.se.blackjack.model.{Hand, Deck}
import de.htwg.se.blackjack.util.Observable

object GameState extends Enumeration {
    type GameState = Value
    val PlayersTurn, DealersTurn, FirstRound, Idle = Value
}

import GameState._

class Controller(var playerHand: Hand, var dealerHand: Hand) extends Observable {
    var gameState = FirstRound
    var message: String = "Starting new game!\nThe deck was shuffled.\nIt's your turn. Hit or stand?(h/s)\n"

    def playerHits(): Unit = {
        playerHand = playerHand.drawCard()
        notifyObservers

        val playerHandValue = playerHand.calculateHandValue()
        if (playerHandValue > 21) {
            checkWinner()
        } else {
            printMessage()
        }
    }

    def printMessage(): Unit = {
        gameState = Idle
        message = "It's your turn. Hit or stand?(h/s)"
        notifyObservers
        gameState = PlayersTurn
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
        gameState = Idle
        val playerHandValue = playerHand.calculateHandValue()
        val dealerHandValue = dealerHand.calculateHandValue()

        if(playerHandValue == 21 && playerHand.cards.size == 2) {
            message = "Lucky boy! The Player has won!"
        } else if(playerHandValue > 21){
            message = "Dealer has won!"
        } else if (dealerHandValue > 21) {
            message = "The Player has won!"
        } else if (playerHandValue < dealerHandValue) {
            message = "Dealer has won!"
        } else if (playerHandValue > dealerHandValue) {
            message = "The Player has won!"
        } else {
            message = "It's a draw!"
        }

        notifyObservers
        gameState = DealersTurn
        notifyObservers
        gameState = Idle
        message = "q = quit, n = start new game"
        notifyObservers
    }

    def newGame(): Unit ={
        message = "Starting new game!\nThe deck was shuffled.\nIt's your turn. Hit or stand?(h/s)"
        notifyObservers
        gameState = PlayersTurn
        Deck.resetDeck()
        playerHand = new Hand()
        dealerHand = new Hand()
        notifyObservers
    }

    def gameStateToString: String = {
        gameState match {
            case FirstRound => {
                gameState = PlayersTurn
                message + "Playerhand: " + playerHand.toString + "Dealerhand: " + dealerHand.toStringDealer
            }
            case PlayersTurn => "Playerhand: " + playerHand.toString + "Dealerhand: " + dealerHand.toStringDealer
            case DealersTurn => "Playerhand: " + playerHand.toString + "Dealerhand: " + dealerHand.toString
            case _ => message
        }
    }
}
