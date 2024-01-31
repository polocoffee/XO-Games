package com.banklannister.xogames.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.banklannister.xogames.models.GameModelFour
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


object GamesFour {
    private var _gameModel: MutableLiveData<GameModelFour> = MutableLiveData()
    var gameModel: LiveData<GameModelFour> = _gameModel
    var myID = ""


    fun saveGameFour(model: GameModelFour) {
        _gameModel.postValue(model)
        if (model.gameId != "-1") {
            Firebase.firestore.collection("games")
                .document(model.gameId)
                .set(model)
        }

    }

    fun fetchGameModelFour(){
        gameModel.value?.apply {
            if(gameId!="-1"){
                Firebase.firestore.collection("games")
                    .document(gameId)
                    .addSnapshotListener { value, error ->
                        val model = value?.toObject(GameModelFour::class.java)
                        _gameModel.postValue(model!!)
                    }
            }
        }
    }

    }








