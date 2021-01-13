package de.htwg.se.blackjack.controller.controllerComponent.controllerBaseImpl

trait State {
    def handle(e: State): (State, String)
}
case class IsRunning() extends State {
    override def handle(e: State): (State, String) = {
        e match {
            case isRunning: IsRunning => {
                (IsRunning(), "Game is running!")
            }
            case isNotRunning: IsNotRunning => {
                (IsNotRunning(), "No game is running!")
            }
        }
    }
}
case class IsNotRunning() extends State {
    override def handle(e: State): (State, String) = {
        e match {
            case isRunning: IsRunning => {
                (IsRunning(), "Game is running!")
            }
            case isNotRunning: IsNotRunning => {
                (IsNotRunning(), "No game is running!")
            }
        }
    }
}



