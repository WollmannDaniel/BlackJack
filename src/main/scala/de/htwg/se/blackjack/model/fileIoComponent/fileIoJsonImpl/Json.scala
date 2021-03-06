package de.htwg.se.blackjack.model.fileIoComponent.fileIoJsonImpl

import de.htwg.se.blackjack.controller.{GameState, IController}
import de.htwg.se.blackjack.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.blackjack.model.deckComponent.Rank.Rank
import de.htwg.se.blackjack.model.deckComponent.{ICard, IDeck, Rank, Suit}
import de.htwg.se.blackjack.model.deckComponent.Suit.Suit
import de.htwg.se.blackjack.model.deckComponent.deckBaseImpl.{Card, Deck}
import de.htwg.se.blackjack.model.fileIoComponent.IFileIO
import de.htwg.se.blackjack.model.gameConfigComponent.IGameConfig
import de.htwg.se.blackjack.model.gameConfigComponent.gameConfigBaseImpl.GameConfig
import de.htwg.se.blackjack.model.playerComponent.playerComponentBaseImpl._
import de.htwg.se.blackjack.model.playerComponent.{IHand, IPlayer}
import play.api.libs.json.{JsArray, JsLookupResult, JsNumber, JsObject, JsValue, Json}

import scala.io.Source
import scala.collection.Seq

class Json extends IFileIO {
    override def load: IController = {
        val source: String = Source.fromFile("controller.json").getLines().mkString
        val json: JsValue = Json.parse(source)
        controllerFromJson(json)
    }

    override def save(controller: IController): Unit = {
        import java.io._
        val pw = new PrintWriter(new File("controller.json"))
        pw.write(Json.prettyPrint(controllerToJson(controller)))
        pw.close()
    }

    def controllerFromJson(value: JsValue): IController = {
        val controller = new Controller(gameConfigFromJson(value \ "controller" \"gameConfig"))
        controller.gameState = GameState.withName((value \ "controller" \ "gameState").as[String])
        controller
    }

    def gameConfigFromJson(seq: JsLookupResult): IGameConfig = {
        GameConfig(
            playersFromJson(seq \ "players" \\ "player"),
            playerFromJson((seq \ "dealer").get),
            Deck(cardsFromJson(seq \ "deck" \\ "card")),
            (seq \ "activePlayerIndex").as[Int],
            playersFromJson(seq \ "winners" \\ "player")
        )
    }

    def playersFromJson(seq: Seq[JsValue]): Vector[IPlayer] = {
        var players = Vector[IPlayer]()
        for (p <- seq) {
            players = players :+ playerFromJson(p)
        }
        players
    }

    def playerFromJson(seq: JsValue): IPlayer = {
        val cards = cardsFromJson(seq \ "hand" \\ "card")
        Player(
            (seq \ "name").as[String],
            Hand(cards)
        )
    }

    def cardsFromJson(seq: Seq[JsValue]): Vector[ICard] = {
        var cards = Vector[ICard]()
        for(c <- seq){
            val cardSymbol = c.as[String]
            var rank = ""
            var suit = ""

            if (cardSymbol.count(_.isValidChar) == 3) {
                rank = cardSymbol.slice(0,2)
                suit = cardSymbol.slice(2,3)
            } else {
                rank = cardSymbol.slice(0,1)
                suit = cardSymbol.slice(1,2)
            }

            cards = cards :+ Card(checkSuit(suit), checkRank(rank))
        }
        cards
    }

    def checkSuit(suit: String): Suit = {
        var ret = Suit.Diamond
        suit match{
            case "D" => ret = Suit.Diamond
            case "S" => ret = Suit.Spade
            case "C" => ret = Suit.Club
            case "H" => ret = Suit.Heart
        }
        ret
    }

    def checkRank(rank: String): Rank = {
        var ret = Rank.Ace
        rank match{
            case "2" => ret = Rank.Two
            case "3" => ret = Rank.Three
            case "4" => ret = Rank.Four
            case "5" => ret = Rank.Five
            case "6" => ret = Rank.Six
            case "7" => ret = Rank.Seven
            case "8" => ret = Rank.Eight
            case "9" => ret = Rank.Nine
            case "10" => ret = Rank.Ten
            case "J" => ret = Rank.Jack
            case "Q" => ret = Rank.Queen
            case "K" => ret = Rank.King
            case "A" => ret = Rank.Ace
        }
        ret
    }

    def controllerToJson(controller: IController): JsObject = {
        Json.obj(
            "controller" -> Json.obj(
                "gameState" -> controller.gameState.toString,
                "gameConfig" -> Json.toJson(gameConfigToJson(controller.gameConfig))
            )
        )
    }

    def gameConfigToJson(gameConfig: IGameConfig): JsObject = {
        Json.obj(
            "players" -> playersToJson(gameConfig.getPlayers()),
            "dealer" -> Json.toJson(dealerToJson(gameConfig.getDealer())),
            "deck" -> cardsToJson(gameConfig.getDeck().getCards()),
            "activePlayerIndex" -> JsNumber(gameConfig.getActivePlayerIndex()),
            "winners" -> playersToJson(gameConfig.getWinners())
        )
    }

    def playersToJson(players: Vector[IPlayer]): JsArray = {
        var jSonObject = Json.arr()
        for (p <- players) {
            jSonObject = jSonObject :+ Json.obj("player" -> Json.toJson(playerToJson(p)))
        }
        jSonObject
    }

    def playerToJson(p: IPlayer): JsObject = Json.obj(
        "name" -> p.getName(),
        "hand" -> handToJson(p.getHand())
    )

    def handToJson(hand: IHand): JsArray = {
        cardsToJson(hand.getCards())
    }

    def cardsToJson(cards: Vector[ICard]): JsArray = {
        var jSonObject = Json.arr()
        for (c <- cards) {
            jSonObject = jSonObject :+ Json.obj(
                "card" -> c.mapCardSymbol()
            )
        }
        jSonObject
    }

    def dealerToJson(dealer: IPlayer): JsObject = {
        Json.obj(
            "name" -> "Dealer",
            "hand" -> handToJson(dealer.getHand())
        )
    }
}
