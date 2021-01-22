package de.htwg.se.blackjack.model.fileIoComponent

import de.htwg.se.blackjack.controller.IController

/**
 * Interface of FileIO that has the following methods.
 */
trait IFileIO {
    /**
     * load saved game from file
     * @return loaded controller
     */
    def load: IController

    /**
     * save game to file
     * @param controller controller to save
     */
    def save(controller: IController): Unit
}
