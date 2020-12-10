package de.htwg.se.blackjack.aview
import de.htwg.se.blackjack.controller.Controller

trait UserInterface {
    def processCommands(input: String): Unit
}

object UserInterface {
    def apply(kind: String, controller: Controller) = kind match {
        case "gui" | "GUI" => new Gui(controller)
        case "tui" | "TUI" => new Tui(controller)
    }
}
