package com.example.nzr.modules.startScreen

import android.util.Log
import com.example.nzr.data.rest.RetrofitFabric
import com.example.nzr.data.rest.models.board
import com.example.nzr.data.rest.repository.TrelloRepository
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class DepartmentPresenter(var view:DepartmentContract.DepartmentView) : DepartmentContract.DepartmentPresenter , RXPresenter(){

    var trelloRepository = TrelloRepository()

    override fun fetchDepartments() {
        var retList :List<board>?
        subscriptions += trelloRepository
            .fetchBoards()
            .subscribe({
                    retList = it.body()
                    view.initAdapter(retList)
                },{
                    Log.d("fetch",it.localizedMessage)
                })
    }
}