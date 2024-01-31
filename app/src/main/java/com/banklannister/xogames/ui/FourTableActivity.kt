package com.banklannister.xogames.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.banklannister.xogames.data.GamesFour
import com.banklannister.xogames.databinding.ActivityFourTableBinding
import com.banklannister.xogames.models.GameModelFour
import com.banklannister.xogames.models.GameStatus

class FourTableActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityFourTableBinding

    private var gameModelFour : GameModelFour? = null



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

        binding.startGameBtn.setOnClickListener {
            startGame()
        }

        GamesFour.gameModel.observe(this){
            gameModelFour = it
            setUi()
        }


    }

    private fun setUi(){
        gameModelFour?.apply {
            binding.btn0.text = filledPos[0]
            binding.btn1.text = filledPos[1]
            binding.btn2.text = filledPos[2]
            binding.btn3.text = filledPos[3]
            binding.btn4.text = filledPos[4]
            binding.btn5.text = filledPos[5]
            binding.btn6.text = filledPos[6]
            binding.btn7.text = filledPos[7]
            binding.btn8.text = filledPos[8]
            binding.btn9.text = filledPos[9]
            binding.btn10.text = filledPos[10]
            binding.btn11.text = filledPos[11]
            binding.btn12.text = filledPos[12]
            binding.btn13.text = filledPos[13]
            binding.btn14.text = filledPos[14]
            binding.btn15.text = filledPos[15]

            binding.startGameBtn.visibility = View.VISIBLE

            binding.gameStatusText.text =
                when(gameStatus){
                    GameStatus.CREATED -> {
                        binding.startGameBtn.visibility = View.VISIBLE
                        "Game ID :"+ gameId
                    }
                    GameStatus.JOINED ->{
                        "Click on start game"
                    }
                    GameStatus.INPROGRESS ->{
                        binding.startGameBtn.visibility = View.VISIBLE
                        when(GamesFour.myID){
                            currentPlayer -> "Your turn"
                            else ->  currentPlayer + " turn"
                        }

                    }
                    GameStatus.FINISHED ->{
                        if(winner.isNotEmpty()) {
                            when(GamesFour.myID){
                                winner -> "You won"
                                else ->   winner + " Won"
                            }

                        }
                        else "DRAW"

                    }
                }

        }
    }


   private fun startGame(){
        gameModelFour?.apply {
            updateGameData(
                GameModelFour(
                    gameId = gameId,
                    gameStatus = GameStatus.INPROGRESS
                )
            )
        }
    }

    private fun updateGameData(model : GameModelFour){
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

        gameModelFour?.apply {
            for ( i in winningPos){
                //0123
                if(
                    filledPos[i[0]] == filledPos[i[1]] &&
                    filledPos[i[1]]== filledPos[i[2]] &&
                    filledPos[i[2]] == filledPos[i[3]] &&
                    filledPos[i[0]].isNotEmpty()
                ){
                    gameStatus = GameStatus.FINISHED
                    winner = filledPos[i[0]]
                }
            }

            if( filledPos.none(){ it.isEmpty() }){
                gameStatus = GameStatus.FINISHED
            }

            updateGameData(this)
        }
    }

    override fun onClick(v: View?) {
        gameModelFour?.apply {
            if(gameStatus!= GameStatus.INPROGRESS){
                Toast.makeText(applicationContext,"Game not started", Toast.LENGTH_SHORT).show()
                return
            }

            val clickedPos =(v?.tag  as String).toInt()
            if(filledPos[clickedPos].isEmpty()){
                filledPos[clickedPos] = currentPlayer
                currentPlayer = if(currentPlayer=="X") "O" else "X"
                checkForWinner()
                updateGameData(this)
            }

        }
    }
}