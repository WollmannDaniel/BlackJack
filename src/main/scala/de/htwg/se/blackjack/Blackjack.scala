package de.htwg.se.blackjack

import com.google.inject.{Guice, Injector}
import de.htwg.se.blackjack.aview.gui.WelcomeGui
import de.htwg.se.blackjack.aview.{Tui}
import de.htwg.se.blackjack.controller.{IController, RefreshData}

import scala.io.StdIn.readLine

object Blackjack {
    def main(args: Array[String]): Unit = {
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
