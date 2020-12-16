package de.htwg.se.blackjack.aview.gui

import de.htwg.se.blackjack.controller.{Controller}

import scala.swing._
import scala.swing.event.ButtonClicked

class WelcomeGui(controller: Controller) extends Frame{
    listenTo(controller)

    visible = true
    peer.setLocationRelativeTo(null)
    peer.setPreferredSize(new Dimension(500, 500))
    peer.setResizable(false)

    title = "Blackjack"

    val lbl_title = new Label {
        text = "BLACKJACK"
    }

    val lbl_Players = new Label {
        text += "Starting new game!"
        text += "The deck was shuffled."
        text += "How many players want to play?"
   }

    val btn_Player1 = new Button {
        text = "1 Player"
    }
    val btn_Player2 = new Button {
        text = "2 Player"
    }
    val btn_Player3 = new Button {
        text = "3 Player"
    }
    val btn_Player4 = new Button {
        text = "4 Player"
    }
    val flowPanel = new FlowPanel {
        contents += btn_Player1
        contents += btn_Player2
        contents += btn_Player3
        contents += btn_Player4
        listenTo(btn_Player1, btn_Player2, btn_Player3, btn_Player4)

        reactions += {
            case ButtonClicked(component) => {
                if(component == btn_Player1){
                    controller.performInitGame(1)
                }else if(component == btn_Player2){
                    controller.performInitGame(2)
                }else if(component == btn_Player3){
                    controller.performInitGame(3)
                }else if(component == btn_Player4){
                    controller.performInitGame(4)
                }
                new SetupGui(controller).visible = true
                peer.setVisible(false)
            }
        }
    }

    val grid = new GridPanel(2,1){
        contents += lbl_Players
        contents += flowPanel
    }

    contents = new GridPanel(2, 1) {
        contents += lbl_title
        contents += grid
    }
}
