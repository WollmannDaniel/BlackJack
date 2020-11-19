package de.htwg.se.blackjack.controller

import de.htwg.se.blackjack.controller.GameState._
import de.htwg.se.blackjack.model.{Card, Deck, Hand, Rank, Suit}
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
                controller.gameState = Idle
                controller.newGame()
                observer.updated should be(true)
                controller.gameState should be(FirstRound)
                controller.playerHand.cards.size should be(2)
                controller.dealerHand.cards.size should be(2)
            }

            "notify its Observer after player hits and is his turn again" in {
                controller.playerHits()
                observer.updated should be(true)
                controller.playerHand.cards.size should be(3)
            }


            "notify its Observer after player wins" in {
                controller.playerHand = Hand(Vector(Card(Suit.Diamond, Rank.Ace), Card(Suit.Club, Rank.Jack)))
                controller.checkWinner()
                observer.updated should be(true)
                controller.gameState should be(Idle)
            }

            "notify its Observer after dealer gets a bust" in {
                controller.playerHand = Hand(Vector(Card(Suit.Diamond, Rank.Ten), Card(Suit.Club, Rank.Two)))
                controller.dealerHand = Hand(Vector(Card(Suit.Diamond, Rank.Ten), Card(Suit.Club, Rank.Ten), Card(Suit.Club, Rank.Two)))
                controller.checkWinner()
                observer.updated should be(true)
                controller.gameState should be(Idle)
            }

            "notify its Observer after dealer wins with bigger hand than player" in {
                controller.dealerHand = Hand(Vector(Card(Suit.Diamond, Rank.Jack), Card(Suit.Club, Rank.Jack)))
                controller.playerHand = Hand(Vector(Card(Suit.Heart, Rank.Nine), Card(Suit.Heart, Rank.Jack)))
                controller.checkWinner()
                observer.updated should be(true)
                controller.gameState should be(Idle)
            }

            "notify its Observer after player wins with bigger hand than dealer" in {
                controller.playerHand = Hand(Vector(Card(Suit.Diamond, Rank.Jack), Card(Suit.Club, Rank.Jack)))
                controller.dealerHand = Hand(Vector(Card(Suit.Heart, Rank.Nine), Card(Suit.Heart, Rank.Jack)))
                controller.checkWinner()
                observer.updated should be(true)
                controller.gameState should be(Idle)
            }

            "notify its Observer after its a draw" in {
                controller.playerHand = Hand(Vector(Card(Suit.Diamond, Rank.Two), Card(Suit.Club, Rank.Jack)))
                controller.dealerHand = Hand(Vector(Card(Suit.Heart, Rank.Two), Card(Suit.Heart, Rank.Jack)))
                controller.checkWinner()
                observer.updated should be(true)
                controller.gameState should be(Idle)
            }

            "notify its Observer after player stands" in {
                controller.playerStands()
                observer.updated should be(true)
            }

            "notify its Observer after quiting the game" in {
                controller.quitGame()
                observer.updated should be(true)
                controller.gameState should be(EndGame)
            }

            "notify its Observer after trying to create new game during round" in {
                controller.gameState = PlayersTurn
                controller.newGame()
                observer.updated should be(true)
                controller.gameState should be(PlayersTurn)
            }
        }
    }
}
