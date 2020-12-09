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
            hand.cards should be(Vector(Card(Suit.Diamond, Rank.Two), Card(Suit.Club, Rank.Jack)))
        }
    }}

    "A Playerhand" when { "is printed" should {
        val handCards = Vector(Card(Suit.Diamond, Rank.Two), Card(Suit.Club, Rank.Jack))
        val hand = Hand(handCards)

        "have this string representation" in {
            hand.toString should be("[♦2,♣J] = 12\n")
        }
    }}

    "A Dealerhand" when { "is printed" should {
        val handCards = Vector(Card(Suit.Diamond, Rank.Two), Card(Suit.Club, Rank.Jack))
        val hand = Hand(handCards)

        "have this string representation" in {
            hand.toStringDealer should be("[♦2, ?]\n")
        }
    }}

    "A Hand" when { "has multiple ace's" should {
        val cards = Vector(Card(Suit.Diamond, Rank.Ace), Card(Suit.Club, Rank.Ace), Card(Suit.Heart, Rank.Ace))
        val hand = Hand(cards)

        "has the calculated hand value" in {
            hand.calculateHandValue() should be (13)
        }
    }}

    "A Hand" when { "draws a card" should {
        val deck = Deck(Vector(Card(Suit.Diamond, Rank.Jack), Card(Suit.Club, Rank.Nine), Card(Suit.Heart, Rank.Ace)))
        val (newDeckAfterDeckDraw, cards) = deck.drawCards(2)
        var handCards = Vector[Card]()

        cards(0) match {
            case Some(card) => handCards = handCards :+ card
            case None =>
        }
        cards(1) match {
            case Some(card) => handCards = handCards :+ card
            case None =>
        }

        var hand = Hand(handCards)

        val (newHand, newDeckAfterHandDraw) = hand.drawCard(deck)
        hand = newHand

        "have three cards" in {
            hand.cards.length should be(3)
        }
    }}

    "A Hand" when { "is new and initialized" should {
        val deck = Deck(Vector(Card(Suit.Diamond, Rank.Jack), Card(Suit.Club, Rank.Nine)))
        val (newDeck, cards) = deck.drawCards(2)
        var handCards = Vector[Card]()

        cards(0) match {
            case Some(card) => handCards = handCards :+ card
            case None =>
        }
        cards(1) match {
            case Some(card) => handCards = handCards :+ card
            case None =>
        }

        val hand = Hand(handCards)
        "have two cards" in {
            hand.cards.length should be(2)
        }
    }}

    "A Hand" when { "draws a card from deck which is empty" should {
        val deck = Deck(Vector())
        val handCards = Vector[Card]()
        val hand = Hand(handCards)

        "have drawn a none and exception is thrown" in {
            val thrown = intercept[Exception] {
                val (newHand, newDeckAfterHandDraw) = hand.drawCard(deck)
            }
            thrown.getMessage should be("Deck doesn't have enough cards.")
        }
    }}

    "A Hand" when { "card is added" should {
        val deck = Deck(Vector())
        val handCards = Vector[Card]()
        var hand = Hand(handCards)

        "have the new card in his hand" in {
            hand = hand.addCard(Card(Suit.Diamond, Rank.Jack))
            hand.cards.size should be(1)
        }
    }}
}