package de.htwg.se.blackjack

import de.htwg.se.blackjack.aview.Tui2
import de.htwg.se.blackjack.controller.Controller
import de.htwg.se.blackjack.model.{Deck2, Hand}

import scala.io.StdIn.readLine

object Blackjack2 {
    val controller = new Controller(new Hand(), new Hand())
    val tui = new Tui2(controller)
    controller.notifyObservers

    def main(args: Array[String]): Unit = {
        var input: String = ""

        do {
            input = readLine().toLowerCase()
            tui.processInputLine(input)
        } while (input != "q")
    }
}
