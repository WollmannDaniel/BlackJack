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
                override def update: Unit = {
                    updated = true
                }
            }
            controller.add(observer)

            "notify its Observer after creating two new hands" in {
                controller.newGame()
                observer.updated should be(true)
                controller.gameState should be(FirstRound)
                Deck.cards.size should be(52)
                controller.playerHand.cards.size should be(2)
                controller.dealerHand.cards.size should be(2)
            }

            "notify its Observer after player hits" in {
                controller.playerHits()
                observer.updated should be(true)
                controller.playerHand.cards.size should be(3)
            }

            "notify its Observer after player stands" in {
                //controller.
            }

            "notify its Observer after quiting the game" in {
                //controller.
            }
        }
    }
}
