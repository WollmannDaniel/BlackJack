package de.htwg.se.blackjack

import com.google.inject.AbstractModule
import de.htwg.se.blackjack.controller.IController
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.blackjack.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.blackjack.model.gameConfigComponent._
import de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl.GameConfig
import de.htwg.se.blackjack.model.fileIoComponent._


class BlackjackModule extends AbstractModule with ScalaModule {
    override def configure(): Unit = {
        bind[IController].to[Controller]
        bind[IGameConfig].toInstance(GameConfig())
        //bind[IFileIO].to[fileIoJsonImpl.Json]
        bind[IFileIO].to[fileIoXmlImpl.Xml]
    }
}
