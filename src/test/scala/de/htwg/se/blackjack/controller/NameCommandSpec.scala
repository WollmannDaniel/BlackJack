/*
package de.htwg.se.blackjack.controller

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.blackjack.util.UndoManager
import de.htwg.se.blackjack.controller._
import de.htwg.se.blackjack.controller.controllerComponent.controllerBaseImpl.{Controller, NameCommand}
import de.htwg.se.blackjack.model.deckComponent.Deck
import de.htwg.se.blackjack.model.deckComponent.deckBaseImpl.{Card, Deck, Rank, Suit}
import de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl
import de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl.GameConfig
import de.htwg.se.blackjack.model.Player
import de.htwg.se.blackjack.model.playerComponent.playerComponentBaseImpl.{Hand, Player}

class NameCommandSpec extends AnyWordSpec with Matchers {
    "SetCommand" should {
        val undoManager = new UndoManager
        var deck = new Deck()
        deck = Deck(deck.initDeck())
        val controller = new Controller(deck)
        val playerHandCards = Vector(Card(Suit.Diamond, Rank.Jack), Card(Suit.Club, Rank.Nine))
        val templateGameConfig = gameConfigBaseImpl.GameConfig(Vector[Player](Player("", Hand(playerHandCards))), Player("Dealer", Hand(Vector[Card]())), deck.resetDeck(), 0, Vector[Player]())
        "doStep" in {
            controller.gameConfig = templateGameConfig
            undoManager.doStep(new NameCommand(controller, "playerName"))
            controller.gameConfig.players(0).name should be("playerName")
        }
        "undoStep" in {
            controller.gameConfig = templateGameConfig
            undoManager.doStep(new NameCommand(controller, "playerName"))
            undoManager.undoStep
            controller.gameConfig.players(0).name should be("")
        }
        "redoStep" in {
            controller.gameConfig = templateGameConfig
            undoManager.doStep(new NameCommand(controller, "playerName"))
            undoManager.undoStep
            undoManager.redoStep
            controller.gameConfig.players(0).name should be("playerName")
        }
    }
}
*/
