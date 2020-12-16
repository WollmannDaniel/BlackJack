package de.htwg.se.blackjack.aview
import de.htwg.se.blackjack.controller.Controller
import de.htwg.se.blackjack.util.Observer
import de.htwg.se.blackjack.controller.GameState._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import java.io.{ByteArrayOutputStream, StringReader}

import de.htwg.se.blackjack.aview.gui.WelcomeGui
import de.htwg.se.blackjack.model.Deck

class WelcomeGuiSpec extends AnyWordSpec with Matchers{
    /*"A Blackjack Gui" should {
        val deck = new Deck()
        val controller = new Controller(deck)
        val gui = new Gui(controller)

        "should have this output when is updated" in {
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                controller.testNotify()
            }
            out.toString should include ("gui not implemented yet")
        }

        "should have this output when command is processed" in {
            gui.processCommands("some input")
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                controller.testNotify()
            }
            out.toString should include ("gui not implemented yet")
        }
    }*/
}
