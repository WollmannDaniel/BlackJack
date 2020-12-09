package de.htwg.se.blackjack.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayerSpec extends AnyWordSpec with Matchers {
    "The Player" when { "new" should {
        val playerHand = Vector(Card(Suit.Diamond, Rank.Two), Card(Suit.Heart, Rank.Ace))
        var player = Player("playername", Hand(playerHand))

        "have the playerhand and name" in {
            player.name should be("playername")
            player.hand.cards should be(playerHand)
        }
        "have name = newPlayerName after setName" in {
            player = player.setName("newPlayerName")
            player.name should be("newPlayerName")
        }
        "have unapply" in {
            Player.unapply(player).get should be (player.name, player.hand)
        }
    }}

    "The Player" when { "draw a card" should {
        val playerHand = Vector(Card(Suit.Diamond, Rank.Two), Card(Suit.Heart, Rank.Ace))
        val player = Player("playername", Hand(playerHand))

        val deck = Deck(Vector(
            Card(Suit.Heart, Rank.Jack),
            Card(Suit.Spade, Rank.Ten),
            Card(Suit.Club, Rank.Seven)))

        "have three handcards" in {
            val (newPlayer, newDeck) = player.drawCard(deck)
            newPlayer.hand.cards.size should be(3)
        }
    }}

    "The Player" when { "with two cards" should {
        val playerHand = Vector(Card(Suit.Diamond, Rank.Two), Card(Suit.Heart, Rank.Ace))
        val player = Player("playername", Hand(playerHand))

        "have these string representation" in {
            player.toString should be("playername [♦2,♥A] = 13\n")
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
