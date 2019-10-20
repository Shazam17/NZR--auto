package com.example.nzr.modules.addCard

interface AddCardContract{


    interface AddCardPresenter{
        fun createCard(name:String,id: String, vendor: Boolean)
    }

    interface AddCardView{

    }

    interface AddCardModel{

    }

}