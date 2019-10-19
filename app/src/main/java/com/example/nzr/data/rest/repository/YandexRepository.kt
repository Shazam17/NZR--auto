package com.example.nzr.data.rest.repository

import com.example.nzr.data.rest.RetrofitFabric
import com.example.nzr.data.rest.YandexRequests
import com.example.nzr.data.rest.models.listsCards
import com.example.nzr.data.rest.models.yandexBoard
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
}