package de.htwg.se.blackjack.aview

import de.htwg.se.blackjack.controller.Controller
import de.htwg.se.blackjack.util.Observer
import de.htwg.se.blackjack.controller.GameState._

class Tui(controller: Controller) extends Observer with UserInterface {
    controller.add(this)

    override def processCommands(input: String): Unit = {
        if(controller.gameState == WELCOME){
            initPlayers(input)
        } else if (controller.gameState == NAME_CREATION) {
            setPlayerName(input)
        } else {
            processInputLine(input)
        }

    }

    def initPlayers(input: String): Unit = {
        if (!List("1", "2", "3", "4").contains(input)) {
            println("There may only be 1,2,3 or 4 players!")
        }else{
            controller.initGame(input.toInt)
        }
    }

    def setPlayerName(playerName: String): Unit = {
        controller.setPlayerName(playerName)
    }

    def processInputLine(input: String): Unit = {
        input match {
            case "s" => controller.playerStands()
            case "h" => controller.playerHits()
            case "n" => controller.newGame()
            case "q" => controller.quitGame()
            case "state" => controller.getState()
            case _ => print("unknown command")
        }
    }

    override def update: Boolean = {
        controller.gameState match {
            case WELCOME => {
                println("Starting new game!\nThe deck was shuffled.\nHow many players want to play?")
            }
            case NAME_CREATION => {
                println(controller.getPlayerName)
            }
            case PLAYER_TURN => {
                println(s"${controller.getActivePlayerName}'s turn. Hit or stand?(h/s)\n")
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
        true
    }
}
