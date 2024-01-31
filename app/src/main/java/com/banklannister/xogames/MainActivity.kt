package com.banklannister.xogames

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.banklannister.xogames.data.GameState
import com.banklannister.xogames.data.GamesFour
import com.banklannister.xogames.data.GamesThree
import com.banklannister.xogames.databinding.ActivityMainBinding
import com.banklannister.xogames.models.GamesFourData
import com.banklannister.xogames.models.GamesThreeData
import com.banklannister.xogames.ui.FourTableActivity
import com.banklannister.xogames.ui.ThreeTableActivity
import kotlin.random.Random
import kotlin.random.nextInt


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Intent to Activity
        binding.apply {
            btnThreeTable.setOnClickListener {
                createGameThreeTable()
            }

            btnFourTable.setOnClickListener {
                createGameFourTable()
            }
        }
    }


    private fun fourTable() {
        startActivity(Intent(this, FourTableActivity::class.java))
    }


    private fun threeTable() {
        startActivity(Intent(this, ThreeTableActivity::class.java))
    }

    private fun createGameThreeTable() {
        GamesThree.saveGameThree(
            GamesThreeData(
                gameStatus = GameState.CREATED,
                matchId = Random.nextInt(1000..9999).toString()
            )
        )

        threeTable()
    }

    private fun createGameFourTable() {
        GamesFour.saveGameFour(
            GamesFourData(
                gameStatus = GameState.CREATED,
                matchId = Random.nextInt(1000..9999).toString()
            )
        )

        fourTable()
    }
}