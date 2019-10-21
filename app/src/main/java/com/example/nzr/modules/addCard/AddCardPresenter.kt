package com.example.nzr.modules.addCard

import android.util.Log
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import com.example.nzr.modules.startScreen.RXPresenter
import io.reactivex.rxkotlin.plusAssign

class CreateCardPresenter(var view:CreateCardContract.AddCardView) :CreateCardContract.AddCardPresenter,RXPresenter(){

    override fun createCard(name:String,id: String, vendor: Boolean) {
        if(vendor){
            //trello
           // TrelloRepository()
                //.createCard(id)
        }else{
            //yandex
            subscriptions += YandexRepository()
                .createCard(name,id)
                .subscribe({
                        Log.d("create",it.message())
                    },{
                        Log.d("create",it.localizedMessage)
                    })
            }
    }
}