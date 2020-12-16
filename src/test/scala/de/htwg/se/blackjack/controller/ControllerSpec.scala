package de.htwg.se.blackjack.controller

import java.io.ByteArrayOutputStream

import de.htwg.se.blackjack.controller.GameState._
import de.htwg.se.blackjack.model.{Card, Deck, GameConfig, Hand, Player, Rank, Suit}
import de.htwg.se.blackjack.util.{Observable, Observer}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.language.reflectiveCalls

class ControllerSpec extends AnyWordSpec with Matchers {
    /*"A Controller" when {
        "observed by an Observer" should {
            var deck = new Deck()
            deck = Deck(deck.initDeck())

            val controller = new Controller(deck)
            val observer = new Observer {
                var updated: Boolean = false

                override def update: Boolean = {
                    updated = true; updated
                }
            }
            controller.add(observer)

            "when game is running" in {
                controller.running = IsRunning()

                val out = new ByteArrayOutputStream();
                Console.withOut(out) {
                    controller.getState()
                }
                out.toString should include("Game is running!")
            }

            "when game is not running" in {
                controller.running = IsNotRunning()

                val out = new ByteArrayOutputStream();
                Console.withOut(out) {
                    controller.getState()
                }
                out.toString should include("No game is running!")
            }

            "notify its observer after init game" in {
                controller.performInitGame(2)
                observer.updated should be(true)
            }

            "notify its observer after init game and init dealer" in {
                val gameConfig = controller.gameConfig
                controller.gameConfig = GameConfig(gameConfig.players, gameConfig.dealer, Deck(Vector[Card]()), 0, Vector[Player]())
                controller.initGame(2)
                controller.gameState = EMPTY_DECK
                observer.updated should be(true)
            }

            "notify its observer after init game and init player" in {
                val gameConfig = controller.gameConfig
                val deck = Deck(Vector(Card(Suit.Diamond, Rank.Two), Card(Suit.Heart, Rank.Seven)))
                controller.gameConfig = GameConfig(gameConfig.players, gameConfig.dealer, deck, 0, Vector[Player]())
                controller.initGame(1)
                controller.gameState = EMPTY_DECK
                observer.updated should be(true)
            }

            "get active player name" in {
                val dealer = Player("any-dealer-name", Hand(Vector[Card]()))
                controller.gameConfig = GameConfig(Vector[Player](), dealer, deck: Deck, 0, Vector[Player]())
                controller.gameConfig = controller.gameConfig.createPlayer("any-name")
                controller.getActivePlayerName should be("any-name")
            }

            "get player name" in {
                val dealer = Player("any-dealer-name", Hand(Vector[Card]()))
                controller.gameConfig = GameConfig(Vector[Player](), dealer, deck: Deck, 5, Vector[Player]())
                controller.getPlayerName should be("Please enter Playername 6:")
            }

            "notify its observer when performing set player name" in {
                val dealer = Player("any-dealer-name", Hand(Vector[Card]()))
                val playerList = Vector[Player](Player("any-name", Hand(Vector[Card]())))
                controller.gameConfig = GameConfig(playerList, dealer, deck: Deck, 0, Vector[Player]())
                controller.performSetPlayerName("new-player-name")
                observer.updated should be(true)
                controller.gameConfig.getActivePlayerName should be("new-player-name")
            }

            "notify its observer when drawing new card but hand value going over 21" in {
                val newDeck = Deck(Vector(Card(Suit.Diamond, Rank.Queen), Card(Suit.Diamond, Rank.Ten), Card(Suit.Diamond, Rank.Jack)))
                val dealer = Player("any-dealer-name", Hand(Vector[Card]()))
                val player = Player("any-name", Hand(Vector(Card(Suit.Diamond, Rank.Ten), Card(Suit.Heart, Rank.Jack))))
                controller.gameConfig = GameConfig(Vector[Player](player), dealer, newDeck, 0, Vector[Player]())
                controller.playerHits()
                controller.gameState should be(IDLE)
                observer.updated should be(true)
            }

            "notify its observer when drawing new card but hand value under 21" in {
                val newDeck = Deck(Vector(Card(Suit.Diamond, Rank.Ace)))
                val dealer = Player("any-dealer-name", Hand(Vector[Card]()))
                val player = Player("any-name", Hand(Vector(Card(Suit.Diamond, Rank.Ten), Card(Suit.Heart, Rank.Jack))))
                controller.gameConfig = GameConfig(Vector[Player](player), dealer, newDeck, 0, Vector[Player]())
                controller.playerHits()
                controller.gameState should be(PLAYER_TURN)
                observer.updated should be(true)
            }

            "notify its observer when failing on drawing new card" in {
                val newDeck = Deck(Vector[Card]())
                val dealer = Player("any-dealer-name", Hand(Vector[Card]()))
                val player = Player("any-name", Hand(Vector(Card(Suit.Diamond, Rank.Ten), Card(Suit.Heart, Rank.Jack))))
                controller.gameConfig = GameConfig(Vector[Player](player), dealer, newDeck, 0, Vector[Player]())
                controller.playerHits()
                controller.gameState should be(EMPTY_DECK)
                observer.updated should be(true)
            }

            "notify its observer when there are other players left" in {
                val newDeck = Deck(Vector[Card]())
                val dealer = Player("any-dealer-name", Hand(Vector[Card]()))
                val playerOne = Player("any-name-1", Hand(Vector(Card(Suit.Diamond, Rank.Ten), Card(Suit.Heart, Rank.Jack))))
                val playerTwo = Player("any-name-2", Hand(Vector(Card(Suit.Diamond, Rank.Eight), Card(Suit.Heart, Rank.Seven))))
                controller.gameConfig = GameConfig(Vector[Player](playerOne, playerTwo), dealer, newDeck, 0, Vector[Player]())
                controller.playerStands()
                controller.gameState = PLAYER_TURN
                observer.updated should be(true)
            }

            "notify its observer when there is no next player" in {
                val newDeck = Deck(Vector[Card]())
                val dealer = Player("any-dealer-name", Hand(Vector(Card(Suit.Diamond, Rank.Ten), Card(Suit.Heart, Rank.Jack), Card(Suit.Spade, Rank.Jack))))
                val playerOne = Player("any-name-1", Hand(Vector(Card(Suit.Diamond, Rank.Ten), Card(Suit.Heart, Rank.Jack))))
                val playerTwo = Player("any-name-2", Hand(Vector(Card(Suit.Diamond, Rank.Eight), Card(Suit.Heart, Rank.Seven))))
                controller.gameConfig = GameConfig(Vector[Player](playerOne, playerTwo), dealer, newDeck, 1, Vector[Player]())
                controller.playerStands()
                controller.gameState = PLAYER_WON
                observer.updated should be(true)
            }

            "notify its Observer after manageDealerLogic and its dealer turn" in {
                val smallDeck = Deck(Vector(Card(Suit.Diamond, Rank.Seven), Card(Suit.Club, Rank.Seven)))
                val tempController = controller
                val dealerHand = Hand(Vector(Card(Suit.Diamond, Rank.Ten), Card(Suit.Club, Rank.Two)))
                tempController.gameConfig = GameConfig(Vector[Player](), Player("Dealer", dealerHand), smallDeck, 0, Vector[Player]())
                tempController.manageDealerLogic()
                observer.updated should be(true)
                controller.gameState should be(PLAYER_WON)
            }

            "notify its Observer after manageDealerLogic and the deck is empty" in {
                val emptyDeck = Deck(Vector())
                val tempController = controller
                tempController.gameConfig = GameConfig(Vector[Player](), Player("Dealer", Hand(Vector())), emptyDeck, 0, Vector[Player]())
                tempController.manageDealerLogic()
                observer.updated should be(true)
                controller.gameState should be(EMPTY_DECK)
            }

            "notify its Observer after dealer gets a bust" in {
                val tempController = controller
                val playerHand = Hand(Vector(Card(Suit.Diamond, Rank.Ten), Card(Suit.Club, Rank.Two)))
                val dealerHand = Hand(Vector(Card(Suit.Diamond, Rank.Ten), Card(Suit.Club, Rank.Ten), Card(Suit.Club, Rank.Two)))
                tempController.gameConfig = GameConfig(Vector[Player](Player("Player1", playerHand)), Player("Dealer", dealerHand), deck.resetDeck(), 0, Vector[Player]())
                tempController.checkWinner()
                observer.updated should be(true)
                controller.gameState should be(IDLE)
            }

            "notify its Observer after dealer wins with bigger hand than player" in {
                val tempController = controller
                val dealerHand = Hand(Vector(Card(Suit.Diamond, Rank.Jack), Card(Suit.Club, Rank.Jack)))
                val playerHand = Hand(Vector(Card(Suit.Heart, Rank.Nine), Card(Suit.Heart, Rank.Jack)))
                tempController.gameConfig = GameConfig(Vector[Player](Player("Player1", playerHand)), Player("Dealer", dealerHand), deck.resetDeck(), 0, Vector[Player]())
                controller.checkWinner()
                observer.updated should be(true)
                controller.gameState should be(IDLE)
            }

            "notify its Observer after player wins with bigger hand than dealer" in {
                val tempController = controller
                val playerHand = Hand(Vector(Card(Suit.Diamond, Rank.Jack), Card(Suit.Club, Rank.Jack)))
                val dealerHand = Hand(Vector(Card(Suit.Heart, Rank.Nine), Card(Suit.Heart, Rank.Jack)))
                tempController.gameConfig = GameConfig(Vector[Player](Player("Player1", playerHand)), Player("Dealer", dealerHand), deck.resetDeck(), 0, Vector[Player]())
                controller.checkWinner()
                observer.updated should be(true)
                controller.gameState should be(IDLE)
            }

            "notify its Observer after its a draw" in {
                val tempController = controller
                val playerHand = Hand(Vector(Card(Suit.Diamond, Rank.Two), Card(Suit.Club, Rank.Jack)))
                val dealerHand = Hand(Vector(Card(Suit.Heart, Rank.Two), Card(Suit.Heart, Rank.Jack)))
                tempController.gameConfig = GameConfig(Vector[Player](Player("Player1", playerHand)), Player("Dealer", dealerHand), deck.resetDeck(), 0, Vector[Player]())
                controller.checkWinner()
                observer.updated should be(true)
                controller.gameState should be(IDLE)
            }

            "notify its Observer after quiting the game" in {
                controller.quitGame()
                observer.updated should be(true)
                controller.gameState should be(END_GAME)
            }

            "notify its Observer after trying to create new game during round" in {
                controller.gameState = PLAYER_TURN
                controller.newGame()
                observer.updated should be(true)
                controller.gameState should be(PLAYER_TURN)
            }

            "notify its Observer after trying to create new game" in {
                controller.gameState = IDLE
                controller.newGame()
                observer.updated should be(true)
                controller.gameState should be(PLAYER_TURN)
            }

            "when undo is performed" in {
                val playerHandCards = Vector(Card(Suit.Diamond, Rank.Jack), Card(Suit.Club, Rank.Nine))
                val tempController = controller
                tempController.gameConfig = GameConfig(Vector[Player](Player("", Hand(playerHandCards))), Player("Dealer", Hand(Vector[Card]())), deck.resetDeck(), 0, Vector[Player]())

                tempController.performSetPlayerName("playerName")
                tempController.gameConfig.players(0).name should be("playerName")
                tempController.undo
                tempController.gameConfig.players(0).name should be("")
            }

            "when redo is performed" in {
                val playerHandCards = Vector(Card(Suit.Diamond, Rank.Jack), Card(Suit.Club, Rank.Nine))
                val tempController = controller
                tempController.gameConfig = GameConfig(Vector[Player](Player("", Hand(playerHandCards))), Player("Dealer", Hand(Vector[Card]())), deck.resetDeck(), 0, Vector[Player]())

                tempController.performSetPlayerName("playerName")
                tempController.gameConfig.players(0).name should be("playerName")
                tempController.undo
                tempController.gameConfig.players(0).name should be("")
                tempController.redo
                tempController.gameConfig.players(0).name should be("playerName")
            }

            "notify its observers after test notify is called" in {
                val tempController = controller
                tempController.gameState = EMPTY_DECK
                tempController.testNotify()
                observer.updated should be(true)
                controller.gameState should be(EMPTY_DECK)
            }

            "have these gameStateToString representation when gameState is PLAYER_TURN" in {
                val tempController = controller
                val playerHand = Hand(Vector(Card(Suit.Spade, Rank.Queen), Card(Suit.Heart, Rank.Jack)))
                val dealerHand = Hand(Vector(Card(Suit.Diamond, Rank.Nine), Card(Suit.Heart, Rank.Two)))
                tempController.gameConfig = GameConfig(Vector[Player](Player("Player1", playerHand)), Player("Dealer", dealerHand), deck.resetDeck(), 0, Vector[Player]())
                tempController.gameState = PLAYER_TURN
                tempController.gameStateToString should be("Player1 [♠Q,♥J] = 20\nDealer [♦9, ?]\n")
            }

            "have these gameStateToString representation when gameState is PLAYER_WON" in {
                val tempController = controller
                val dealerHand = Hand(Vector(Card(Suit.Spade, Rank.Queen), Card(Suit.Heart, Rank.Jack)))
                val playerHand = Hand(Vector(Card(Suit.Diamond, Rank.Nine), Card(Suit.Spade, Rank.Eight)))
                tempController.gameConfig = GameConfig(Vector[Player](Player("Player1", playerHand)), Player("Dealer", dealerHand), deck.resetDeck(), 0, Vector[Player]())
                tempController.gameState = PLAYER_WON
                val outputString = tempController.gameConfig.getAllWinnerAsString
                tempController.gameStateToString should be(outputString)
            }

            "have these gameStateToString representation when gameState is DEALERS_TURN" in {
                val tempController = controller
                val dealerHand = Hand(Vector(Card(Suit.Spade, Rank.Queen), Card(Suit.Heart, Rank.Jack)))
                val playerHand = Hand(Vector(Card(Suit.Diamond, Rank.Nine), Card(Suit.Spade, Rank.Eight)))
                tempController.gameConfig = GameConfig(Vector[Player](Player("Player1", playerHand)), Player("Dealer", dealerHand), deck.resetDeck(), 0, Vector[Player]())
                tempController.gameState = DEALERS_TURN
                val outputString = tempController.gameConfig.getAllPlayerAndDealerHandsAsString
                tempController.gameStateToString should be(outputString)
            }
        }
    }*/
}
