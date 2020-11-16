package de.htwg.se.blackjack

import de.htwg.se.blackjack.aview.Tui
import de.htwg.se.blackjack.controller.Controller
import de.htwg.se.blackjack.model.{Deck, Hand}

import scala.io.StdIn.readLine

object Blackjack {
    val controller = new Controller(new Hand(), new Hand())
    val tui = new Tui(controller)
    controller.notifyObservers

    def main(args: Array[String]): Unit = {
        var input: String = ""

        do {
            input = readLine().toLowerCase()
            tui.processInputLine(input)
        } while (input != "q")
    }
}
