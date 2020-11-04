package de.htwg.se.blackjack.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PersonSpec extends AnyWordSpec with Matchers {
    "A Player" when { "new" should {
        var hand = Array("7","J")
        val player = Person("any-name", hand)
        "have unapply" in {
            Person.unapply(player).get should be ("any-name", hand)
        }
        "have a name" in {
            player.name should be("any-name")
        }
        "have two cards" in {
            player.cards.length should be(2)
        }
        "have these cards" in {
            player.cards should contain allOf ("7","J")
        }
        "have this string representation" in {
            player.toString should be("any-name's hand: [7, J]\n")
        }
    }}
}
