package com.example.nzr.modules.CardDetailActivity

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.nzr.R
import com.example.nzr.data.rest.getTrello
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.card_kanban.view.*

class CardDetailActivity: AppCompatActivity(){

    var id :String? = ""


    override fun onStart() {
        super.onStart()
        id = intent.extras!!.getString("id")
        Log.d("detail2",id)

        var cardObserv = getTrello()
            .getCardById(id!!,
                "936bbab43463e479a095c368eb847f35",
                "dbaca998bd52ec777318a316442f4997c9441537b97f22e5fb9663288b5aa56d",
                "name,desc")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("detail",it.body()?.name)
                },{
                    Log.d("main",it.localizedMessage)
                })
    }


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_card_detail)
    }

}