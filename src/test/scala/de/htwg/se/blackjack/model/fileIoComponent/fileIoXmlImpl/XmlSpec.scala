package de.htwg.se.blackjack.model.fileIoComponent.fileIoXmlImpl

import de.htwg.se.blackjack.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.blackjack.model.deckComponent.{Rank, Suit}
import de.htwg.se.blackjack.model.deckComponent.deckBaseImpl.Card
import de.htwg.se.blackjack.model.fileIoComponent.fileIoXmlImpl.Xml
import de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl.GameConfig
import de.htwg.se.blackjack.model.playerComponent.IPlayer
import de.htwg.se.blackjack.model.playerComponent.playerComponentBaseImpl.{Hand, Player}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class XmlSpec extends AnyWordSpec with Matchers {
    "A Xml FileIO" when {
        "loading" should {
            val playerOne = Player("any-name-1", Hand(Vector[Card](Card(Suit.Spade, Rank.Seven), Card(Suit.Diamond, Rank.Ten))))
            val playerTwo = Player("any-name-2", Hand(Vector[Card]()))
            val controller = new Controller(GameConfig(Vector[IPlayer](playerOne, playerTwo), winners = Vector[IPlayer](playerOne, playerTwo)))
            val xmlIO = new Xml()

            "get the correct game config and game state" in {
                xmlIO.save(controller)
                val loadedController = xmlIO.load

                loadedController.gameState should be(controller.gameState)
                loadedController.gameConfig.getPlayers() should be(controller.gameConfig.getPlayers())
                loadedController.gameConfig.getDealer() should be(controller.gameConfig.getDealer())
                loadedController.gameConfig.getDeck() should be(controller.gameConfig.getDeck())
                loadedController.gameConfig.getActivePlayerIndex() should be(controller.gameConfig.getActivePlayerIndex())
                loadedController.gameConfig.getWinners() should be(controller.gameConfig.getWinners())
            }
        }
    }
}
