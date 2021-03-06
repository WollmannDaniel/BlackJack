package de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl

import de.htwg.se.blackjack.model.deckComponent.ICard
import de.htwg.se.blackjack.model.gameConfigComponent
import de.htwg.se.blackjack.model.gameConfigComponent.IGameConfig
import de.htwg.se.blackjack.model.playerComponent.playerComponentBaseImpl
import de.htwg.se.blackjack.model.playerComponent.playerComponentBaseImpl.{Hand, Player}

import scala.util.{Failure, Success, Try}

object DrawStrategy {
    def drawDealerHand(gameConfig: IGameConfig): Try[IGameConfig] = {
        var newHand = gameConfig.getDealer().getHand()
        var newDeck = gameConfig.getDeck()
        while (newHand.calculateHandValue() < 17) {
            val tmp = newHand.drawCard(newDeck)
            tmp match {
                case Success(value) => {
                    newHand = value._1
                    newDeck = value._2
                }
                case Failure(exception) => {
                    return Failure(exception)
                }
            }
        }
        val newDealer = Player(gameConfig.getDealer().getName(), newHand)
        Success(GameConfig(gameConfig.getPlayers(), newDealer, newDeck))
    }

    def drawPlayerHand(gameConfig: IGameConfig): Try[IGameConfig] = {
        val (newDeck, newCard) = gameConfig.getDeck().drawCards(1)

        newCard(0) match {
            case Some(card) => {
                val newHand = gameConfig.getActivePlayer.getHand().addCard(card)
                val newPlayer = playerComponentBaseImpl.Player(gameConfig.getActivePlayerName, newHand)
                Success(gameConfig.updatePlayerAtIndex(newPlayer, gameConfig.getActivePlayerIndex(), newDeck))
            }
            case None => Failure(new Throwable("Deck doesn't have enough cards."))
        }
    }

    def strategy(callback: (IGameConfig) => (Try[IGameConfig]), gameConfig: IGameConfig) = callback(gameConfig)
}
