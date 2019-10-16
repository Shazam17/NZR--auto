package com.example.nzr.modules.startScreen

import android.util.Log
import com.example.nzr.data.rest.RetrofitFabric
import com.example.nzr.data.rest.models.board
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class DepartmentPresenter(var view:DepartmentContract.DepartmentView) : DepartmentContract.DepartmentPresenter , RXPresenter(){


    override fun fetchDepartments() {
        var map = mapOf("fields" to "all")
        var retList :List<board>?
        subscriptions += RetrofitFabric()
            .getTrello()
            .getAllBoards(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    retList = it.body()
                    retList?.forEach { Log.d("fetch",it.name) }
                    view.initAdapter(retList)

                },{
                    Log.d("fetch","errorr")
                    Log.d("fetch",it.localizedMessage)
                })
    }
}