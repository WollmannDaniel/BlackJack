package de.htwg.se.blackjack

import de.htwg.se.blackjack.model.Person

object Blackjack {
    def main(args:Array[String]): Unit = {
        var board = createGameBoard()
        println(board)
    }

    def createGameBoard(): String = {
        var board = ""
        val playerHand = Array("J", "2")
        val dealerHand = Array("7", "Q")
        val player = Person("Player", playerHand)
        val dealer = Person("Dealer", dealerHand)
        print(player.toString())
        board = board.concat("Welcome to main.de.htwg.se.blackjack!\n")
        board = board.concat(player.toString())
        board = board.concat(dealer.toString())
        board = board.concat("It is Player's turn! hit or stand? (h/s): ")
        return board
    }
}
