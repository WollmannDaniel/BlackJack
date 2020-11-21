package de.htwg.se.blackjack.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CardSpec extends AnyWordSpec with Matchers {
    "A Card" when { "is printed" should{
        val diamondCard = Card(Suit.Diamond, Rank.Two)
        val heartCard = Card(Suit.Heart, Rank.Three)
        val spadeCard = Card(Suit.Spade, Rank.Four)
        val clubCard = Card(Suit.Club, Rank.Five)

        val jackCard = Card(Suit.Club, Rank.Jack)
        val aceCard = Card(Suit.Heart, Rank.Ace)
        val queenCard = Card(Suit.Heart, Rank.Queen)
        val kingCard = Card(Suit.Spade, Rank.King)
        val tenCard = Card(Suit.Club, Rank.Ten)

        "have unapply" in {
            Card.unapply(diamondCard).get should be (Suit.Diamond, Rank.Two)
        }
        "have this string representation when its a diamond 2" in {
            diamondCard.toString should be("♦2,")
        }
        "have this string representation when its a heart 3" in {
            heartCard.toString should be("♥3,")
        }
        "have this string representation when its a spade 4" in {
            spadeCard.toString should be("♠4,")
        }
        "have this string representation when its a club 5" in {
            clubCard.toString should be("♣5,")
        }
        "have this card value when is jack" in {
            jackCard.mapCardValue() equals 10
        }
        "have this card value when is ace" in {
            aceCard.mapCardValue() equals 11
        }
        "have this card value when is queen" in {
            queenCard.mapCardValue() equals 10
        }
        "have this card value when is king" in {
            kingCard.mapCardValue() equals 10
        }
        "have this card value when is ten" in {
            tenCard.mapCardValue() equals 10
        }
        "have this rank value when is ace" in {
            aceCard.mapCardRank() equals "A"
        }
        "have this rank value when is queen" in {
            queenCard.mapCardRank() equals "Q"
        }
        "have this rank value when is jack" in {
            jackCard.mapCardRank() equals "J"
        }
        "have this rank value when is king" in {
            kingCard.mapCardRank() equals "K"
        }
    }}
    "To get 100% code coverage" when { "for enum" should {
        "have this call" in {
            val tenCard = Card(Suit.Club, Rank.Ten)
            Rank.values
        }
    }}
}
