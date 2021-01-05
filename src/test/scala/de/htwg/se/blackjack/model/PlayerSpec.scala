package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.model.deckComponent.deckBaseImpl.{Card, Deck}
import de.htwg.se.blackjack.model.playerComponent.playerComponentBaseImpl.{Hand, Player}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.blackjack.model.deckComponent._

class PlayerSpec extends AnyWordSpec with Matchers {
    "The Player" when { "new" should {
        val playerHand = Vector(Card(Suit.Diamond, Rank.Two), Card(Suit.Heart, Rank.Ace))
        var player = Player("playername", Hand(playerHand))

        "have the playerhand and name" in {
            player.name should be("playername")
            player.hand.getCards() should be(playerHand)
        }
        "have name = newPlayerName after setName" in {
            player = player.setName("newPlayerName").asInstanceOf[Player]
            player.name should be("newPlayerName")
        }
        "have unapply" in {
            Player.unapply(player).get should be (player.name, player.hand)
        }
    }}

    "The Player" when { "with two cards" should {
        val playerHand = Vector(Card(Suit.Diamond, Rank.Two), Card(Suit.Heart, Rank.Ace))
        val player = Player("playername", Hand(playerHand))

        "have these string representation" in {
            player.toString should be("playername [♦2,♥A] = 13\n")
        }

        "have this name" in {
            player.getName() should be("playername")
        }

        "have these cards" in {
            player.getHand().getCards() should be(playerHand)
        }
    }}

    "The Dealer" when { "with three cards" should {
        val dealerHand = Vector(Card(Suit.Diamond, Rank.Eight), Card(Suit.Heart, Rank.Jack))
        val dealer = Player("Dealer", Hand(dealerHand))

        "have these string representation" in {
            dealer.toStringDealer should be("Dealer [♦8, ?]\n")
        }
    }}
}
