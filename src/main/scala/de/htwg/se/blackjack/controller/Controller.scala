package de.htwg.se.blackjack.controller

import de.htwg.se.blackjack.model.Hand
import de.htwg.se.blackjack.util.Observable

class Controller(var playerHand: Hand, var dealerHand: Hand) extends Observable {
    def playerHits(): Unit = {
        notifyObservers
    }

    def playerStands(): Unit = {
        notifyObservers
    }

    def gameStateToString: String = playerHand.toString + dealerHand.toString
}
