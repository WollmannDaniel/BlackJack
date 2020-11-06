package de.htwg.se.blackjack.aview

import de.htwg.se.blackjack.model.{Card, Deck, Person}

import scala.util.Random

class Tui {
    var deck: Deck = Deck(Random.shuffle(initList()))

    def processInputLine(input: String, person: Person): Person = {
        input match {
            case "s" | "exit" => person
            case "h"=> {
                val newDeck = deck.drawCard()
                val newPerson = person.addCard(newDeck.getDrawedCard(deck))
                deck = newDeck
                newPerson
            }
            case _ => {
                print("Unknown command")
                person
            }
        }
    }

    def initList(): List[Card] = {
        println("Shuffling card deck.")
        for {
            suit <- List("Heart", "Diamond", "Spade", "Club")
            rank <- List("Ace", "King", "Queen", "Jack", "Ten", "Nine", "Eight", "Seven", "Six", "Five", "Four", "Three", "Two")
        }
            yield Card(suit, rank)
    }
}
