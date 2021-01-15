package de.htwg.se.blackjack.aview.gui

import java.awt.{Color, Image}

import de.htwg.se.blackjack.controller.{IController, InitGame}
import javax.swing.ImageIcon

import scala.swing._
import scala.swing.event.ButtonClicked

class WelcomeGui(controller: IController) extends Frame {
    visible = true
    peer.setPreferredSize(new Dimension(500, 400))
    peer.setResizable(false)
    peer.setDefaultCloseOperation(3)
    listenTo(controller)
    val backGroundColor = new Color(0, 102, 0)
    val fontColor = Color.WHITE
    val buttonColor = new Color(202, 42, 28)

    title = "Blackjack"
    val pathToLogo = "src/main/scala/de/htwg/se/blackjack/aview/gui/img/"
    val textFontSize = 22
    val buttonFontSize = 20

    val lbl_title = new Label {
        icon = scaledImageIcon(pathToLogo + "logo.png", 377, 170)
    }

    val lbl_Players = new Label {
        text += "<html><center>Starting new game!</center><center>The deck was shuffled.</center><center>How many players want to play?</center></html>"
        font = new Font("Arial", 0, textFontSize)
        foreground = fontColor
    }

    val btn_Player1 = new Button {
        text = "1 Player"
        font = new Font("Arial", 0, buttonFontSize)
        foreground = fontColor
        background = buttonColor
        opaque = true
        borderPainted = false

    }

    val btn_Player2 = new Button {
        text = "2 Player"
        font = new Font("Arial", 0, buttonFontSize)
        foreground = fontColor
        background = buttonColor
        opaque = true
        borderPainted = false
    }

    val btn_Player3 = new Button {
        text = "3 Player"
        font = new Font("Arial", 0, buttonFontSize)
        foreground = fontColor
        background = buttonColor
        opaque = true
        borderPainted = false
    }
    val btn_Player4 = new Button {
        text = "4 Player"
        font = new Font("Arial", 0, buttonFontSize)
        foreground = fontColor
        background = buttonColor
        opaque = true
        borderPainted = false
    }
    val flowPanel = new FlowPanel {
        contents += btn_Player1
        contents += btn_Player2
        contents += btn_Player3
        contents += btn_Player4
        background = backGroundColor
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
            }
        }
    }

    val grid = new GridPanel(2,1){
        contents += lbl_Players
        contents += flowPanel
        background = backGroundColor
    }

    contents = new GridPanel(2, 1) {
        contents += lbl_title
        contents += grid
        background = backGroundColor
    }

    reactions += {
        case event: InitGame => {
            new SetupGui(controller).visible = true
            dispose()
        }
    }

    def scaledImageIcon(path: String, width: Int, height: Int): ImageIcon = {
        val orig = new ImageIcon(path)
        val scaledImage = orig.getImage.getScaledInstance(width, height, Image.SCALE_DEFAULT)
        new ImageIcon(scaledImage)
    }

    centerOnScreen()
}
