package de.htwg.se.blackjack.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DeckSpec extends AnyWordSpec with Matchers {
    "The Deck" when { "is created" should {
        "have 52 cards" in {
            Deck.cards.size should be(52)
        }
    }}

    "The Deck" when {
        "is initialized" should {
            val deckCards = Deck.initDeck()
            "have 52 cards" in {
                deckCards.size should be(52)
            }
        }
    }

    /*
    "The Deck" when {
        "a card is drawn" should {
            val card = Deck.drawCard()
            "has a card less" in {
                Deck.cards.size should be(51)
            }
        }
    }*/

    "The Deck" when {
        "is reseted" should {
            Deck.resetDeck()
            "have 52 cards" in {
                Deck.cards.size should be(52)
            }
        }
    }
}
