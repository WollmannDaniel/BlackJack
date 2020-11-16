package de.htwg.se.blackjack.aview

import de.htwg.se.blackjack.model.{CardManager, Deck2, Hand}

class Tui2 {

    def processInputLine(input: String, playerHand: Hand, dealerHand: Hand, deck: Deck2): (String, Hand, Hand, Deck2) = {
        deck = updateDeck(playerHand, dealerHand, deck)
        input match {
            case "n" => {
                val pH = new Hand(deck)
                val dH = new Hand(deck)
                ("Starting new hand", pH, dH, new Deck2())
            }
            case "h" => playerHits(playerHand, dealerHand, deck)
            case "s" => dealerHits(playerHand, dealerHand, deck)
            case _ => ("Unknown command", playerHand, dealerHand, deck)
        }
    }

    def playerHits(playerHand: Hand, dealerHand: Hand, deck: Deck2): (String, Hand, Hand, Deck2) = {

        ("Starting new hand", new Hand(2), new Hand(2), deck)
    }

    def dealerHits(playerHand: Hand, dealerHand: Hand, deck: Deck2): (String, Hand, Hand, Deck2) = {

        ("Starting new hand", new Hand(2), new Hand(2), deck)
    }

    def updateDeck(playerHand: Hand, dealerHand: Hand, deck: Deck2): Deck2 = {

    }
}
