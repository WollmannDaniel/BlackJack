package de.htwg.se.blackjack.model

case class Person(name: String, cards: Vector[String]) {
    override def toString: String = {
        var output = s"$name's hand: ["
        output += cards.mkString(", ")
        output += "]\n"
        return output
    }
}
