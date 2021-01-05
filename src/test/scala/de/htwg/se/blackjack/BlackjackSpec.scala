package de.htwg.se.blackjack

import java.io.ByteArrayInputStream

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import scala.io.StdIn

class BlackjackSpec extends AnyWordSpec with Matchers {
    "The Blackjack main class" should {
        "accept text input as argument without readline loop, to test it from command line " in {
            val in = new ByteArrayInputStream("q".getBytes)
            Console.withIn(in)  {
                Blackjack.main(Array[String](""))
            }
                //assert(StdIn.readLine()
        }
    }
}
