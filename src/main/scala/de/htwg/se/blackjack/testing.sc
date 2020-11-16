object Rank extends Enumeration {
    type Rank = Value
    val Ace = Value(11)
    val Two = Value
}

object Suit extends Enumeration {
    type Suit = Value
    val Diamond, Spade, Heart, Club = Value
}

val suit = Suit.Club
val rank = Rank.Ace

println(Suit.values.toVector)

println(s"${rank.id} of $suit" )
println(s"${rank.id} of $suit" )
println(s"${rank.id} of $suit" )