package de.htwg.se.blackjack

import de.htwg.se.blackjack.aview.{Tui, UserInterface}
import de.htwg.se.blackjack.controller.Controller
import de.htwg.se.blackjack.model.{Deck, Hand}

import scala.io.StdIn.readLine

object Blackjack {
    val deck = new Deck()
    val controller = new Controller(deck)

    def main(args: Array[String]): Unit = {
        var input: String = ""

        val uiType = "tui"
        val ui = UserInterface(uiType, controller)
        controller.notifyObservers

        do {
            input = readLine().toLowerCase()
            ui.processCommands(input)
        } while (input != "q")
    }
}
