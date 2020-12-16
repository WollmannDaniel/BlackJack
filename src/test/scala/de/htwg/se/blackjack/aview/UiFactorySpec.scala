package de.htwg.se.blackjack.aview

import de.htwg.se.blackjack.aview.gui.WelcomeGui
import de.htwg.se.blackjack.controller.Controller
import de.htwg.se.blackjack.model.Deck
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.language.reflectiveCalls

class UiFactorySpec extends AnyWordSpec with Matchers{
    "A UserInterface" should {
        val userInterface = new UserInterface {
            var stubbedInput = ""

            override def processCommands(input: String) = {
                stubbedInput = input
            }
        }

        "process command" in {
            userInterface.processCommands("h")
            userInterface.stubbedInput should equal("h")
        }
    }

    "The Tui UserInterface" when { "is created" should {
        val deck = new Deck()
        val controller = new Controller(deck)
        val uiType = "tui"
        val ui = UserInterface(uiType, controller)

        "have instance of Tui" in {
            ui.isInstanceOf[Tui] should be(true)
        }
    }}

    "The Gui UserInterface" when { "is created" should {
        val deck = new Deck()
        val controller = new Controller(deck)
        val uiType = "gui"
        val ui = UserInterface(uiType, controller)

        "have instance of Gui" in {
            ui.isInstanceOf[WelcomeGui] should be(true)
        }
    }}
}
