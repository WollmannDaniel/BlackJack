package de.htwg.se.blackjack.aview.gui

import java.awt.Color

import scala.swing._
import de.htwg.se.blackjack.controller.{IController, RefreshData, StartGame}

import scala.swing.event.ButtonClicked

class SetupGui(controller: IController) extends Frame {
    listenTo(controller)
    peer.setDefaultCloseOperation(3)
    title = "Blackjack"

    val textFontSize = 22
    val buttonFontSize = 20

    val backGroundColor = new Color(0, 102, 0)
    val fontColor = Color.WHITE
    val buttonColor = new Color(202, 42, 28)

    var boardGui = new BoardGui(SetupGui.this, controller)

    val btn_undo = new Button {
        text = "Undo"
        font = new Font("Arial", 0, buttonFontSize)
        foreground = fontColor
        background = buttonColor
        opaque = true
        borderPainted = false
    }
    val btn_redo = new Button {
        text = "Redo"
        font = new Font("Arial", 0, buttonFontSize)
        foreground = fontColor
        background = buttonColor
        opaque = true
        borderPainted = false
    }
    val btn_do = new Button {
        text = "Do"
        font = new Font("Arial", 0, buttonFontSize)
        foreground = fontColor
        background = buttonColor
        opaque = true
        borderPainted = false
    }

    val flowPanel = new FlowPanel {
        contents += btn_undo
        contents += btn_redo
        contents += btn_do
        background = backGroundColor
    }

    this.defaultButton = btn_do

    val txt_playername = new TextField {
        columns = 40
        font = new Font("Arial", 0, textFontSize)
    }

    val lbl_playername = new Label {
        text = controller.getPlayerName
        font = new Font("Arial", 0, textFontSize)
        foreground = fontColor
    }

    contents = new GridPanel(3,1) {
        btn_undo.enabled = false

        contents += lbl_playername
        contents += txt_playername
        contents += flowPanel
        background = backGroundColor
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

    centerOnScreen()
}
