package de.htwg.se.blackjack.util

/**
 * Interface of Command that has the following methods.
 */
trait Command {
    /**
     * do step
     */
    def doStep:Unit

    /**
     * undo step
     */
    def undoStep:Unit

    /**
     * redo step
     */
    def redoStep:Unit
}
