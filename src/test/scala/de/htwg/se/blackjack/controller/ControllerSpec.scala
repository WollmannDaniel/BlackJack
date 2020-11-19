package de.htwg.se.blackjack.controller

import de.htwg.se.blackjack.controller.GameState._
import de.htwg.se.blackjack.model.{Deck, Hand}
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
                var stubbedCount: Int = 0
                def isUpdated: Boolean = updated
                def getStubbedCount: Int = stubbedCount
                override def update: Unit = {
                    updated = true
                    stubbedCount += 1
                }
            }
            controller.add(observer)
            "notify its Observer 2 times after new game" in {
                controller.newGame()
                observer.updated should be(true)
                observer.stubbedCount should be(2)
                controller.gameState should be(PlayersTurn)
                controller.playerHand.cards.size should be(2)
                controller.dealerHand.cards.size should be(2)
                Deck.cards.size should be(48)
            }
            "notify its Observer 2 times after player hits" in {
                //controller.
            }
        }
    }
}
