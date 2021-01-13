package de.htwg.se.blackjack.model.deckComponent.deckBaseImpl

import de.htwg.se.blackjack.model.deckComponent.ICard
import de.htwg.se.blackjack.model.deckComponent.Rank._
import de.htwg.se.blackjack.model.deckComponent.Suit._

case class Card(suit: Suit, rank: Rank) extends ICard {
    override def toString: String = {
        val builder = new StringBuilder()
        suit match {
            case Diamond => builder.append("♦")
            case Heart => builder.append("♥")
            case Spade => builder.append("♠")
            case Club => builder.append("♣")
        }
        builder.append(mapCardRank())
        builder.append(",")
        builder.toString()
    }

    def mapCardRank(): String = {
        rank match {
            case Ace => "A"
            case Jack => "J"
            case Queen => "Q"
            case King => "K"
            case _ => rank.id.toString
        }
    }

    def mapCardValue(): Int = {
        rank match {
            case Jack | Queen | King => 10
            case _ => rank.id
        }
    }

    def mapCardSymbol(): String = {
        val builder = new StringBuilder(mapCardRank())
        suit match {
            case Diamond => builder.append("D")
            case Heart => builder.append("H")
            case Spade => builder.append("S")
            case Club => builder.append("C")
        }
        builder.toString()
    }

    def getRank(): Rank = {
        rank
    }

    def getSuit(): Suit = {
        suit
    }
}

