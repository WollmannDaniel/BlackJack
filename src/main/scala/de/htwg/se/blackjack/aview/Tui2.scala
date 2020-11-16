package de.htwg.se.blackjack.aview

import de.htwg.se.blackjack.controller.Controller
import de.htwg.se.blackjack.model.{Deck2, Hand}
import de.htwg.se.blackjack.util.Observer

class Tui2(controller: Controller) extends Observer {
    controller.add(this)

    def processInputLine(input: String): Unit = {
        input match {
            case _ =>
        }
    }

    override def update: Unit = println(controller.gameState)
}
