package de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl

import de.htwg.se.blackjack.model.deckComponent.{ICard, IDeck}
import de.htwg.se.blackjack.model.gameConfigComponent.IGameConfig
import de.htwg.se.blackjack.model.playerComponent.IPlayer
import de.htwg.se.blackjack.model.playerComponent.playerComponentBaseImpl.{Hand, Player}

case class GameConfig(players: Vector[IPlayer], dealer: IPlayer, deck: IDeck, activePlayerIndex: Int = 0, winners: Vector[IPlayer] = Vector[IPlayer]()) extends IGameConfig {

    def createPlayer(playerName: String = ""): IGameConfig = {
        val (newDeck, newHand) = deck.drawCards(2)

        val cardList = unpackOption(newHand)

        val newPlayer = Player(playerName, Hand(cardList))
        copy(players :+ newPlayer, dealer, newDeck)
    }

    def unpackOption(cards: Vector[Option[ICard]]): Vector[ICard] = {
        var cardList = Vector[ICard]()

        for (i <- (0 to cards.size - 1)) {
            cards(i) match {
                case Some(card) => cardList = cardList :+ card
                case None => throw new NullPointerException("Deck doesn't have enough cards.")
            }
        }
        cardList
    }

    def setPlayerName(playerName: String, playerIndex: Int): IGameConfig = {
        val newPlayer = Player(playerName, players(playerIndex).getHand())
        updatePlayerAtIndex(newPlayer, playerIndex, deck)
    }

    def updatePlayerAtIndex(newPlayer: IPlayer, index: Int, newDeck: IDeck): IGameConfig = {
        var newPlayers = Vector[IPlayer]()
        for (i <- 0 until players.size) {
            if (i == index) {
                newPlayers = newPlayers :+ newPlayer
            } else {
                newPlayers = newPlayers :+ players(i)
            }
        }
        GameConfig(newPlayers, dealer, newDeck, activePlayerIndex)
    }

    def incrementActivePlayerIndex(): IGameConfig = {
        copy(players, dealer, deck, activePlayerIndex + 1)
    }

    def resetActivePlayerIndex(): IGameConfig = {
        copy(players, dealer, deck, 0)
    }

    def getActivePlayerName = players(activePlayerIndex).getName()

    def getActivePlayer = players(activePlayerIndex)

    def initDealer(): IGameConfig = {
        val (newDeck, newHand) = deck.drawCards(2)

        val cardList = unpackOption(newHand)

        val newDealer = Player(dealer.getName(), Hand(cardList))
        copy(players, newDealer, newDeck)
    }

    def getAllPlayerAndDealerHandsAsString: String = {
        val builder = new StringBuilder()
        for (i <- 0 until players.size) {
            builder.append(players(i).toString)
        }
        builder.append(dealer.toString)
        builder.toString
    }

    def resetGameConfig(): IGameConfig = {
        var resetedGameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())

        resetedGameConfig = resetedGameConfig.initDealer().asInstanceOf[GameConfig]
        for (i <- 0 until players.size) {
            resetedGameConfig = resetedGameConfig.createPlayer(players(i).getName()).asInstanceOf[GameConfig]
        }
        resetedGameConfig
    }

    def addWinner(winner: IPlayer): IGameConfig = {
        val newWinners = winners :+ winner
        copy(players, dealer, deck, 0, newWinners)
    }

    def setWinner(winner: IPlayer): GameConfig = {
        copy(players, dealer, deck, 0, Vector[IPlayer](winner))
    }

    def getAllWinnerAsString: String = {
        val builder = new StringBuilder("")
        if (winners.size > 1) {
            for (i <- 0 until winners.size) {
                if (i == (winners.size - 1)) {
                    builder.append(winners(i).getName()).append(" ")
                } else {
                    builder.append(winners(i).getName()).append(", ")
                }
            }
            builder.append("have won!")
        } else if (winners.size == 1) {
            builder.append(winners(0).getName()).append(" has won!")
        }
        builder.toString()
    }

    def getPlayerAtIndex(index: Int): IPlayer = players(index)

    def getPlayers(): Vector[IPlayer] = players

    def getDeck(): IDeck = deck

    def getActivePlayerIndex(): Int = activePlayerIndex

    def getDealer(): IPlayer = dealer
}
