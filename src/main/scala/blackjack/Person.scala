package blackjack

case class Person(name: String, kartenhand: Array[String]) {
    override def toString: String = {
        var output = s"Kartenhand des $name's: ["
        output += kartenhand.mkString(", ")
        output += "]\n"
        return output
    }
}
