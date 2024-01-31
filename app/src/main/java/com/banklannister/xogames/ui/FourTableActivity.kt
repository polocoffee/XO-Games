package com.banklannister.xogames.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.banklannister.xogames.data.GameState
import com.banklannister.xogames.data.GamesFour
import com.banklannister.xogames.databinding.ActivityFourTableBinding
import com.banklannister.xogames.models.GamesFourData

class FourTableActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityFourTableBinding

    private var gamesFourData : GamesFourData? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFourTableBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GamesFour.fetchGameModelFour()

        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)
        binding.btn9.setOnClickListener(this)
        binding.btn10.setOnClickListener(this)
        binding.btn11.setOnClickListener(this)
        binding.btn12.setOnClickListener(this)
        binding.btn13.setOnClickListener(this)
        binding.btn14.setOnClickListener(this)
        binding.btn15.setOnClickListener(this)

        binding.readyButton.setOnClickListener {
            startGame()
        }

        GamesFour.gameModel.observe(this){
            gamesFourData = it
            setUi()
        }


    }

    private fun setUi(){
        gamesFourData?.apply {
            binding.btn0.text = position[0]
            binding.btn1.text = position[1]
            binding.btn2.text = position[2]
            binding.btn3.text = position[3]
            binding.btn4.text = position[4]
            binding.btn5.text = position[5]
            binding.btn6.text = position[6]
            binding.btn7.text = position[7]
            binding.btn8.text = position[8]
            binding.btn9.text = position[9]
            binding.btn10.text = position[10]
            binding.btn11.text = position[11]
            binding.btn12.text = position[12]
            binding.btn13.text = position[13]
            binding.btn14.text = position[14]
            binding.btn15.text = position[15]

            binding.readyButton.visibility = View.VISIBLE

            binding.gameStatusText.text =
                when(gameStatus){
                    GameState.CREATED -> {
                        binding.readyButton.visibility = View.VISIBLE
                        "MATCH ID :$matchId"
                    }
                    GameState.JOINED ->{
                        "Click on start game"
                    }
                    GameState.INPROGRESS ->{
                        binding.readyButton.visibility = View.VISIBLE
                        when(GamesFour.myID){
                            currentPlayer -> "Your turn"
                            else -> "$currentPlayer turn"
                        }

                    }
                    GameState.FINISHED ->{
                        if(winner.isNotEmpty()) {
                            when(GamesFour.myID){
                                winner -> "You won"
                                else -> "$winner Won"
                            }

                        }
                        else "DRAW"

                    }
                }

        }
    }


   private fun startGame(){
        gamesFourData?.apply {
            updateGameData(
                GamesFourData(
                    matchId = matchId,
                    gameStatus = GameState.INPROGRESS
                )
            )
        }
    }

    private fun updateGameData(model : GamesFourData){
        GamesFour.saveGameFour(model)
    }

    private fun checkForWinner(){
        val winningPos = arrayOf(
            intArrayOf(0,1,2,3),
            intArrayOf(4,5,6,7),
            intArrayOf(8,9,10,11),
            intArrayOf(12,13,14,15),
            intArrayOf(0,4,8,12),
            intArrayOf(1,5,9,13),
            intArrayOf(2,6,10,14),
            intArrayOf(3,7,11,15),
            intArrayOf(0,5,10,15),
            intArrayOf(3,6,9,12),
        )

        gamesFourData?.apply {
            for ( i in winningPos){
                //0123
                if(
                    position[i[0]] == position[i[1]] &&
                    position[i[1]]== position[i[2]] &&
                    position[i[2]] == position[i[3]] &&
                    position[i[0]].isNotEmpty()
                ){
                    gameStatus = GameState.FINISHED
                    winner = position[i[0]]
                }
            }

            if(position.none(){ it.isEmpty() }){
                gameStatus = GameState.FINISHED
            }

            updateGameData(this)
        }
    }

    override fun onClick(v: View?) {
        gamesFourData?.apply {
            if(gameStatus!= GameState.INPROGRESS){
                Toast.makeText(applicationContext,"PRESS READY BUTTON", Toast.LENGTH_SHORT).show()
                return
            }

            val clickedPosition =(v?.tag  as String).toInt()
            if(position[clickedPosition].isEmpty()){
                position[clickedPosition] = currentPlayer
                currentPlayer = if(currentPlayer=="X") "O" else "X"
                checkForWinner()
                updateGameData(this)
            }

        }
    }
}