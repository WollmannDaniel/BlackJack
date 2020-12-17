package de.htwg.se.blackjack.aview

import de.htwg.se.blackjack.controller.{Controller, DealersTurn, RefreshData}
import de.htwg.se.blackjack.util.Observer
import de.htwg.se.blackjack.controller.GameState._

import scala.swing.Reactor

class Tui(controller: Controller) extends Reactor {
    listenTo(controller)

    def processCommands(input: String): Unit = {
        if (controller.gameState == WELCOME) {
            input match {
                case "z" => controller.undo
                case "y" => controller.redo
                case _ => initPlayers(input)
            }
        } else if (controller.gameState == NAME_CREATION) {
            input match {
                case "z" => controller.undo
                case "y" => controller.redo
                case _ => controller.performSetPlayerName(input)
            }
        } else {
            processInputLine(input)
        }
    }

    def initPlayers(input: String): Unit = {
        if (!List("1", "2", "3", "4").contains(input)) {
            println("There may only be 1,2,3 or 4 players!")
        } else {
            controller.performInitGame(input.toInt)
        }
    }

    def processInputLine(input: String): Unit = {
        input match {
            case "s" => controller.playerStands()
            case "h" => controller.playerHits()
            case "n" => controller.newGame()
            case "q" => controller.quitGame()
            case "z" => controller.undo
            //case "y" => controller.redo
            case "state" => controller.getState()
            case _ => print("unknown command")
        }
    }

    reactions += {
        case _ => update
    }

    def update: Unit = {
        controller.gameState match {
            case WELCOME => {
                print("Starting new game!\nThe deck was shuffled.\nHow many players want to play?\n")
            }
            case NAME_CREATION => {
                print(controller.getPlayerName + "\n")
            }
            case NEW_GAME_STARTED => {
                print("Starting new game!\nThe deck was shuffled.\n")
            }
            case PLAYER_TURN => {
                println(s"${controller.getActivePlayerName}'s turn. Hit or stand?(h/s)\n")
                println(controller.gameStateToString)
            }
            case PLAYER_LOST => {
                println(s"${controller.getActivePlayerName}'s hand value went over twenty-one!\n")
                println(controller.gameStateToString)
            }
            case DEALERS_TURN => println(controller.gameStateToString)
            case IDLE => println("q = quit, n = start new game")
            case DEALER_WON | PLAYER_WON => {
                println(controller.gameStateToString)
            }
            case DRAW => {
                print("It's a draw!\n")
                println(controller.gameStateToString)
            }
            case WRONG_CMD => print("Command not allowed!\n")
            case END_GAME => print("Good bye!")
            case EMPTY_DECK => {
                throw new IllegalStateException("Deck doesn't have enough cards.")
            }
        }
    }
}
