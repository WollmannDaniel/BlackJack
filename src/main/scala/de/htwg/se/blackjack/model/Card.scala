package de.htwg.se.blackjack.model

object Rank extends Enumeration {
    type Rank = Value
    val Ace = Value(11)
    val Two = Value(2)
    val Three = Value(3)
    val Four = Value(4)
    val Five = Value(5)
    val Six = Value(6)
    val Seven = Value(7)
    val Eight = Value(8)
    val Nine = Value(9)
    val Ten = Value(10)
    val Jack = Value(12)
    val Queen = Value(13)
    val King = Value(14)
}

object Suit extends Enumeration {
    type Suit = Value
    val Diamond, Spade, Heart, Club = Value
}

import Rank._
import Suit._

case class Card(suit: Suit, rank: Rank) {
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
}

