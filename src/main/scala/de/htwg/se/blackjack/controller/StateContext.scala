package de.htwg.se.blackjack.controller

import de.htwg.se.blackjack.controller.GameState._
import de.htwg.se.blackjack.model.Hand

object StateContext {
    var state = ""
    def handle(e: GameState, playerHand: Hand, dealerHand: Hand) = {
        e match {
            case PlayersTurn | FirstRound => state = showDealerHand(playerHand, dealerHand)
            case _ => state = showFullDealerHand(playerHand, dealerHand)
        }
        state
    }

    def showFullDealerHand(playerHand: Hand, dealerHand: Hand) = "Player hand: " + playerHand.toString + "Dealer hand: " + dealerHand.toString
    def showDealerHand(playerHand: Hand, dealerHand: Hand) = "Player hand: " + playerHand.toString + "Dealer hand: " + dealerHand.toStringDealer
}

