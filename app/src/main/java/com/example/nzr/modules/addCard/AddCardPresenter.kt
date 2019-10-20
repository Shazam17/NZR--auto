package com.example.nzr.modules.addCard

import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository

class AddCardPresenter(var view:AddCardContract.AddCardView) :AddCardContract.AddCardPresenter{

    override fun createCard(name:String,id: String, vendor: Boolean) {
        if(vendor){
            //trello
            TrelloRepository()
                .createCard(id)
        }else{
            //yandex
            YandexRepository()
                .createCard(id)
        }
    }
}