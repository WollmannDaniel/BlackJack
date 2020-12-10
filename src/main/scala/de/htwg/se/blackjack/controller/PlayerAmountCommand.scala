package de.htwg.se.blackjack.controller

import de.htwg.se.blackjack.util.Command

class PlayerAmountCommand(controller: Controller, playerAmount: Int) extends Command {
    val gameConfig = controller.gameConfig
    val gameState = controller.gameState

    override def doStep:Unit = controller.initGame(playerAmount)

    override def undoStep:Unit = {
        controller.gameConfig = gameConfig
        controller.gameState = gameState
    }

    override def redoStep:Unit = controller.initGame(playerAmount)
}
