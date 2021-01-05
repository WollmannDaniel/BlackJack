
package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.model.deckComponent._
import de.htwg.se.blackjack.model.deckComponent.deckBaseImpl.{Card, Deck}
import de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl
import de.htwg.se.blackjack.model.playerComponent.playerComponentBaseImpl.{Hand, Player}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameConfigSpec extends AnyWordSpec with Matchers {
    "A GameConfig" when { "created" should {
        val deck = Deck(Vector(Card(Suit.Diamond, Rank.Two),
            Card(Suit.Heart, Rank.Seven),
            Card(Suit.Heart, Rank.Jack),
            Card(Suit.Spade, Rank.Ten),
            Card(Suit.Diamond, Rank.Ten),
            Card(Suit.Club, Rank.Seven)))

        val dealer = Player("any-dealer-name", Hand(Vector[Card]()))
        val gameConfig = gameConfigBaseImpl.GameConfig(Vector[Player](), dealer, deck: Deck, 0, Vector[Player]())

        "create player with default name" in {
            val config = gameConfig.createPlayer()
            config.getPlayerAtIndex(0).getName() should be("")
        }

        "create a player" in {
            val config = gameConfig.createPlayer("any-name")
            config.getPlayerAtIndex(0).getName() should be("any-name")
            config.getPlayerAtIndex(0).getHand().getCards().size should be(2)
        }

        "success in unpacking list with options cards" in {
            val optionsCards = Vector(Option(Card(Suit.Diamond, Rank.Two)), Option(Card(Suit.Spade, Rank.Two)))
            val cards = gameConfig.unpackOption(optionsCards)
            cards(0) should not be(Some(Card(Suit.Diamond, Rank.Two)))
            cards(1) should not be(Some(Card(Suit.Spade, Rank.Two)))
        }

        "fail in unpacking list with option cards" in {
            val optionsCards = Vector(Option(Card(Suit.Diamond, Rank.Two)), None)
            val thrown = intercept[Exception] {
                val cards = gameConfig.unpackOption(optionsCards)
            }
            thrown.getMessage should be("Deck doesn't have enough cards.")
        }

        "set player name" in {
            var config = gameConfig.createPlayer("any-name")
            config = config.setPlayerName("any-name-changed", 0)
            config.getPlayerAtIndex(0).getName() should be("any-name-changed")
        }

        "update player at index" in {
            val newPlayer = Player("updated-name", Hand(Vector[Card]()))
            var config = gameConfig.createPlayer("any-name-1")
            config = config.createPlayer("any-name-2")
            config = config.updatePlayerAtIndex(newPlayer, 0, deck)
            config.getPlayerAtIndex(0).getName() should be("updated-name")
            config.getPlayerAtIndex(0).getHand().getCards().size should be(0)
            config.getPlayerAtIndex(1).getName() should be("any-name-2")
            config.getPlayerAtIndex(1).getHand().getCards().size should be(2)
        }

        "increment active player index" in {
            val currentIndex = gameConfig.activePlayerIndex
            val config = gameConfig.incrementActivePlayerIndex()
            (config.getActivePlayerIndex() - currentIndex) should be(1)
        }

        "reset active player index" in {
            val config = gameConfig.resetActivePlayerIndex()
            config.getActivePlayerIndex() should be(0)
        }

        "get active player name" in {
            val config = gameConfig.createPlayer("any-name")
            config.getActivePlayerName should be("any-name")
        }

        "get active player" in {
            val config = gameConfig.createPlayer("any-name")
            config.getActivePlayer should be(config.getPlayerAtIndex(0))
        }

        "init dealer" in {
            val config = gameConfig.initDealer()
            config.getDealer().getName() should be("any-dealer-name")
            config.getDealer().getHand().getCards().size should be(2)
        }

        "get players and dealer as string" in {
            var config = gameConfig.createPlayer("any-name-1")
            config = config.createPlayer("any-name-2")
            config = config.initDealer()
            val string = config.getAllPlayerAndDealerHandsAsString
            val builder = new StringBuilder()
            string should be(builder.append(config.getPlayerAtIndex(0)).append(config.getPlayerAtIndex(1)).append(config.getDealer()).toString())
        }

        "reset itself" in {
            var config = gameConfig.createPlayer("any-name")
            config = config.resetGameConfig()
            config.getPlayers().size should be(1)
            config.getPlayerAtIndex(0).getName() should be("any-name")
            config.getDealer().getName() should be("Dealer")
            config.getDealer().getHand().getCards().size should be(2)
            //config.getDeck().cards.size should be(48)
            config.getActivePlayerIndex() should be(0)
            config.getWinners().size should be(0)
        }

        "add winner" in {
            val playerOne = Player("any-name-1", Hand(Vector[Card]()))
            val playerTwo = Player("any-name-2", Hand(Vector[Card]()))
            var config = gameConfig.addWinner(playerOne)
            config = config.addWinner(playerTwo)
            config.getWinners().size should be(2)
        }

        "set winner" in {
            val playerOne = Player("any-name-1", Hand(Vector[Card]()))
            val playerTwo = Player("any-name-2", Hand(Vector[Card]()))
            var config = gameConfig.addWinner(playerOne)
            config = config.setWinner(playerTwo)
            config.getWinners().size should be(1)
            config.getWinners()(0) should be(playerTwo)
        }

        "get winners as string" in {
            val playerOne = Player("any-name-1", Hand(Vector[Card]()))
            val playerTwo = Player("any-name-2", Hand(Vector[Card]()))
            var config = gameConfig.addWinner(playerOne)
            config = config.addWinner(playerTwo)
            val builder = new StringBuilder()
            val string = config.getAllWinnerAsString
            string should be(builder.append(playerOne.name).append(", ").append(playerTwo.name).append(" ").append("have won!").toString())
        }

        "get winner as string" in {
            val playerOne = Player("any-name-1", Hand(Vector[Card]()))
            var config = gameConfig.addWinner(playerOne)
            val builder = new StringBuilder()
            val string = config.getAllWinnerAsString
            string should be(builder.append(playerOne.name).append(" has won!").toString())
        }
    }}
}