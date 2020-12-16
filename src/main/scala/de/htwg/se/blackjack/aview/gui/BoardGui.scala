package de.htwg.se.blackjack.aview.gui

import de.htwg.se.blackjack.controller.Controller

import scala.swing.Frame

class BoardGui(controller: Controller) extends Frame {
    listenTo(controller)
    title = "Blackjack"
}
