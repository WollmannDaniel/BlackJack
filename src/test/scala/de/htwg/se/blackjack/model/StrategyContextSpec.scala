package de.htwg.se.blackjack.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class StrategyContextSpec extends AnyWordSpec with Matchers {
    "The Strategy" when { "is called with drawDealerHand" should {
        var deck = Deck(Vector(Card(Suit.Diamond, Rank.Two),
            Card(Suit.Heart, Rank.Seven),
            Card(Suit.Heart, Rank.Jack),
            Card(Suit.Spade, Rank.Ten),
            Card(Suit.Club, Rank.Seven)))

        val handCards = Vector(Card(Suit.Diamond, Rank.Four), Card(Suit.Club, Rank.Five))
        val hand = Hand(handCards)

        "have draw cards until card value is greather or equals than 17" in {
            val (newDealerHand, newDeck) = StrategyContext.strategy(StrategyContext.drawDealerHand, deck, hand)
            newDealerHand.calculateHandValue() should be > 17
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

        "have draw cards until card value is greather or equals than 17" in {
            val (newPlayerHand, newDeck) = StrategyContext.strategy(StrategyContext.drawPlayerHand, deck, hand)
            newPlayerHand.calculateHandValue() should be(16)
        }
    }}
}
