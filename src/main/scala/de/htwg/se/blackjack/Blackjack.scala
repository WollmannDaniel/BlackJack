package de.htwg.se.blackjack

import de.htwg.se.blackjack.aview.gui.WelcomeGui
import de.htwg.se.blackjack.aview.{Tui, UserInterface}
import de.htwg.se.blackjack.controller.{Controller, RefreshData}
import de.htwg.se.blackjack.model.{Deck, Hand}

import scala.io.StdIn.readLine

object Blackjack {
    val deck = new Deck()
    val controller = new Controller(deck)

    def main(args: Array[String]): Unit = {
        var input: String = ""

        val gui = new WelcomeGui(controller)
        val tui = new Tui(controller)
        controller.publish(new RefreshData)

        do {
            input = readLine().toLowerCase()
            tui.processCommands(input)
        } while (input != "q")
    }
}
