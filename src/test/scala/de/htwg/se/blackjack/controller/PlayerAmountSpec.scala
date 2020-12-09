package de.htwg.se.blackjack.controller

import de.htwg.se.blackjack.model.{Card, Deck, GameConfig, Hand, Player, Rank, Suit}
import de.htwg.se.blackjack.util.UndoManager
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayerAmountSpec extends AnyWordSpec with Matchers {
    val undoManager = new UndoManager
    var deck = new Deck()
    deck = Deck(deck.initDeck())
    val controller = new Controller(deck)

    val templateGameConfig = GameConfig(Vector[Player](), Player("Dealer", Hand(Vector[Card]())), deck.resetDeck(), 0, Vector[Player]())
    "doStep" in {
        controller.gameConfig = templateGameConfig
        undoManager.doStep(new PlayerAmountCommand(controller, 3))
        controller.gameConfig.players.size should be(3)
    }
    "undoStep" in {
        controller.gameConfig = templateGameConfig
        undoManager.doStep(new PlayerAmountCommand(controller, 3))
        undoManager.undoStep
        controller.gameConfig.players.size should be(0)
    }
    "redoStep" in {
        controller.gameConfig = templateGameConfig
        undoManager.doStep(new PlayerAmountCommand(controller, 3))
        undoManager.undoStep
        undoManager.redoStep
        controller.gameConfig.players.size should be(3)
    }
}
