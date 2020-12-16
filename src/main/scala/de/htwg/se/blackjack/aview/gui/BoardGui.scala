package de.htwg.se.blackjack.aview.gui

import java.awt.Image

import de.htwg.se.blackjack.controller.{Controller, RefreshData, DealersTurn}
import javax.swing.ImageIcon

import scala.swing._
import scala.swing.event.ButtonClicked

class BoardGui(controller: Controller) extends Frame {
    listenTo(controller)
    title = "Blackjack"
    peer.setPreferredSize(new Dimension(750, 750))
    peer.setResizable(false)

    val pathToImage = "src/main/scala/de/htwg/se/blackjack/aview/gui/img/"
    val imageHeight = 80
    val imageWidth = 60

    var hideDealerCard = true

    val lbl_dealer = new Label {
        text = "Dealer"
    }

    val lbl_player = new Label {
        text = controller.getActivePlayerName
    }

    val btn_hit = new Button {
        text = "Hit"
    }

    val btn_stand = new Button {
        text = "Stand"
    }

    def getCards(hideDealerCards: Boolean, isDealer: Boolean): collection.mutable.Buffer[Component] = {
        var content = collection.mutable.Buffer[Component]()
        val cardList = controller.mapSymbolToChar(hideDealerCards, isDealer)
        for(str <- cardList) {
            content += new Label {
                icon = scaledImageIcon(pathToImage + str, imageWidth, imageHeight)
            }
        }
        content
    }

    def createDealerCards = new FlowPanel {
        val cards = getCards(hideDealerCard, true)
        for(content <- cards) {
            contents += content
        }
    }

    def createDealerGrid: GridPanel = new GridPanel(2,1){
        contents += lbl_dealer
        contents += createDealerCards
    }

    def createPlayerGrid: GridPanel = new GridPanel(2,1) {
        contents += lbl_player
        contents += createPlayerCards
    }

    def createPlayerCards: FlowPanel = new FlowPanel {
        val cards = getCards(false, false)
        for (content <- cards) {
            contents += content
        }
    }

    val buttonFlowPanel = new FlowPanel {
        contents += btn_hit
        contents += btn_stand

        listenTo(btn_hit, btn_stand)

        reactions += {
            case ButtonClicked(component) => {
                if(component == btn_hit) {
                    controller.playerHits()
                } else if(component == btn_stand) {
                    controller.playerStands()
                }
            }
        }
    }

    contents = new GridPanel(3,1) {
        contents += createDealerGrid
        contents += createPlayerGrid
        contents += buttonFlowPanel
    }

    reactions += {
        case event: DealersTurn => {
            hideDealerCard = false
            redraw
        }
        case event: RefreshData => redraw

        lbl_player.text = controller.getActivePlayerName
    }

    def redraw: Unit = {
        val grid = new GridPanel(3,1) {
            contents += createDealerGrid
            contents += createPlayerGrid
            contents += buttonFlowPanel
        }
        contents = grid
    }

    def scaledImageIcon(path: String, width: Int, height: Int): ImageIcon = {
        val orig = new ImageIcon(path)
        val scaledImage = orig.getImage.getScaledInstance(width, height, Image.SCALE_DEFAULT)
        new ImageIcon(scaledImage)
    }
}
