package de.htwg.se.blackjack.aview

import java.io.{ByteArrayOutputStream, StringReader}

import de.htwg.se.blackjack.controller.GameState._
import de.htwg.se.blackjack.controller.controllerComponent.controllerBaseImpl.{Controller, IsRunning}
import de.htwg.se.blackjack.model.deckComponent.ICard
import de.htwg.se.blackjack.model.deckComponent.deckBaseImpl.{Card, Deck}
import de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl.GameConfig
import de.htwg.se.blackjack.model.playerComponent.IPlayer
import de.htwg.se.blackjack.model.playerComponent.playerComponentBaseImpl.{Hand, Player}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.blackjack.model.deckComponent._
import de.htwg.se.blackjack.controller._
import de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl

class TuiSpec extends AnyWordSpec with Matchers {
    "A Blackjack Tui" should {
        "process command 'z' on state WELCOME" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val controller = new Controller(gameConfig)
            val tui = new Tui(controller)
            controller.gameState = WELCOME
            tui.processCommands("z")
            controller.gameState should be(WELCOME)
        }

        "process command 'y' on state WELCOME" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            tmpController.gameState = WELCOME
            tui.processCommands("y")
            tmpController.gameState should be(WELCOME)
        }

        "process player amount on state WELCOME" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            tmpController.gameState = WELCOME
            tui.processCommands("2")
            tmpController.gameState should be(NAME_CREATION)
        }

        "process command 'z' on state NAME_CREATION" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            tmpController.gameState = NAME_CREATION
            tui.processCommands("z")
            tmpController.gameState should be(NAME_CREATION)
        }

        "process command 'y' on state NAME_CREATION" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            tmpController.gameState = NAME_CREATION
            tui.processCommands("y")
            tmpController.gameState should be(NAME_CREATION)
        }

        "process player name on state NAME_CREATION" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            tmpController.gameState = WELCOME
            tui.processCommands("1")
            tui.processCommands("any-name")
            tmpController.gameConfig.getPlayerAtIndex(0).getName() should be("any-name")
        }

        "notice user to enter correct number of players" in {
            val out = new ByteArrayOutputStream();
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            Console.withOut(out) {
                tui.initPlayers("5")
            }
            out.toString should include("There may only be 1,2,3 or 4 players!")
        }

        "perform init game on correct player amount" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            tui.initPlayers("2")
            tmpController.gameState should be(NAME_CREATION)
        }

        "process command 's'" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            tmpController.gameState = WELCOME
            tui.processCommands("2")
            tui.processCommands("any-name-1")
            tui.processCommands("any-name-2")
            tui.processCommands("s")
            tmpController.gameState should be(PLAYER_TURN)
        }

        "process command 'h'" in {
            val deck = Deck(Vector(Card(Suit.Diamond, Rank.Two),
                Card(Suit.Heart, Rank.Seven),
                Card(Suit.Heart, Rank.Jack)))
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            val player = Player("updated-name", Hand(Vector[Card](Card(Suit.Diamond, Rank.Ace), Card(Suit.Spade, Rank.Ace))))
            tmpController.gameState = WELCOME
            tui.processCommands("1")
            tui.processCommands("any-name")
            tmpController.gameConfig = tmpController.gameConfig.updatePlayerAtIndex(player, 0, deck)
            tui.processCommands("h")
            tmpController.gameState should be(PLAYER_TURN)
        }

        "process command 'n'" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            tmpController.gameState = WELCOME
            tui.processCommands("1")
            tui.processCommands("any-name")
            tmpController.gameState = IDLE
            tui.processCommands("n")
            tmpController.gameState should be(PLAYER_TURN)
        }

        "process command 'q'" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            tmpController.gameState = PLAYER_TURN
            tui.processCommands("q")
            tmpController.gameState should be(END_GAME)
        }

        "process command 'z'" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            tmpController.gameState = WELCOME
            tui.processCommands("1")
            tui.processCommands("any-name")
            tmpController.gameState = PLAYER_TURN
            tui.processCommands("z")
            tmpController.gameState should be(NAME_CREATION)
        }

        "process command 'state'" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            tmpController.gameState = PLAYER_TURN
            tmpController.running = IsRunning()
            val out = new ByteArrayOutputStream();
            Console.withOut(out) {
                tui.processCommands("state")
            }
            out.toString should include("Game is running!")
        }

        "process command 'save' when PLAYER_TURN" in {
            val deck = new Deck()
            val playerHand = Hand(Vector(Card(Suit.Diamond, Rank.Two), Card(Suit.Club, Rank.Jack)))
            val dealerHand = Hand(Vector(Card(Suit.Heart, Rank.Two), Card(Suit.Heart, Rank.Jack)))
            val tempGameConfig = gameConfigBaseImpl.GameConfig(Vector[Player](Player("Player1", playerHand)), Player("Dealer", dealerHand), deck.resetDeck(), 0, Vector[Player]())
            val tmpController = new Controller(tempGameConfig)

            tmpController.gameState = PLAYER_TURN
            val tui = new Tui(tmpController)
            val out = new ByteArrayOutputStream();
            Console.withOut(out) {
                tui.processCommands("save")
            }
            out.toString should include("Game was saved!\n")

            tui.processCommands("load")
            tui.getController should be equals(tmpController)
        }

        "process command 'save' when PLAYER_WON" in {
            val deck = new Deck()
            val playerHand = Hand(Vector(Card(Suit.Diamond, Rank.Two), Card(Suit.Club, Rank.Jack)))
            val dealerHand = Hand(Vector(Card(Suit.Heart, Rank.Two), Card(Suit.Heart, Rank.Jack)))
            val tempGameConfig = gameConfigBaseImpl.GameConfig(Vector[Player](Player("Player1", playerHand)), Player("Dealer", dealerHand), deck.resetDeck(), 0, Vector[Player]())
            val tmpController = new Controller(tempGameConfig)

            tmpController.gameState = PLAYER_WON
            val tui = new Tui(tmpController)
            val out = new ByteArrayOutputStream();
            Console.withOut(out) {
                tui.processCommands("save")
            }
            out.toString should include("Game was saved!\n")

            tui.processCommands("load")
            tui.getController should be equals(tmpController)
        }

        "notify user that command is unknown" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            tmpController.gameState = PLAYER_TURN
            val out = new ByteArrayOutputStream();
            Console.withOut(out) {
                tui.processCommands("pay me")
            }
            out.toString should be("unknown command")
        }

        "should have this output when WELCOME" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                tmpController.gameState = WELCOME
                tui.update
            }
            out.toString should be ("Starting new game!\nThe deck was shuffled.\nHow many players want to play?\n")
        }

        "should have this output on NAME_CREATION" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                tmpController.gameState = NAME_CREATION
                tui.update
            }
            out.toString should be ("Please enter Playername 1:\n")
        }

        "should have this output on NEW_GAME_STARTED" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                tmpController.gameState = NEW_GAME_STARTED
                tui.update
            }
            out.toString should be ("Starting new game!\nThe deck was shuffled.\n")
        }

        "should have this output on PLAYER_TURN" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            val out = new ByteArrayOutputStream();
            tmpController.gameState = WELCOME
            tui.processCommands("1")
            tui.processCommands("any-name")
            Console.withOut(out){
                tmpController.gameState = PLAYER_TURN
                tui.update
            }
            val builder = new StringBuilder();
            out.toString should be (builder.append("any-name's turn. Hit or stand?(h/s)\n\n").append(tmpController.gameStateToString).append("\n").toString())
        }

        "should have this output on PLAYER_LOST" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            val out = new ByteArrayOutputStream();
            tmpController.gameState = WELCOME
            tui.processCommands("1")
            tui.processCommands("any-name")
            Console.withOut(out){
                tmpController.gameState = PLAYER_LOST
                tui.update
            }
            val builder = new StringBuilder();
            out.toString should be (builder.append("any-name's hand value went over twenty-one!\n\n").append(tmpController.gameStateToString).append("\n").toString())
        }

        "should have this output on DEALERS_TURN" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            val out = new ByteArrayOutputStream();
            tmpController.gameState = WELCOME
            tui.processCommands("1")
            tui.processCommands("any-name")
            Console.withOut(out){
                tmpController.gameState = DEALERS_TURN
                tui.update
            }
            val builder = new StringBuilder();
        }

