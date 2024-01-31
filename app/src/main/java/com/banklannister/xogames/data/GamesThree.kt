package com.banklannister.xogames.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.banklannister.xogames.models.GamesThreeData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


object GamesThree  {
    private var _gameModel: MutableLiveData<GamesThreeData> = MutableLiveData()
    var gameModel: LiveData<GamesThreeData> = _gameModel
    var myID = ""


    fun saveGameThree(model: GamesThreeData) {
            _gameModel.postValue(model)
            if (model.matchId != "-1") {
                Firebase.firestore.collection("games")
                    .document(model.matchId)
                    .set(model)

        }

    }

    fun fetchGameDataThree() {
            _gameModel.value?.apply {
                if (matchId != "-1") {
                    Firebase.firestore.collection("games")
                        .document(matchId)
                        .addSnapshotListener { value, _ ->
                            val model = value?.toObject(GamesThreeData::class.java)
                            _gameModel.postValue(model!!)
                }
            }
        }
    }

}








