package de.htwg.se.blackjack.model.fileIoComponent.fileIoJsonImpl

import de.htwg.se.blackjack.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.blackjack.model.deckComponent.deckBaseImpl.Card
import de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl.GameConfig
import de.htwg.se.blackjack.model.playerComponent.IPlayer
import de.htwg.se.blackjack.model.playerComponent.playerComponentBaseImpl.{Hand, Player}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class JsonSpec extends AnyWordSpec with Matchers {
    "A Json FileIO" when {
        "loading" should {
            val playerOne = Player("any-name-1", Hand(Vector[Card]()))
            val playerTwo = Player("any-name-2", Hand(Vector[Card]()))
            val controller = new Controller(GameConfig(Vector[IPlayer](playerOne, playerTwo)))
            val jsonIO = new Json()

            "get the correct game config and game state" in {
                jsonIO.save(controller)
                val loadedController = jsonIO.load

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