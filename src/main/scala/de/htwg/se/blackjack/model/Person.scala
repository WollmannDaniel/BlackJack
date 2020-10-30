package de.htwg.se.blackjack.model

case class Person(name: String, cards: Array[String]) {
    override def toString: String = {
        var output = s"Kartenhand des $name's: ["
        output += cards.mkString(", ")
        output += "]\n"
        return output
    }
}
