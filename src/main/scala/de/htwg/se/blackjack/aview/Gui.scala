package de.htwg.se.blackjack.aview

import de.htwg.se.blackjack.util.Observer
import de.htwg.se.blackjack.controller.Controller

class Gui(controller: Controller) extends Observer with UserInterface{
    controller.add(this)

    override def processCommands(input: String): Unit = {
        println("gui not implemented yet")
    }

    override def update: Boolean = {
        println("gui not implemented yet")
        true
    }
}