//        "should have this output on IDLE" in {
//            val deck = new Deck()
//            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
//            val tmpController = new Controller(gameConfig)
//            val tui = new Tui(tmpController)
//            val out = new ByteArrayOutputStream();
//            tmpController.gameState = IDLE
//            Console.withOut(out){
//                tui.update
//            }
//            val builder = new StringBuilder();
//            out.toString should be ("q = quit, n = start new game\n")
//        }

        "should have this output on PLAYER_WON" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            val out = new ByteArrayOutputStream();
            tmpController.gameState = WELCOME
            tui.processCommands("1")
            tui.processCommands("any-name")
            Console.withOut(out){
                tmpController.gameState = PLAYER_WON
                tmpController.gameConfig = tmpController.gameConfig.setWinner(tmpController.gameConfig.getPlayerAtIndex(0))
                tui.update
            }
            val sb = new StringBuilder()
            sb.append(tmpController.gameStateToString)
                .append("\n")
                .append("q = quit, n = start new game")
                .append("\n")
            val outputString = sb.toString()
            out.toString should be (outputString)
        }

        "should have this output on DRAW" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            val out = new ByteArrayOutputStream();
            tmpController.gameState = WELCOME
            tui.processCommands("1")
            tui.processCommands("any-name")
            Console.withOut(out){
                tmpController.gameState = DRAW
                tui.update
            }
            val sb = new StringBuilder()
            sb.append("It's a draw!")
                .append("\n")
                .append(tmpController.gameStateToString)
                .append("\n")
                .append("q = quit, n = start new game")
                .append("\n")
            val outputString = sb.toString()
            out.toString should be (outputString)
        }

        "should have this output on WRONG_CMD" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                tmpController.gameState = WRONG_CMD
                tui.update
            }
            val builder = new StringBuilder();
            out.toString should be (builder.append("Command not allowed!\n").toString())
        }

        "should have this output on END_GAME" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)
            val out = new ByteArrayOutputStream();
            Console.withOut(out){
                tmpController.gameState = END_GAME
                tui.update
            }
            val builder = new StringBuilder();
            out.toString should be (builder.append("Good bye!").toString())
        }

        "should have this output on EMPTY_DECK" in {
            val deck = new Deck()
            var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
            val tmpController = new Controller(gameConfig)
            val tui = new Tui(tmpController)

            val thrown = intercept[Exception] {
                tmpController.gameState = EMPTY_DECK
                tui.update
            }
            thrown.getMessage should be("Deck doesn't have enough cards.")
        }
    }
}
