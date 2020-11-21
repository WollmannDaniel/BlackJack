package de.htwg.se.blackjack.aview

import de.htwg.se.blackjack.controller.Controller
import de.htwg.se.blackjack.util.Observer
import de.htwg.se.blackjack.controller.GameState._

class Tui(controller: Controller) extends Observer {
    controller.add(this)

    def processInputLine(input: String): Unit = {
        input match {
            case "s" => controller.playerStands()
            case "h" => controller.playerHits()
            case "n" => controller.newGame()
            case "q" => controller.quitGame()
            case _ => print("unknown command")
        }
    }

    override def update: Unit = {
        controller.gameState match {
            case FirstRound => {
                println("Starting new game!\nThe deck was shuffled.\nIt's your turn. Hit or stand?(h/s)\n")
                println(controller.gameStateToString)
            }
            case PlayersTurn => {
                println("It's your turn. Hit or stand?(h/s)")
                println(controller.gameStateToString)
            }
            case DealersTurn => println(controller.gameStateToString)
            case Idle => println("q = quit, n = start new game")
            case PlayerWon => {
                println("The Player has won!")
                println(controller.gameStateToString)
            }
            case PlayerLost => {
                println("The Dealer has won!")
                println(controller.gameStateToString)
            }
            case Draw => {
                println("It's a draw!")
                println(controller.gameStateToString)
            }
            case BlackJack => {
                println("Lucky boy! The Player has won")
                println(controller.gameStateToString)
            }
            case WrongCmd => println("Command not allowed!")
            case EndGame => print("Good bye!")
        }
    }
}
