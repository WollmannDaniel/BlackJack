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
    val Jack = Value(10)
    val Queen = Value(10)
    val King = Value(10)
}

object Suit extends Enumeration {
    type Suit = Value
    val Diamond, Spade, Heart, Club = Value
}

import Rank._
import Suit._

case class Card2(suit: Suit, rank: Rank) {

}

