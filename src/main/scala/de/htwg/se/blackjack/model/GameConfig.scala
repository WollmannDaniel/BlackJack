package de.htwg.se.blackjack.model

case class GameConfig(players: Vector[Player], dealer: Player, deck: Deck, activePlayerIndex: Int = 0, winners: Vector[Player] = Vector[Player]()) {

    def createPlayer(playerName: String = ""): GameConfig = {
        val (newDeck, newHand) = deck.drawCards(2)
        val newPlayer = Player(playerName, Hand(newHand))
        copy(players :+ newPlayer, dealer, newDeck)
    }

    def setPlayerName(playerName: String, playerIndex: Int): GameConfig = {
        val newPlayer = Player(playerName, players(playerIndex).hand)
        updatePlayerAtIndex(newPlayer, playerIndex, deck)
    }

    def updatePlayerAtIndex(newPlayer: Player, index: Int, newDeck: Deck): GameConfig = {
        var newPlayers = Vector[Player]()
        for (i <- 0 until players.size) {
            if (i == index) {
                newPlayers = newPlayers :+ newPlayer
            } else {
                newPlayers = newPlayers :+ players(i)
            }
        }
        GameConfig(newPlayers, dealer, newDeck, activePlayerIndex)
    }

    def incrementActivePlayerIndex(): GameConfig = {
        copy(players, dealer, deck, activePlayerIndex + 1)
    }

    def resetActivePlayerIndex(): GameConfig = {
        copy(players, dealer, deck, 0)
    }

    def getActivePlayerName = players(activePlayerIndex).name

    def getActivePlayer = players(activePlayerIndex)

    def initDealer(): GameConfig = {
        val (newDeck, newHand) = deck.drawCards(2)
        val newDealer = Player(dealer.name, Hand(newHand))
        copy(players, newDealer, newDeck)
    }

    def getAllPlayerAndDealerHandsAsString: String ={
        val builder = new StringBuilder()
        for (i <- 0 until players.size) {
            builder.append(players(i).toString)
        }
        builder.append(dealer.toString)
        builder.toString
    }

    def resetGameConfig(): GameConfig = {
        var resetedGameConfig = GameConfig(Vector[Player](), Player("Dealer", Hand(Vector[Card]())), deck.resetDeck(), 0, Vector[Player]())

        resetedGameConfig = resetedGameConfig.initDealer()
        for (i <- 0 until players.size) {
            resetedGameConfig = resetedGameConfig.createPlayer(players(i).name)
        }
        resetedGameConfig
    }

    def addWinner(winner: Player): GameConfig = {
        val newWinners = winners :+ winner
        copy(players, dealer, deck, 0, newWinners)
    }

    def setWinner(winner: Player): GameConfig = {
        copy(players, dealer, deck, 0, Vector[Player](winner))
    }

    def getAllWinnerAsString: String = {
        val builder = new StringBuilder("")
        if (winners.size > 1) {
            for (i <- 0 until winners.size) {
                if (i == (winners.size - 1)) {
                    builder.append(winners(i).name).append(" ")
                } else {
                    builder.append(winners(i).name).append(", ")
                }
            }
            builder.append("have won!")
        } else if (winners.size == 1) {
            builder.append(winners(0).name).append(" has won!")
        }
        builder.toString()
    }
}
