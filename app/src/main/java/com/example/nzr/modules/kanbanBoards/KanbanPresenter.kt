package com.example.nzr.modules.kanbanBoards

import android.util.Log
import com.example.nzr.data.rest.models.listsCards
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.modules.startScreen.RXPresenter
import io.reactivex.rxkotlin.plusAssign

class KanbanPresenter(var view :KanbanContract.KanbanView) : KanbanContract.KanbanPresenter, RXPresenter(){

    lateinit var lists : List<listsCards>

    var trello = TrelloRepository()

    fun fetchListsRep(){
        subscriptions += trello
            .fetchAllCards(view.getBoardId())
            .subscribe({
                Log.d("fetchRep","success")
                Log.d("fetchRep",it.message())
                Log.d("fetchRep",it.raw().toString())
                lists = it.body()!!
                view.initPagerAdapter(lists)

                },{
                    Log.d("fetchRep","errorr")
                })
    }


}