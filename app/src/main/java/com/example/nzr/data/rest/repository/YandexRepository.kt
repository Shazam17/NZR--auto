package com.example.nzr.data.rest.repository

import com.example.nzr.data.rest.RetrofitFabric
import com.example.nzr.data.rest.YandexRequests
import com.example.nzr.data.rest.models.listsCards
import com.example.nzr.data.rest.models.yandexBoard
import com.example.nzr.data.rest.models.yandexCard
import com.example.nzr.data.rest.models.yandexQueue
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class YandexRepository {

    var repository = RetrofitFabric().getYandex()

    fun fetchAllBoards(): Single<Response<List<yandexBoard>>> {
        return repository
            .getAllBoards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchCardById(id:String) : Single<Response<yandexCard>>{
        return repository
            .getCardById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun  fetchCards(filterYandex: Map<String,String>) : Single<Response<List<yandexCard>>>{
        return repository
            .getCards(filterYandex)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun createCard(name:String ,id:String){
        var map = mapOf<String,String>("summary" to name)
        return repository
            .createCard(yandexQueue(self = "",id = id,key = "",display = ""))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}