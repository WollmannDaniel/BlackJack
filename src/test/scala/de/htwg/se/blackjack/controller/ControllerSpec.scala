package de.htwg.se.blackjack.controller

import java.io.ByteArrayOutputStream

import de.htwg.se.blackjack.controller.GameState._
import de.htwg.se.blackjack.model.{Card, Deck, GameConfig, Hand, Rank, Suit}
import de.htwg.se.blackjack.util.{Observable, Observer}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.language.reflectiveCalls

class ControllerSpec extends AnyWordSpec with Matchers {
    "A Controller" when { "observed by an Observer" should {
            var deck = new Deck()
            deck = Deck(deck.initDeck())

            val controller = new Controller(deck)
            val observer = new Observer {
                var updated: Boolean = false
                override def update: Boolean = {updated = true; updated}
            }
            controller.add(observer)

            "when game is running" in {
                controller.running = IsRunning()

                val out = new ByteArrayOutputStream();
                Console.withOut(out){
                    controller.getState()
                }
                out.toString should include ("Game is running!")
            }

            "when game is not running" in {
                controller.running = IsNotRunning()

                val out = new ByteArrayOutputStream();
                Console.withOut(out){
                    controller.getState()
                }
                out.toString should include ("No game is running!")
            }

            "notify its observer after init game" in {
                controller.performInitGame(2)
                observer.updated should be(true)
            }

            "notify its observer after init game and init dealer" in {
                val gameConfig = controller.gameConfig
                //controller.gameConfig = GameConfig(gameConfig.players, gameConfig.dealer, Deck())
                controller.initGame(2)

            }

            /*"notify its Observer after creating two new hands" in {
                controller.gameState = IDLE
                controller.newGame()
                observer.updated should be(true)
                controller.gameState should be(PLAYER_TURN)
                controller.deck.cards.size should be(48)
                controller.playerHand.cards.size should be(2)
                controller.dealerHand.cards.size should be(2)
            }

            "notify its Observer after player hits and is his turn again" in {
                controller.playerHits()
                observer.updated should be(true)
                controller.playerHand.cards.size should be(3)
            }

            "notify its Observer after player hits and has less than 21" in {
                controller.playerHand = Hand(Vector(Card(Suit.Diamond, Rank.Two), Card(Suit.Club, Rank.Two)))
                controller.playerHits()
                observer.updated should be(true)
                controller.gameState should be(PLAYER_TURN)
            }

            "notify its Observer after player hits and has more than 21" in {
                controller.playerHand = Hand(Vector(Card(Suit.Diamond, Rank.Jack), Card(Suit.Club, Rank.Nine), Card(Suit.Heart, Rank.Two)))
                controller.playerHits()
                observer.updated should be(true)
                controller.gameState should be(Idle)
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
                controller.gameState = PLAYER_TURN
                controller.newGame()
                observer.updated should be(true)
                controller.gameState should be(PLAYER_TURN)
            }
        }

        "is initialized" should {
            var deck = new Deck()
            deck = Deck(deck.initDeck())
            val controller = new Controller(deck)
            //controller.initGame()

            "have drawed 2 cards for player" in {
                controller.playerHand.cards.size should be(2)
            }
            "have drawed 2 cards for dealer" in {
                controller.dealerHand.cards.size should be(2)
            }
            "have drawed 4 cards from deck" in {
                controller.deck.cards.size should be(48)
            }
        }*/
    }}
}
