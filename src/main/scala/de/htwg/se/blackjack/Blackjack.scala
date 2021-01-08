package de.htwg.se.blackjack

import com.google.inject.{Guice, Injector}
import de.htwg.se.blackjack.aview.gui.WelcomeGui
import de.htwg.se.blackjack.aview.{Tui, UserInterface}
import de.htwg.se.blackjack.controller.{IController, RefreshData}
import de.htwg.se.blackjack.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.blackjack.model.deckComponent.ICard
import de.htwg.se.blackjack.model.deckComponent.deckBaseImpl.Deck
import de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl.GameConfig
import de.htwg.se.blackjack.model.playerComponent.IPlayer
import de.htwg.se.blackjack.model.playerComponent.playerComponentBaseImpl.{Hand, Player}

import scala.io.StdIn.readLine

object Blackjack {
    def main(args: Array[String]): Unit = {
        //val deck = new Deck()
        //var gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())


        val injector: Injector = Guice.createInjector(new BlackjackModule())
        val controller: IController = injector.getInstance(classOf[IController])

        var input: String = ""

        val gui = new WelcomeGui(controller)
        val tui = new Tui(controller)
        controller.publish(new RefreshData)

        do {
            input = readLine().toLowerCase()
            tui.processCommands(input)
        } while (input != "q")
    }
}
