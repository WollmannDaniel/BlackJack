package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.Blackjack

case class Card(suit: String, rank: String) {
    override def toString: String = {
        var output = ""
        suit match {
            case "Diamond" => output += "♦"
            case "Heart" => output += "♥"
            case "Spade" => output += "♠"
            case "Club" => output += "♣"
        }
        output += Blackjack.cardValues(rank)
        output
    }
}