
package de.htwg.se.blackjack.controller

import de.htwg.se.blackjack.model.deckComponent._
import de.htwg.se.blackjack.controller.controllerComponent.controllerBaseImpl.{Controller, PlayerAmountCommand}
import de.htwg.se.blackjack.model.deckComponent.deckBaseImpl.{Card, Deck}
import de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl
import de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl.GameConfig
import de.htwg.se.blackjack.model.playerComponent.playerComponentBaseImpl.{Hand, Player}
import de.htwg.se.blackjack.util.UndoManager
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayerAmountSpec extends AnyWordSpec with Matchers {
    val undoManager = new UndoManager
    var deck = new Deck()
    deck = Deck(deck.initDeck())
    val templateGameConfig = gameConfigBaseImpl.GameConfig(Vector[Player](), Player("Dealer", Hand(Vector[Card]())), deck.resetDeck(), 0, Vector[Player]())
    val controller = new Controller(templateGameConfig)

    "doStep" in {
        controller.gameConfig = templateGameConfig
        undoManager.doStep(new PlayerAmountCommand(controller, 3))
        controller.gameConfig.getPlayers().size should be(3)
    }
    "undoStep" in {
        controller.gameConfig = templateGameConfig
        undoManager.doStep(new PlayerAmountCommand(controller, 3))
        undoManager.undoStep
        controller.gameConfig.getPlayers().size should be(0)
    }
    "redoStep" in {
        controller.gameConfig = templateGameConfig
        undoManager.doStep(new PlayerAmountCommand(controller, 3))
        undoManager.undoStep
        undoManager.redoStep
        controller.gameConfig.getPlayers().size should be(3)
    }
}

