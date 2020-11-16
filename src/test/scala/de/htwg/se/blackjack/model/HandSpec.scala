package de.htwg.se.blackjack.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class HandSpec extends AnyWordSpec with Matchers {
  "A Hand " when { "new" should {
      val handCards = Vector(Card(Suit.Diamond, Rank.Two), Card(Suit.Club, Rank.Jack))
      val hand = Hand(handCards)
      "have unapply" in {
        Hand.unapply(hand).get should be (handCards)
      }
      "have two cards" in {
        hand.cards.length should be(2)
      }
      "have these cards" in {
        hand.cards  should contain allOf (Card(Suit.Diamond, Rank.Two), Card(Suit.Club, Rank.Jack))
      }
  }}
  /*
  "A Player" when { "has multiple ace's" should {
    val cards = Vector(Card(Suit.Diamond, Rank.Ace), Card(Suit.Club, Rank.Ace), Card(Suit.Heart, Rank.Ace))
    val hand = Hand(cards)

    "has the calculated hand value" in {
      hand.calculateHandValue should be (13)
    }
  }}*/

  "A Player" when { "has multiple ace's" should {
    val cards = Vector(Card(Suit.Diamond, Rank.Ace), Card(Suit.Club, Rank.Ace), Card(Suit.Heart, Rank.Ace))
    val hand = Hand(cards)

    "has the calculated hand value" in {
      hand.calculateHandValue should be (13)
    }
  }}

}
