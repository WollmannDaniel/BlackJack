package de.htwg.se.blackjack.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PersonSpec extends AnyWordSpec with Matchers {
    "A Player" when { "new" should {
        val cards = Vector(Card("Diamond", "Two"), Card("Club", "Jack"))
        val player = Person("any-name", cards)
        "have unapply" in {
            Person.unapply(player).get should be ("any-name", cards)
        }
        "have a name" in {
            player.name should be("any-name")
        }
        "have two cards" in {
            player.cards.length should be(2)
        }
        "have these cards" in {
            player.cards  should contain allOf (Card("Diamond", "Two"), Card("Club", "Jack"))
        }
    }}
    "A Player" when { "draws a card" should {
        val cards = Vector(Card("Diamond", "Two"), Card("Club", "Jack"))
        var player = Person("any-name", cards)
        player = player.addCard(Card("Heart", "Ace"))

        "have added these card" in {
            player.cards should contain allOf (Card("Diamond", "Two"), Card("Club", "Jack"), Card("Heart", "Ace"))
        }

    }}

    "A Player" when { "is printed" should {
        val cards = Vector(Card("Diamond", "Two"), Card("Club", "Jack"))
        val player = Person("any-name", cards)

        "have this string representation" in {
            player.toString should be("any-name's hand: [♦2, ♣J] = 12")
        }
    }}

    "A Player" when { "is dealer and printed" should {
        val cards = Vector(Card("Diamond", "Two"), Card("Club", "Jack"))
        val player = Person("any-name", cards)

        "have this string representation" in {
            player.toStringDealer should be("any-name's hand: [♦2, ?]")
        }
    }}

    "A Player" when { "has multiple ace's" should {
        val cards = Vector(Card("Diamond", "Ace"), Card("Club", "Ace"), Card("Heart", "Ace"))
        val player = Person("any-name", cards)

        "has the calculated hand value" in {
            player.calculateHandValue should be (13)
        }
    }}
}
