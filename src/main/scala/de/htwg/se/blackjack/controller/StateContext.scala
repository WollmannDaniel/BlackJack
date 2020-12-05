package de.htwg.se.blackjack.controller

import de.htwg.se.blackjack.controller.GameState._
import de.htwg.se.blackjack.model.Hand

object InternState extends Enumeration {
    type InternState = Value
    val InternPlayersTurn, InternDealersTurn = Value
}

import InternState._

object StateContext {
    var state = InternPlayersTurn
    var output = ""
    def handle(e: GameState, playerHand: Hand, dealerHand: Hand) = {
        e match {
            case PLAYER_TURN | FirstRound => {
                state = InternPlayersTurn
                output = showDealerHand(playerHand, dealerHand)
            }
            case _ => {
                state = InternDealersTurn
                output = showFullDealerHand(playerHand, dealerHand)
            }
        }
        state
    }

    def showFullDealerHand(playerHand: Hand, dealerHand: Hand) = "Player hand: " + playerHand.toString + "Dealer hand: " + dealerHand.toString
    def showDealerHand(playerHand: Hand, dealerHand: Hand) = "Player hand: " + playerHand.toString + "Dealer hand: " + dealerHand.toStringDealer
}

