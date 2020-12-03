package de.htwg.se.blackjack.controller

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.blackjack.model.{Card, Hand, Rank, Suit}

class StateContextSpec extends AnyWordSpec with Matchers {
    "State " when { "a player is turn or first round" should {
        val playerHand = Hand(Vector(Card(Suit.Diamond, Rank.Two), Card(Suit.Club, Rank.Jack)))
        val dealerHand = Hand(Vector(Card(Suit.Heart, Rank.Eight), Card(Suit.Spade, Rank.Ace)))
        val gameState = GameState.FirstRound
        "hide dealer's hand" in {
            StateContext.handle(gameState, playerHand, dealerHand) should equal("Player hand: " + playerHand.toString + "Dealer hand: " + dealerHand.toStringDealer)
        }
    }}

    "State " when { "is not a player is turn or first round" should {
        val playerHand = Hand(Vector(Card(Suit.Diamond, Rank.Two), Card(Suit.Club, Rank.Jack)))
        val dealerHand = Hand(Vector(Card(Suit.Heart, Rank.Eight), Card(Suit.Spade, Rank.Ace)))
        val gameState = GameState.DealersTurn
        "show full dealer's hand" in {
            StateContext.handle(gameState, playerHand, dealerHand) should equal("Player hand: " + playerHand.toString + "Dealer hand: " + dealerHand.toString)
        }
    }}
}
