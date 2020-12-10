package de.htwg.se.blackjack.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{Failure, Try}

class DrawStrategySpec extends AnyWordSpec with Matchers {
    "The Strategy" when { "is called with drawDealerHand" should {
        val deck = Deck(Vector(Card(Suit.Diamond, Rank.Two),
            Card(Suit.Heart, Rank.Seven),
            Card(Suit.Heart, Rank.Jack),
            Card(Suit.Spade, Rank.Ten),
            Card(Suit.Club, Rank.Seven)))

        val handCards = Vector(Card(Suit.Diamond, Rank.Four), Card(Suit.Club, Rank.Five))
        val hand = Hand(handCards)
        val playerList = Vector[Player](Player("any-name", Hand(Vector[Card]())))
        val dealer = Player("any-name-2", hand)
        val gameConfig = GameConfig(playerList, dealer, deck: Deck, 0, Vector[Player]())

        "have draw cards until card value is greater or equals than 17" in {
            val config = DrawStrategy.strategy(DrawStrategy.drawDealerHand, gameConfig)
            config.dealer.hand.calculateHandValue() should be > 17
        }
    }}

    "The Strategy" when { "is called with drawPlayerHand" should {
        var deck = Deck(Vector(Card(Suit.Diamond, Rank.Two),
            Card(Suit.Heart, Rank.Seven),
            Card(Suit.Heart, Rank.Jack),
            Card(Suit.Spade, Rank.Ten),
            Card(Suit.Club, Rank.Seven)))

        val handCards = Vector(Card(Suit.Diamond, Rank.Four), Card(Suit.Club, Rank.Five))
        val hand = Hand(handCards)
        val playerList = Vector[Player](Player("any-name", hand))
        val dealer = Player("any-name-2", Hand(Vector[Card]()))
        val gameConfig = GameConfig(playerList, dealer, deck: Deck, 0, Vector[Player]())

        "draw and have a hand value of 16" in {
            val config = DrawStrategy.strategy(DrawStrategy.drawPlayerHand, gameConfig)
            config.players(0).hand.calculateHandValue() should be(16)
        }
    }}

    "The Strategy" when { "is called with drawPlayerHand and deck is empty" should {
        val deck = Deck(Vector[Card]())

        val handCards = Vector(Card(Suit.Diamond, Rank.Four), Card(Suit.Club, Rank.Five))
        val hand = Hand(handCards)
        val playerList = Vector[Player](Player("any-name", hand))
        val dealer = Player("any-name-2", Hand(Vector[Card]()))
        val gameConfig = GameConfig(playerList, dealer, deck: Deck, 0, Vector[Player]())

        "draw and have a hand value of 16" in {
            val thrown = intercept[Exception] {
                val config = DrawStrategy.strategy(DrawStrategy.drawPlayerHand, gameConfig)
            }
            thrown.getMessage should be("Deck doesn't have enough cards.")
        }
    }}
}
