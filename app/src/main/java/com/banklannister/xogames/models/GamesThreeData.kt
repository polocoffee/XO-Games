package com.banklannister.xogames.models

import com.banklannister.xogames.data.GameState
import kotlin.random.Random

data class GamesThreeData (
    var matchId : String = "-1",
    var position : MutableList<String> = mutableListOf("","","","","","","","",""),
    var winner : String ="",
    var gameStatus : GameState = GameState.CREATED,
    var currentPlayer : String = (arrayOf("X","O"))[Random.nextInt(2)]
)


