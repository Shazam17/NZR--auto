package com.example.nzr.modules.kanbanBoards

import android.util.Log
import com.example.nzr.data.rest.models.cardShort
import com.example.nzr.data.rest.models.listsCards
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import com.example.nzr.modules.startScreen.RXPresenter
import io.reactivex.rxkotlin.plusAssign

class KanbanPresenter(var view :KanbanContract.KanbanView) : KanbanContract.KanbanPresenter, RXPresenter(){

    var lists : MutableList<listsCards> = ArrayList()


    override fun fetchListsRepTrello(){
        subscriptions += TrelloRepository()
            .fetchCardsById(view.getBoardId())
            .subscribe({
                Log.d("fetchRep","success")
                Log.d("fetchRep",it.message())
                Log.d("fetchRep",it.raw().toString())
                lists.addAll(it.body()!!)
                view.initPagerAdapter(lists)

                },{
                    Log.d("fetchRep","errorr")
                })
    }



    override fun fetchListsRepYandex() {

        subscriptions += YandexRepository()
            .fetchCards(mapOf("queue" to view.getBoardId()))
            .subscribe({
                    var  ls : MutableList<cardShort> = ArrayList()

                    if(it.body() != null){
                        it.body()!!.forEach { ls.add(cardShort(it.id,it.summary))}
                    }

                    var list :listsCards = listsCards("id","name", ls)
                    view.initPagerAdapter(arrayListOf(list))
                },{

                })
    }

}