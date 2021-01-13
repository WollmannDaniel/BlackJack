package de.htwg.se.blackjack.aview
import de.htwg.se.blackjack.aview.gui.WelcomeGui
import de.htwg.se.blackjack.controller.IController

trait UserInterface {
    def processCommands(input: String): Unit
}

object UserInterface {
    def apply(kind: String, controller: IController) = kind match {
        case "gui" | "GUI" => new WelcomeGui(controller)
        case "tui" | "TUI" => new Tui(controller)
    }
}
