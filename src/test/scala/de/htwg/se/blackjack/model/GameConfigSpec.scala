/*
package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.model.deckComponent.Deck
import de.htwg.se.blackjack.model.deckComponent.deckBaseImpl.{Card, Deck, Rank, Suit}
import de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl
import de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl.GameConfig
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
            config.players(0).name should be("")
        }

        "create a player" in {
            val config = gameConfig.createPlayer("any-name")
            config.players(0).name should be("any-name")
            config.players(0).hand.cards.size should be(2)
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
            config.players(0).name should be("any-name-changed")
        }

        "update player at index" in {
            val newPlayer = Player("updated-name", Hand(Vector[Card]()))
            var config = gameConfig.createPlayer("any-name-1")
            config = config.createPlayer("any-name-2")
            config = config.updatePlayerAtIndex(newPlayer, 0, deck)
            config.players(0).name should be("updated-name")
            config.players(0).hand.cards.size should be(0)
            config.players(1).name should be("any-name-2")
            config.players(1).hand.cards.size should be(2)
        }

        "increment active player index" in {
            val currentIndex = gameConfig.activePlayerIndex
            val config = gameConfig.incrementActivePlayerIndex()
            (config.activePlayerIndex - currentIndex) should be(1)
        }

        "reset active player index" in {
            val config = gameConfig.resetActivePlayerIndex()
            config.activePlayerIndex should be(0)
        }

        "get active player name" in {
            val config = gameConfig.createPlayer("any-name")
            config.getActivePlayerName should be("any-name")
        }

        "get active player" in {
            val config = gameConfig.createPlayer("any-name")
            config.getActivePlayer should be(config.players(0))
        }

        "init dealer" in {
            val config = gameConfig.initDealer()
            config.dealer.name should be("any-dealer-name")
            config.dealer.hand.cards.size should be(2)
        }

        "get players and dealer as string" in {
            var config = gameConfig.createPlayer("any-name-1")
            config = config.createPlayer("any-name-2")
            config = config.initDealer()
            val string = config.getAllPlayerAndDealerHandsAsString
            val builder = new StringBuilder()
            string should be(builder.append(config.players(0)).append(config.players(1)).append(config.dealer).toString())
        }

        "reset itself" in {
            var config = gameConfig.createPlayer("any-name")
            config = config.resetGameConfig()
            config.players.size should be(1)
            config.players(0).name should be("any-name")
            config.dealer.name should be("Dealer")
            config.dealer.hand.cards.size should be(2)
            config.deck.cards.size should be(48)
            config.activePlayerIndex should be(0)
            config.winners.size should be(0)
        }

        "add winner" in {
            val playerOne = Player("any-name-1", Hand(Vector[Card]()))
            val playerTwo = Player("any-name-2", Hand(Vector[Card]()))
            var config = gameConfig.addWinner(playerOne)
            config = config.addWinner(playerTwo)
            config.winners.size should be(2)
        }

        "set winner" in {
            val playerOne = Player("any-name-1", Hand(Vector[Card]()))
            val playerTwo = Player("any-name-2", Hand(Vector[Card]()))
            var config = gameConfig.addWinner(playerOne)
            config = config.setWinner(playerTwo)
            config.winners.size should be(1)
            config.winners(0) should be(playerTwo)
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

*/
