package de.htwg.se.blackjack.model

object StrategyContext {
    def drawDealerHand(deck: Deck, hand: Hand) = {
        var newHand = hand
        var newDeck = deck
        while(newHand.calculateHandValue() < 17) {
            val (tempHand, tempDeck) = newHand.drawCard(deck)
            newHand = tempHand
            newDeck = tempDeck
        }
        (newHand, newDeck)
    }

    def drawPlayerHand(deck: Deck, hand: Hand) = {
        hand.drawCard(deck)
    }

    def strategy(callback:(Deck, Hand) => (Hand, Deck), deck: Deck, hand: Hand) = callback(deck, hand)
}
