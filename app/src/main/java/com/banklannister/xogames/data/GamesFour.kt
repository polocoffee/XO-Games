package com.banklannister.xogames.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.banklannister.xogames.models.GamesFourData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


object GamesFour {
    private var _gameModel: MutableLiveData<GamesFourData> = MutableLiveData()
    var gameModel: LiveData<GamesFourData> = _gameModel
    var myID = ""


    fun saveGameFour(model: GamesFourData) {
        _gameModel.postValue(model)
        if (model.matchId != "-1") {
            Firebase.firestore.collection("games")
                .document(model.matchId)
                .set(model)
        }

    }

    fun fetchGameModelFour(){
        gameModel.value?.apply {
            if(matchId!="-1"){
                Firebase.firestore.collection("games")
                    .document(matchId)
                    .addSnapshotListener { value, _ ->
                        val model = value?.toObject(GamesFourData::class.java)
                        _gameModel.postValue(model!!)
                    }
            }
        }
    }

    }








