package de.htwg.se.blackjack.aview.gui

import java.awt.Image

import de.htwg.se.blackjack.controller.{Controller, DealersTurn, NewGameStarted, PlayerWentOver, RefreshData, SetupMenu, ShowResults}
import javax.swing.ImageIcon
import javax.swing.border.{Border, LineBorder}

import scala.swing._
import scala.swing.event.ButtonClicked

class BoardGui(parent: SetupGui, controller: Controller) extends Frame {
    listenTo(controller)
    title = "Blackjack"
    peer.setPreferredSize(new Dimension(1000, 750))
    peer.setResizable(false)
    peer.setDefaultCloseOperation(3)

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

    def getCards(hideDealerCards: Boolean, isDealer: Boolean, playerIndex: Int): collection.mutable.Buffer[Component] = {
        var content = collection.mutable.Buffer[Component]()
        val cardList = controller.mapSymbolToChar(hideDealerCards, isDealer, playerIndex)
        for(str <- cardList) {
            content += new Label {
                icon = scaledImageIcon(pathToImage + str, imageWidth, imageHeight)
            }
        }
        content
    }

    def createDealerCards = new FlowPanel {
        val cards = getCards(hideDealerCard, true, -1)
        for(content <- cards) {
            contents += content
        }
    }

    def createDealerGrid: GridPanel = new GridPanel(2,1){
        contents += lbl_dealer
        contents += createDealerCards
    }

    def createPlayerGrid(index: Int = -1): GridPanel = new GridPanel(2,1) {
        if (index != -1) {
            contents += new Label {
                text = controller.gameConfig.players(index).name
            }
        } else {
            contents += lbl_player
        }
        contents += createPlayerCards(index)
    }

    def createPlayerCards(index: Int): FlowPanel = new FlowPanel {
        val cards = getCards(false, false, index)
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

    val endGameButtonFlowPanel = new FlowPanel {
        val btn_NewGame = new Button{
            text = "New Game"
        }
        val btn_Exit = new Button{
            text = "Exit"
        }
        contents += btn_NewGame
        contents += btn_Exit

        listenTo(btn_NewGame, btn_Exit)

        reactions += {
            case ButtonClicked(component) => {
                if(component == btn_NewGame) {
                    hideDealerCard = true
                    controller.newGame()
                } else if(component == btn_Exit) {
                    controller.quitGame()
                    Dialog.showMessage(contents.head, "Good bye!", "Ciao Bella")
                    System.exit(0)
                }
            }
        }
    }

    contents = new GridPanel(3,1) {
        contents += createDealerGrid
        contents += createPlayerGrid()
        contents += buttonFlowPanel
    }

    reactions += {
        case event: SetupMenu => {
            parent.pack()
            parent.redraw
            parent.visible = true
            dispose()
        }
        case event: DealersTurn => {
            hideDealerCard = false
            redraw
            lbl_player.text = controller.getActivePlayerName
        }
        case event: RefreshData => {
            redraw
            lbl_player.text = controller.getActivePlayerName
        }
        case event: PlayerWentOver => {
            Dialog.showMessage(contents.head, s"${controller.getActivePlayerName}'s hand value went over twenty-one!", "Game message")
            lbl_player.text = controller.getActivePlayerName
            repaint
        }
        case event: ShowResults => {
            redrawResults
        }
        case event: NewGameStarted => hideDealerCard = true
    }

    def redraw: Unit = {
        val grid = new GridPanel(3,1) {
            contents += createDealerGrid
            contents += createPlayerGrid()
            contents += buttonFlowPanel
        }
        contents = grid
    }

    def redrawResults: Unit = {
        val playerAmount = controller.gameConfig.players.size
        contents = new BoxPanel(Orientation.Vertical){
            contents += new GridPanel(1,1) {
                contents += new Label {
                    text = controller.gameConfig.getAllWinnerAsString
                    //horizontalAlignment = Alignment.Center

                }
            }
            contents += createDealerGrid

            contents += new GridPanel(2,2) {
                for (i <- 0 until playerAmount) {
                    contents += createPlayerGrid(i)
                }
            }
            contents += endGameButtonFlowPanel

        }
    }

    def scaledImageIcon(path: String, width: Int, height: Int): ImageIcon = {
        val orig = new ImageIcon(path)
        val scaledImage = orig.getImage.getScaledInstance(width, height, Image.SCALE_DEFAULT)
        new ImageIcon(scaledImage)
    }
}
