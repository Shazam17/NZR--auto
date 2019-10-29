package com.example.nzr.modules.kanbanBoards

import android.util.Log
import com.example.nzr.common.mvp.RXPresenter
import com.example.nzr.data.rest.models.CardShort
import com.example.nzr.data.rest.models.ListsCards
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.plusAssign

class KanbanPresenter(var view :KanbanContract.KanbanView) : KanbanContract.KanbanPresenter, RXPresenter(){

    var lists : MutableList<ListsCards> = ArrayList()

    override fun getTrelloListId(position:Int): String {
        if(lists.size != 0){
            return lists[position].id
        }else{
            return "no"
        }
    }

    override fun fetch() {
        lists.clear()
        Log.d("kanbanPresenter","ids ${view.getTrelloBoardId()} ${view.getYandexBoardId()}")
        if(view.getTrelloBoardId() != "no" && view.getYandexBoardId() != "no"){
            fetchAll()
        }else{
            if(view.getTrelloBoardId() != "no"){
                fetchListsRepTrello()
            }
            if(view.getYandexBoardId() != "no"){
                fetchListsRepYandex()
            }
        }
    }

    private fun fetchAll() {
        if(view.getTrelloBoardId() != "no" && view.getYandexBoardId() != "no" ){
            subscriptions += TrelloRepository()
                .fetchCardsById(view.getTrelloBoardId()!!)
                .concatMap {
                    it.body()!!.forEach { list-> list.cards.forEach{card-> card.vendor = true} }
                    lists.addAll(it.body()!!)
                    view.initPagerAdapter(lists)
                    YandexRepository()
                        .fetchCards(mapOf("queue" to view.getYandexBoardId()!!))
                }.concatMap{
                    Log.d("fetchRep",it.body().toString())
                    if(it.body() != null){
                        var  lsOpen : MutableList<CardShort> = ArrayList()
                        it.body()!!.forEach { task -> if(task.status.key.equals("open")) lsOpen.add(CardShort(task.id,task.summary,false))}
                        lists[0].cards.addAll(lsOpen)

                        var lsNeedInformation : MutableList<CardShort> = ArrayList()
                        it.body()!!.forEach { task -> if(task.status.key.equals("needInfo")) lsNeedInformation.add(CardShort(task.id,task.summary,false))}
                        lists[1].cards.addAll(lsNeedInformation)

                        var lsInWork : MutableList<CardShort> = ArrayList()
                        it.body()!!.forEach { task -> if(task.status.key.equals("inProgress")) lsInWork.add(CardShort(task.id,task.summary,false))}
                        lists[2].cards.addAll(lsInWork)

                        var lsClosed : MutableList<CardShort> = ArrayList()
                        it.body()!!.forEach { task -> if(task.status.key.equals("resolved")) lsClosed.add(CardShort(task.id,task.summary,false))}
                        lists[3].cards.addAll(lsClosed)
                        view.initPagerAdapter(lists)
                        view.setRefresh(false)
                    }
                    Observable.just(null)
                }.subscribe({

                },{
                    Log.d("kanbanPresenter","error ${it.localizedMessage}")
                    view.setRefresh(false)
                })
        }
    }



    private fun fetchListsRepTrello(){
        subscriptions += TrelloRepository()
            .fetchCardsById(view.getTrelloBoardId()!!)
            .subscribe({
                lists.addAll(it.body()!!)
                it.body()!!.forEach { list-> list.cards.forEach{card-> card.vendor = true} }
                view.initPagerAdapter(lists)
                view.setRefresh(false)
            },{
                Log.d("kanbanPresenter","error ${it.localizedMessage}")
                view.setRefresh(false)
            })
    }

    private fun fetchListsRepYandex() {
        Log.d("fetchRep",view.getYandexBoardId()!!)
        subscriptions += YandexRepository()
            .fetchCards(mapOf("queue" to view.getYandexBoardId()!!))
            .subscribe({
                    //var  ls : MutableList<CardShort> = ArrayList()
//                    if(it.body() != null){
//                        it.body()!!.forEach { ls.add(CardShort(it.id,it.summary,false))
//                        Log.d("fetchRep",it.summary)}
//                    }
//                    var list = ListsCards("id","name", ls)


                var  lsOpen : MutableList<CardShort> = ArrayList()
                it.body()!!.forEach { task -> if(task.status.key.equals("open")) lsOpen.add(CardShort(task.id,task.summary,false))}
                lists.add(ListsCards("","",lsOpen))

                var lsNeedInformation : MutableList<CardShort> = ArrayList()
                it.body()!!.forEach { task -> if(task.status.key.equals("needInfo")) lsNeedInformation.add(CardShort(task.id,task.summary,false))}
                lists.add(ListsCards("","",lsNeedInformation))

                var lsInWork : MutableList<CardShort> = ArrayList()
                it.body()!!.forEach { task -> if(task.status.key.equals("inProgress")) lsInWork.add(CardShort(task.id,task.summary,false))}
                lists.add(ListsCards("","",lsInWork))

                var lsClosed : MutableList<CardShort> = ArrayList()
                it.body()!!.forEach { task -> if(task.status.key.equals("resolved")) lsClosed.add(CardShort(task.id,task.summary,false))}
                lists.add(ListsCards("","",lsClosed))


                view.initPagerAdapter(lists)
                view.setRefresh(false)
            },{
                Log.d("kanbanPresenter","error ${it.localizedMessage}")
                view.setRefresh(false)
            })
    }

}