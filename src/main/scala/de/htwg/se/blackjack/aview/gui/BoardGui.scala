package de.htwg.se.blackjack.aview.gui

import java.awt.{Color, Image}

import de.htwg.se.blackjack.controller.{DealersTurn, IController, NewGameStarted, PlayerWentOver, RefreshData, SetupMenu, ShowResults}
import javax.swing.ImageIcon

import scala.swing._
import scala.swing.event.{ButtonClicked, Key}

class BoardGui(parent: SetupGui, controller: IController) extends Frame {
    listenTo(controller)
    title = "Blackjack"
    peer.setPreferredSize(new Dimension(1000, 1000))
    peer.setResizable(false)
    peer.setDefaultCloseOperation(3)

    val pathToImage = "src/main/scala/de/htwg/se/blackjack/aview/gui/img/"
    val imageHeight = 116
    val imageWidth = 76

    var hideDealerCard = true
    val textFontSize = 28
    val buttonFontSize = 20
    val backGroundColor = new Color(0, 102, 0)
    val fontColor = Color.WHITE
    val buttonColor = new Color(202, 42, 28)

    val lbl_dealer = new Label {
        text = "Dealer"
        font = new Font("Arial", 0, textFontSize)
        foreground = fontColor
    }

    val lbl_player = new Label {
        text = controller.getActivePlayerName
        font = new Font("Arial", 0, textFontSize)
        foreground = fontColor
    }

    val btn_hit = new Button {
        text = "Hit"
        font = new Font("Arial", 0, buttonFontSize)
        foreground = fontColor
        background = buttonColor
        opaque = true
        borderPainted = false
    }

    val btn_stand = new Button {
        text = "Stand"
        font = new Font("Arial", 0, buttonFontSize)
        foreground = fontColor
        background = buttonColor
        opaque = true
        borderPainted = false
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
            background = backGroundColor
        }
    }

    def createDealerGrid: GridPanel = new GridPanel(2,1){
        contents += lbl_dealer
        contents += createDealerCards
        background = backGroundColor
    }

    def createPlayerGrid(index: Int = -1): GridPanel = new GridPanel(2,1) {
        if (index != -1) {
            contents += new Label {
                text = controller.gameConfig.getPlayerAtIndex(index).getName()
                font = new Font("Arial", 0, textFontSize)
                foreground = fontColor
            }
        } else {
            contents += lbl_player
        }
        contents += createPlayerCards(index)
        background = backGroundColor
    }

    def createPlayerCards(index: Int): FlowPanel = new FlowPanel {
        val cards = getCards(false, false, index)
        for (content <- cards) {
            contents += content
            background = backGroundColor
        }
    }

    val buttonFlowPanel = new FlowPanel {
        contents += btn_hit
        contents += btn_stand
        background = backGroundColor

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
            font = new Font("Arial", 0, buttonFontSize)
            foreground = fontColor
            background = buttonColor
            opaque = true
            borderPainted = false
        }
        val btn_Exit = new Button{
            text = "Exit"
            font = new Font("Arial", 0, buttonFontSize)
            foreground = fontColor
            background = buttonColor
            opaque = true
            borderPainted = false
        }
        contents += btn_NewGame
        contents += btn_Exit
        background = backGroundColor

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
        background = backGroundColor
    }

    menuBar = new MenuBar {
        contents += new Menu("File") {
            mnemonic = Key.F
            contents += new MenuItem(Action("Save") { controller.save })
            contents += new MenuItem(Action("Load") { controller.load })
        }
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
            repaint()
        }
        case event: ShowResults => {
            hideDealerCard = false
            redrawResults
        }
        case event: NewGameStarted => hideDealerCard = true
    }

    def redraw: Unit = {
        val grid = new GridPanel(3,1) {
            contents += createDealerGrid
            contents += createPlayerGrid()
            contents += buttonFlowPanel
            background = backGroundColor
        }
        contents = grid
    }

    def redrawResults: Unit = {
        val playerAmount = controller.gameConfig.getPlayers().size
        contents = new BoxPanel(Orientation.Vertical){
            contents += new GridPanel(1,1) {
                contents += new Label {
                    text = controller.gameConfig.getAllWinnerAsString
                    font = new Font("Arial", 0, textFontSize)
                    foreground = fontColor
                    //horizontalAlignment = Alignment.Center

                }
                background = backGroundColor
            }
            contents += createDealerGrid

            contents += new GridPanel(2,2) {
                for (i <- 0 until playerAmount) {
                    contents += createPlayerGrid(i)
                }
                background = backGroundColor
            }
            contents += endGameButtonFlowPanel
            background = backGroundColor
        }
    }

    def scaledImageIcon(path: String, width: Int, height: Int): ImageIcon = {
        val orig = new ImageIcon(path)
        val scaledImage = orig.getImage.getScaledInstance(width, height, Image.SCALE_DEFAULT)
        new ImageIcon(scaledImage)
    }

    centerOnScreen()
}
