package de.htwg.se.blackjack.aview
import de.htwg.se.blackjack.aview.gui.WelcomeGui
import de.htwg.se.blackjack.controller.Controller

trait UserInterface {
    def processCommands(input: String): Unit
}

object UserInterface {
    def apply(kind: String, controller: Controller) = kind match {
        case "gui" | "GUI" => new WelcomeGui(controller)
        case "tui" | "TUI" => new Tui(controller)
    }
}
