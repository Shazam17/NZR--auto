package com.example.nzr.data.rest.repository

import com.example.nzr.data.rest.RetrofitFabric
import com.example.nzr.data.rest.models.board
import com.example.nzr.data.rest.models.cardDetail
import com.example.nzr.data.rest.models.cardShort
import com.example.nzr.data.rest.models.listsCards
import com.example.nzr.modules.startScreen.RXPresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class TrelloRepository {

    var trelloFabric =  RetrofitFabric().getTrello()

    fun fetchAllCards(id:String):Single<Response<List<listsCards>>>{
        var map = mapOf("cards" to "all",
            "card_fields" to "name",
            "filter" to "open",
            "fields" to "name")
        return trelloFabric
            .getListsOfBoard(id,map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchBoards(): Single<Response<List<board>>> {
        var map = mapOf("fields" to "all")

        return trelloFabric
            .getAllBoards(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchCardById(id :String) :Single<Response<cardDetail>> {
        var fields = "name,desc"
        return trelloFabric
            .getCardById(id, fields)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}