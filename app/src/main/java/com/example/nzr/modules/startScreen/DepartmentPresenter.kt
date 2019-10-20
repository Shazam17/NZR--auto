package com.example.nzr.modules.startScreen

import android.util.Log
import com.example.nzr.data.rest.RetrofitFabric
import com.example.nzr.data.rest.models.*
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import retrofit2.Response

class DepartmentPresenter(var view:DepartmentContract.DepartmentView) : DepartmentContract.DepartmentPresenter , RXPresenter(){

    var trelloRepository = TrelloRepository()
    var yandexRepository = YandexRepository()

    var boards :MutableList<genericBoardShort> = ArrayList()

    override fun fetchDepartments() {
        subscriptions += yandexRepository
            .fetchAllBoards()
            .subscribe({
                it.body()?.forEach { boards.add(yandexToGeneric(it))
                Log.d("fetch",it.name)}
                view.updateAdapter(boards)
            },{
                Log.d("fetch",it.localizedMessage)
            })

        subscriptions += trelloRepository
            .fetchBoards()
            .subscribe({
                it.body()?.forEach { boards.add(trelloToGeneric(it))
                    Log.d("fetch",it.name)}
                view.updateAdapter(boards)
            },{
                    Log.d("fetch",it.localizedMessage)
                })

    }

}