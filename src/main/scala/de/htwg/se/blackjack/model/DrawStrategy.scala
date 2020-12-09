package de.htwg.se.blackjack.model

object DrawStrategy {
    def drawDealerHand(gameConfig: GameConfig): GameConfig = {
        var newHand = gameConfig.dealer.hand
        var newDeck = gameConfig.deck
        while(newHand.calculateHandValue() < 17) {
            val (tempHand, tempDeck) = newHand.drawCard(newDeck)
            newHand = tempHand
            newDeck = tempDeck
        }
        val newDealer = Player(gameConfig.dealer.name, newHand)
        GameConfig(gameConfig.players, newDealer, newDeck)
    }

    def drawPlayerHand(gameConfig: GameConfig): GameConfig = {
        val (newDeck, newCard) = gameConfig.deck.drawCards(1)

        newCard(0) match {
            case Some(card) => {
                val newHand = gameConfig.getActivePlayer.hand.addCard(card)
                val newPlayer = Player(gameConfig.getActivePlayerName, newHand)
                gameConfig.updatePlayerAtIndex(newPlayer, gameConfig.activePlayerIndex, newDeck)
            }
            case None => throw new NullPointerException("Deck doesn't have enough cards.")
        }
    }

    def strategy(callback:(GameConfig) => (GameConfig), gameConfig: GameConfig) = callback(gameConfig)
}
