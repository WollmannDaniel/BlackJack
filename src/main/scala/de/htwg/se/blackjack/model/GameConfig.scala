package de.htwg.se.blackjack.model

case class GameConfig(players: Vector[Player], deck: Deck, activePlayerIndex: Int = 0) {

    def createPlayer(): GameConfig = {
        val newPlayer = Player("", Hand(Vector[Card]()))
        val (newPlayerWithHand, newDeck) = newPlayer.drawCard(deck)
        copy(players :+ newPlayerWithHand, newDeck)
    }

    def setPlayerName(playerName: String, playerIndex: Int): GameConfig = {
        val newPlayers = Vector[Player]()
        for (i <- 0 until players.size) {
            if (i == playerIndex) {
                val newPlayer = players(playerIndex).setName(playerName)
                newPlayers :+ newPlayer
            } else {
                newPlayers :+ players(playerIndex)
            }
        }
        GameConfig(newPlayers, deck)
    }

    def incrementActivePlayerIndex(): GameConfig = {
        copy(players, deck, activePlayerIndex + 1)
    }

    def resetActivePlayerIndex(): GameConfig = {
        copy(players, deck, 0)
    }

    def getActivePlayerName() = players(activePlayerIndex).name
}
