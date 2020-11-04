package de.htwg.se.blackjack.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PersonSpec extends AnyWordSpec with Matchers {
    "A Player" when { "new" should {
        val player = Person("any-name", Array("7","J"))
        "have a name" in {
            player.name should be("any-name")
        }
        "have two cards" in {
            player.cards.length should be(2)
        }
        "have these cards" in {
            player.cards should contain allOf ("7","J")
        }
    }}
}
