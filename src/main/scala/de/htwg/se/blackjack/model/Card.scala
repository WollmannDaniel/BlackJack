package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.Blackjack

case class Card(suit: String, rank: String) {
    val cardRank: Map[String, String] = Map("Ace" -> "A", "Two" -> "2", "Three" -> "3", "Four" -> "4", "Five" -> "5", "Six" -> "6",
        "Seven" -> "7", "Eight" -> "8", "Nine" -> "9", "Ten" -> "10", "Jack" -> "J", "Queen" -> "Q", "King" -> "K")

    override def toString: String = {
        var output = ""
        suit match {
            case "Diamond" => output += "♦"
            case "Heart" => output += "♥"
            case "Spade" => output += "♠"
            case "Club" => output += "♣"
        }
        output += cardRank(rank)
        output
    }
}