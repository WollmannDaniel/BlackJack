package de.htwg.se.blackjack.controller

import de.htwg.se.blackjack.model.Hand
import de.htwg.se.blackjack.util.Observer

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec with Matchers {
    "A Controller" when {
        "observed by an Observer" should {
            val playerHand = new Hand()
            val dealerHand = new Hand()
            val controller = new Controller(playerHand, dealerHand)
            val observer = new Observer {
                var updated: Boolean = false
                def isUpdated: Boolean = updated
                override def update: Unit = updated = true
            }
            controller.add(observer)
        }
    }
}
