package de.htwg.se.blackjack.aview

import java.io.{ByteArrayOutputStream, StringReader}

import de.htwg.se.blackjack.controller.{Controller, IsRunning}
import de.htwg.se.blackjack.model.Hand
import de.htwg.se.blackjack.model.Deck
import de.htwg.se.blackjack.controller.GameState._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TuiSpec extends AnyWordSpec with Matchers {
    "A Blackjack Tui" should {
        val deck = new Deck()
        val controller = new Controller(deck)
        val tui = new Tui(controller)

        "create two new hands on input 'n'" in {
            controller.gameState = Idle
            tui.processCommands("n")
            controller.deck.cards.size should be(48)
            controller.playerHand.cards.size should be(2)
            controller.dealerHand.cards.size should be(2)
            controller.gameState should be(FirstRound)
        }

        "add a card to player's hand on input 'h'" in {
            tui.processCommands("h")
            controller.playerHand.cards.size should be(3)
        }

        "add a card to dealer's hand on input 's'" in {
            tui.processCommands("s")
            controller.dealerHand.cards.size should be >= 2
        }

        "quit the game on input 'q'" in {
            tui.processCommands("q")
            controller.gameState should be(EndGame)
        }

        "get state in input 'state'" in {
            controller.running = IsRunning()
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                tui.processCommands("state")
            }
            out.toString should include ("Game is running!")
        }

        "should have this output when unknown command" in {
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                tui.processCommands("this is a unknown command")
            }
            out.toString should include ("unknown command")
        }

        "should have this output when PlayerLost" in {
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                controller.gameState = PlayerLost
                controller.testNotify()
            }
            out.toString should include ("The Dealer has won!")
        }

        "should have this output when PlayerWon" in {
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                controller.gameState = PlayerWon
                controller.testNotify()
            }
            out.toString should include ("The Player has won!")
        }

        "should have this output when Draw" in {
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                controller.gameState = Draw
                controller.testNotify()
            }
            out.toString should include ("It's a draw!")
        }

        "should have this output when has a BlackJack in FirstRound" in {
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                controller.gameState = BlackJack
                controller.testNotify()
            }
            out.toString should include ("Lucky boy! The Player has won")
        }

        "should have this output when is WrongCmd" in {
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                controller.gameState = WrongCmd
                controller.testNotify()
            }
            out.toString should include ("Command not allowed!")
        }

        "should have this output when is EndGame" in {
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                controller.gameState = EndGame
                controller.testNotify()
            }
            out.toString should include ("Good bye!")
        }

        "should have this output when is PlayersTurn" in {
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                controller.gameState = PLAYER_TURN
                controller.testNotify()
            }
            out.toString should include ("It's your turn. Hit or stand?(h/s)")
        }
    }
}
