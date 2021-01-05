package de.htwg.se.blackjack.aview.gui

import scala.swing._
import de.htwg.se.blackjack.controller.{IController, RefreshData, StartGame}

import scala.swing.event.ButtonClicked

class SetupGui(controller: IController) extends Frame {
    listenTo(controller)
    peer.setLocationRelativeTo(null)
    peer.setDefaultCloseOperation(3)
    title = "Blackjack"

    var boardGui = new BoardGui(SetupGui.this, controller)

    val btn_undo = new Button {
        text = "Undo"
    }
    val btn_redo = new Button {
        text = "Redo"
    }
    val btn_do = new Button {
        text = "Do"
    }

    val flowPanel = new FlowPanel {
        contents += btn_undo
        contents += btn_redo
        contents += btn_do
    }

    val txt_playername = new TextField {
        columns = 40
    }

    val lbl_playername = new Label {
        text = controller.getPlayerName
    }

    contents = new GridPanel(3,1) {
        btn_undo.enabled = false

        contents += lbl_playername
        contents += txt_playername
        contents += flowPanel
        listenTo(btn_undo, btn_do, btn_redo)

        reactions += {
            case ButtonClicked(component) => {
                if(component == btn_undo) {
                    controller.undo
                } else if(component == btn_redo) {
                    controller.redo
                } else if(component == btn_do) {
                    controller.performSetPlayerName(txt_playername.text)
                }
            }
        }
    }

    def checkUndo = {
        if (lbl_playername.text == "Please enter Playername 1:") {
            btn_undo.enabled = false
        } else {
            btn_undo.enabled = true
        }
    }

    reactions += {
        case event: RefreshData => {
            lbl_playername.text = controller.getPlayerName
            txt_playername.text = ""
            checkUndo
        }
        case event: StartGame => {
            boardGui.deafTo(controller)
            boardGui = new BoardGui(SetupGui.this, controller)
            boardGui.pack()
            boardGui.visible = true
            dispose()
        }
    }

    def redraw: Unit = {
        lbl_playername.text = controller.getPlayerName
        txt_playername.text = ""
    }
}
