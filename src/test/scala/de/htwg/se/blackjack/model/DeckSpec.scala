
package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.model.deckComponent._
import de.htwg.se.blackjack.model.deckComponent.deckBaseImpl.{Card, Deck}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DeckSpec extends AnyWordSpec with Matchers {
    "The Deck" when { "new" should {
        val deck = new Deck()
        "be empty" in {
            deck.cards.size should be(0)
        }
        "have unapply" in {
            var deck = new Deck()
            deck = Deck(deck.initDeck())
            Deck.unapply(deck).get should be (deck.cards)
        }
    }}

    "The Deck" when { "initialized" should {
        var deck = new Deck()
        deck = Deck(deck.initDeck())
        "have 52 cards" in {
            deck.cards.size should be(52)
        }
    }}

    "The Deck" when { "a card is drawn" should {
        var deck = Deck(Vector(Card(Suit.Diamond, Rank.Two),
                               Card(Suit.Heart, Rank.Ace),
                               Card(Suit.Heart, Rank.Jack),
                               Card(Suit.Spade, Rank.Ten),
                               Card(Suit.Club, Rank.Seven)))

        val (newDeck, cards) = deck.drawCards(1)
        deck = newDeck.asInstanceOf[Deck]
        "have draw card" in {
            deck.cards.size should be(4)
            cards(0) should be(Some(Card(Suit.Club, Rank.Seven)))
        }
    }}

    "The Deck" when { "multiple cards are drawn" should {
        var deck = Deck(Vector(Card(Suit.Diamond, Rank.Two),
            Card(Suit.Heart, Rank.Ace),
            Card(Suit.Heart, Rank.Jack),
            Card(Suit.Spade, Rank.Ten),
            Card(Suit.Club, Rank.Seven)))

        val (newDeck, cards) = deck.drawCards(4)
        deck = newDeck.asInstanceOf[Deck]
        "have drawn multiple cards" in {
            deck.cards.size should be(1)
            cards.size should be(4)
        }

        "have drawn too many cards" in {
            val (updatedDeck, cards2) = deck.drawCards(2)
            cards2(0) should be(Some(Card(Suit.Diamond, Rank.Two)))
            cards2(1) should be(None)
            updatedDeck.asInstanceOf[Deck].cards.size should be(0)
        }
    }}

    "The Deck" when { "is reseted" should {
        var deck = new Deck(Vector(Card(Suit.Diamond, Rank.Two), Card(Suit.Club, Rank.Jack)))
        "has only 2 cards before reset" in {
            deck.cards.size should be(2)
        }
        "have 52 cards" in {
            deck = deck.resetDeck().asInstanceOf[Deck]
            deck.cards.size should be (52)
        }
    }}
}
