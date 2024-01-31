package com.banklannister.xogames.models

import kotlin.random.Random

data class GameModelFour (
    var gameId : String = "-1",
    var filledPos : MutableList<String> = mutableListOf("","","","","","","","","","", "", "", "","", "", ""),
    var winner : String ="",
    var gameStatus : GameStatus = GameStatus.CREATED,
    var currentPlayer : String = (arrayOf("X","O"))[Random.nextInt(2)]
)


