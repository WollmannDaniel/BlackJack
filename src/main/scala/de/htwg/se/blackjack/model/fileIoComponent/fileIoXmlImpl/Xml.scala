package de.htwg.se.blackjack.model.fileIoComponent.fileIoXmlImpl

import de.htwg.se.blackjack.controller.{GameState, IController}
import de.htwg.se.blackjack.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.blackjack.model.deckComponent.deckBaseImpl.{Card, Deck}
import de.htwg.se.blackjack.model.deckComponent.{ICard, IDeck, Rank, Suit}
import de.htwg.se.blackjack.model.fileIoComponent.IFileIO
import de.htwg.se.blackjack.model.gameConfigComponent.IGameConfig
import de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl.GameConfig
import de.htwg.se.blackjack.model.playerComponent.playerComponentBaseImpl.{Hand, Player}
import de.htwg.se.blackjack.model.playerComponent.{IHand, IPlayer}

import scala.xml.{Elem, Node, NodeSeq, PrettyPrinter}

class Xml extends IFileIO{

    override def load: IController = {
        val file = scala.xml.XML.loadFile("controller.xml")
        controllerFromXml(file)
    }

    def controllerFromXml(file: Elem): IController = {
        val controller = new Controller(gameConfigFromXml(file \ "gameConfig"))
        controller.gameState = GameState.withName((file \ "gameState").text)
        controller
    }

    def gameConfigFromXml(seq: NodeSeq): IGameConfig = {
        val gameConfig = GameConfig(
            playersFromXml(seq \ "players"),
            dealerFromXml(seq \ "dealer"),
            Deck(cardsFromXml(seq \ "deck")),
            (seq \ "activePlayerIndex").text.toInt,
            playersFromXml(seq \ "winners")
        )
        gameConfig
    }

    def playersFromXml(seq: NodeSeq): Vector[IPlayer] = {
        var players = Vector[IPlayer]()
        val playerSeq = seq \\ "player"
        for (p <- playerSeq) {
            players = players :+ Player(
                (p \ "name").text,
                Hand(cardsFromXml(p \ "hand"))
            )
        }
        players
    }

    def dealerFromXml(seq: NodeSeq): IPlayer = {
        Player(
            (seq \ "name").text,
            Hand(cardsFromXml(seq \ "hand"))
        )
    }

    def cardsFromXml(seq: NodeSeq): Vector[ICard] = {
        var cards = Vector[ICard]()
        val cardSeq = seq \\ "card"
        for (c <- cardSeq) {
            cards = cards :+ Card(
                Suit.withName((c \ "suit").text),
                Rank.withName((c \ "rank").text)
            )
        }
        cards
    }

    override def save(controller: IController): Unit = {
        import java.io._
        val pw = new PrintWriter(new File("controller.xml"))
        val prettyPrinter = new PrettyPrinter(200, 5)
        val xml = prettyPrinter.format(controllerToXml(controller))
        pw.write(xml)
        pw.close()
    }

    def controllerToXml(controller: IController): Node = {
        var c: Node =
            <controller>
                <gameState>{controller.gameState}</gameState>
            </controller>
        c = addNode(c, gameConfigToXml(controller.gameConfig))
        c
    }

    def gameConfigToXml(gameConfig: IGameConfig): Node = {
        var c: Node =
            <gameConfig>
                <activePlayerIndex>{gameConfig.getActivePlayerIndex()}</activePlayerIndex>
            </gameConfig>
        c = addNode(c, playersToXml(gameConfig.getPlayers()))
        c = addNode(c, dealerToXml(gameConfig.getDealer()))
        c = addNode(c, deckToXml(gameConfig.getDeck()))
        c = addNode(c, winnersToXml(gameConfig.getWinners()))
        c
    }

    def playersToXml(players: Vector[IPlayer]): Node = {
        var playersNode: Node = <players></players>
        for (p <- players) {
            val player = playerToXml(p)
            playersNode = addNode(playersNode, player)
        }
        playersNode
    }

    def winnersToXml(players: Vector[IPlayer]): Node = {
        var winnersNode: Node = <winners></winners>
        for (p <- players) {
            val player = playerToXml(p)
            winnersNode = addNode(winnersNode, player)
        }
        winnersNode
    }

    def dealerToXml(dealer: IPlayer): Node = {
        var c: Node =
            <dealer>
                <name>{dealer.getName()}</name>
            </dealer>
        c = addNode(c, handToXml(dealer.getHand()))
        c
    }

    def playerToXml(player: IPlayer): Node = {
        var c: Node =
            <player>
                <name>{player.getName()}</name>
            </player>
        c = addNode(c, handToXml(player.getHand()))
        c
    }

    def deckToXml(deck: IDeck): Node = {
        var deckNode: Node = <deck></deck>
        for (c <- deck.getCards()) {
            val card = cardToXml(c)
            deckNode = addNode(deckNode, card)
        }
        deckNode
    }

    def handToXml(hand: IHand): Node = {
        var handNode: Node = <hand></hand>
        for (c <- hand.getCards()) {
            val card = cardToXml(c)
            handNode = addNode(handNode, card)
        }
        handNode
    }

    def cardToXml(card: ICard): Elem = {
        <card>
            <suit>{card.getSuit().toString}</suit>
            <rank>{card.getRank().toString}</rank>
        </card>
    }


    def addNode(to: Node, newNode: Node) = to match {
        case Elem(prefix, label, attributes, scope, child@_*) =>
            Elem(prefix, label, attributes, scope, child ++ newNode: _*)
        case _ => to
    }
}
