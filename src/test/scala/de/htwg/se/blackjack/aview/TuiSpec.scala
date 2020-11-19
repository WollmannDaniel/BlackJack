package de.htwg.se.blackjack.aview

import de.htwg.se.blackjack.controller.Controller
import de.htwg.se.blackjack.model.Hand
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TuiSpec extends AnyWordSpec with Matchers {
    "A Blackjack Tui" should {
        val playerHand = new Hand()
        val dealerHand = new Hand()
        val controller = new Controller(playerHand, dealerHand)
        "create new game on input 'n'" in {

        }
    }
}
