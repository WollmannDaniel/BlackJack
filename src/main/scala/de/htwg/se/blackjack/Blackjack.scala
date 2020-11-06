package de.htwg.se.blackjack

import de.htwg.se.blackjack.model.{Card, Deck, Person}
import de.htwg.se.blackjack.aview.{Tui}
import scala.io.StdIn.readLine

object Blackjack {
    val cardValues: Map[String, Int] = Map("Ace" -> 11, "Two" -> 2, "Three" -> 3, "Four" -> 4, "Five" -> 5, "Six" -> 6,
        "Seven" -> 7, "Eight" -> 8, "Nine" -> 9, "Ten" -> 10, "Jack" -> 10, "Queen" -> 10, "King" -> 10)

    def main(args:Array[String]): Unit = {
        val board = createGameBoard()
        println(board)

        val playerName = readLine()

        println(s"Hello $playerName, lets start!")

        val tui = new Tui
        var player1 = Person(playerName, Vector[Card]())
        var dealer = Person("Dealer", Vector[Card]())

        var input: String = ""

        dealer = tui.processInputLine("h", dealer)
        dealer = tui.processInputLine("h", dealer)

        player1 = tui.processInputLine("h", player1)
        player1 = tui.processInputLine("h", player1)

        println(dealer.toStringDealer)

        do {
            println(player1)
            print("It is your turn! hit or stand? (h/s): ")
            input = readLine()
            player1 = tui.processInputLine(input, player1)

            if (player1.calculateHandValue() > 21){
                println(s"${dealer.name} has won!")
                println(player1)
                input = "exit"
            } else if(player1.calculateHandValue() == 21){
                println(s"${player1.name} has won! You have a Blackjack!!")
                println(player1)
                input = "exit"
            }

            if (input == "s") {
                while(dealer.calculateHandValue() < 17){
                    dealer = tui.processInputLine("h", dealer)
                }
                println(dealer)

                if (dealer.calculateHandValue() > 21){
                    println(s"${player1.name} has won!")
                } else if (player1.calculateHandValue() > dealer.calculateHandValue()) {
                    println(s"${dealer.name} has won!")
                } else if (player1.calculateHandValue() < dealer.calculateHandValue()) {
                    println(s"${player1.name} has won!")
                } else {
                    println("It's a draw!")
                }
                println(dealer)
                println(player1)
                input = "exit"
            }
        } while(input != "exit")
    }

    def createGameBoard(): String = {
        "Welcome to blackjack!\nWhats your name?: "
    }
}
