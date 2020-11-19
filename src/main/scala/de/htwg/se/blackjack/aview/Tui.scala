package de.htwg.se.blackjack.aview

import de.htwg.se.blackjack.controller.Controller
import de.htwg.se.blackjack.util.Observer
import de.htwg.se.blackjack.controller.GameState._

class Tui(controller: Controller) extends Observer {
    controller.add(this)

    def processInputLine(input: String): Unit = {
        controller.gameState match {
            case PlayersTurn | FirstRound => {
                input match {
                    case "s" => controller.playerStands()
                    case "h" => controller.playerHits()
                    case _ => print("unknown command")
                }
            }
            case Idle => {
                input match {
                    case "n" => controller.newGame()
                    case _ =>
                }
            }
        }
    }

    override def update: Unit = {
        println(controller.gameStateToString)
    }
}
