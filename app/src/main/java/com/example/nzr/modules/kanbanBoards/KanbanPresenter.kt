package com.example.nzr.modules.kanbanBoards

import android.util.Log
import com.example.nzr.data.rest.models.cardShort
import com.example.nzr.data.rest.models.listsCards
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import com.example.nzr.modules.startScreen.RXPresenter
import io.reactivex.Observable
import io.reactivex.rxkotlin.plusAssign

class KanbanPresenter(var view :KanbanContract.KanbanView) : KanbanContract.KanbanPresenter, RXPresenter(){

    var lists : MutableList<listsCards> = ArrayList()


    override fun fetchListsRepTrello(){
        subscriptions += TrelloRepository()
            .fetchCardsById(view.getTrelloBoardId()!!)
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

    override fun fetch() {
//        if(view.getTrelloBoardId() != null){
//            Log.d("fetchRep","trello")
//          fetchListsRepTrello()
//        }
//        if(view.getYandexBoardId() != null){
//            Log.d("fetchRep","trello")
//            fetchListsRepYandex()
//        }
        Log.d("fetchRep",view.getTrelloBoardId()!!)
        Log.d("fetchRep",view.getYandexBoardId()!!)
        if(view.getTrelloBoardId() != null && view.getYandexBoardId() != null ){
            subscriptions += TrelloRepository()
                .fetchCardsById(view.getTrelloBoardId()!!)
                .concatMap {
                    lists.addAll(it.body()!!)
                    Log.d("fetchRep",lists.toString())
                    view.initPagerAdapter(lists)
                    YandexRepository()
                        .fetchCards(mapOf("queue" to view.getYandexBoardId()!!))
                }.concatMap{
                    Log.d("fetchRep",it.body().toString())
                    if(it.body() != null){
                        var  ls : MutableList<cardShort> = ArrayList()
                        //it.body()!!.forEach { ls.add(cardShort(it.id,it.summary))}
                        it.body()!!.forEach { task -> if(task.status.key.equals("open")) ls.add(cardShort(task.id,task.summary))}
                        lists[0].cards.addAll(ls)
                        Log.d("fetchRep",lists.toString())
                        view.initPagerAdapter(lists)
                    }
                    Log.d("fetchRep",it.code().toString())
                    Observable.just(null)
                }.subscribe({

                },{

                })
        }
    }

    override fun fetchListsRepYandex() {

        subscriptions += YandexRepository()
            .fetchCards(mapOf("queue" to view.getYandexBoardId()!!))
            .subscribe({
                    var  ls : MutableList<cardShort> = ArrayList()

                    if(it.body() != null){
                        it.body()!!.forEach { ls.add(cardShort(it.id,it.summary))
                        Log.d("fetchRep",it.summary)}
                    }

                    var list :listsCards = listsCards("id","name", ls)
                    lists.add(list)
                    view.initPagerAdapter(lists)
                },{

                })
    }

}