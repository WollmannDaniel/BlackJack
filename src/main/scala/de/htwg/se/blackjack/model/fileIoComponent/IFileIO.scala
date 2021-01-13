package de.htwg.se.blackjack.model.fileIoComponent

import de.htwg.se.blackjack.controller.IController

trait IFileIO {
    def load: IController
    def save(controller: IController): Unit
}
