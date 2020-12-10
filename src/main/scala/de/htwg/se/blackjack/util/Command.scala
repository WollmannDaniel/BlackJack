package de.htwg.se.blackjack.util

trait Command {
    def doStep:Unit
    def undoStep:Unit
    def redoStep:Unit
}
