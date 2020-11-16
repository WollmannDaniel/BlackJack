package de.htwg.se.blackjack

import de.htwg.se.blackjack.aview.Tui2
import de.htwg.se.blackjack.model.{Deck2, Hand}

import scala.io.StdIn.readLine

object Blackjack2 {
    val tui = new Tui2
    var playerHand = new Hand(2)
    var dealerHand = new Hand(2)
    var output: String = "Starting new hand"
    var deck = new Deck2()

    val controller =

    def main(args: Array[String]): Unit = {
        var input: String = ""

        do {
            println(output)
            input = readLine().toLowerCase()
            val (out, pH, dH, d) = tui.processInputLine(input, playerHand, dealerHand, deck)
            output = out
            playerHand = pH
            dealerHand = dH
        } while (input != "q")
    }
}
