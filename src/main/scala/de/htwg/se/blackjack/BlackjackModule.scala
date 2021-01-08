package de.htwg.se.blackjack

import com.google.inject.AbstractModule
import de.htwg.se.blackjack.controller.IController
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.blackjack.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.blackjack.model.deckComponent._
import de.htwg.se.blackjack.model.deckComponent.deckBaseImpl.Deck
import de.htwg.se.blackjack.model.gameConfigComponent._
import de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl.GameConfig
import de.htwg.se.blackjack.model.playerComponent._
import de.htwg.se.blackjack.model.playerComponent.playerComponentBaseImpl.{Hand, Player}

class BlackjackModule extends AbstractModule with ScalaModule {
    override def configure(): Unit = {
        bind[IController].to[Controller]
        bind[IDeck].to[deckBaseImpl.Deck]
        bind[ICard].to[deckBaseImpl.Card]
        bind[IHand].to[playerComponentBaseImpl.Hand]
        bind[IPlayer].to[playerComponentBaseImpl.Player]

        //val deck = new Deck()
        //val gameConfig = GameConfig(Vector[IPlayer](), Player("Dealer", Hand(Vector[ICard]())), deck.resetDeck(), 0, Vector[IPlayer]())
        bind[IGameConfig].toInstance(GameConfig())
    }
}
