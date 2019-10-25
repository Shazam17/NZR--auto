package com.example.nzr.modules.CardDetailActivity

import com.example.nzr.common.mvp.IView

interface CardDetailContract {

    interface CardDetailPresenter{
        fun fetchCardByIdTrello(id:String)
        fun fetchCardByIdYandex(id:String)
        fun moveToClosed(id:String,type:String)
    }

    interface CardDetailView : IView{
        fun initViews(name : String , desc : String)
        fun getVendor():Boolean
        fun back()
    }

    interface CardDetailModel{

    }
}