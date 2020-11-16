package de.htwg.se.blackjack.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DeckSpec extends AnyWordSpec with Matchers {
    /*"The Deck" when { "is created" should {
        val deck = Deck(List(Card("Diamond", "Two"), Card("Club", "Jack")))
        "have these cards" in {
            deck.cards should contain allOf (Card("Diamond", "Two"), Card("Club", "Jack"))
        }
        "have unapply" in {
            Deck.unapply(deck).get should be(List(Card("Diamond", "Two"), Card("Club", "Jack")))
        }
    }}

    "The Deck" when { "a card is drawn" should {
        val deckOld = Deck(List(Card("Diamond", "Two"), Card("Club", "Jack"), Card("Heart", "Ace")))
        val deckNew = deckOld.drawCard()
        "have these cards left" in {
            deckNew.cards should contain allOf(Card("Diamond", "Two"), Card("Club", "Jack"))
        }
        "have this next card" in {
            deckNew.getDrawedCard(deckOld) should be(Card("Heart", "Ace"))
        }
    }}*/
}
