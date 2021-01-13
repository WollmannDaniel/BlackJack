package de.htwg.se.blackjack.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.blackjack.util.Command

class NameCommand(controller: Controller, playerName: String) extends Command {
    val gameConfig = controller.gameConfig
    val gameState = controller.gameState

    override def doStep:Unit = controller.setPlayerName(playerName)

    override def undoStep:Unit = {
        controller.gameConfig = gameConfig
        controller.gameState = gameState
    }

    override def redoStep:Unit = controller.setPlayerName(playerName)
}
