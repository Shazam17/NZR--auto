package com.example.nzr.modules.CardDetailActivity

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.nzr.R
import com.example.nzr.data.rest.RetrofitFabric
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_card_detail.*

class CardDetailActivity: AppCompatActivity(){

    var id :String? = ""


    override fun onStart() {
        super.onStart()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)

        id = intent.extras!!.getString("id")
        Log.d("detail2",id)
        var cardObserv = RetrofitFabric().getTrello()
            .getCardById(id!!, "name,desc")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    var name = it.body()?.name
                    nameDetail.text = it.body()!!.name
                    descDetail.text = it.body()!!.desc
                },{
                    Log.d("main",it.localizedMessage)
                })

    }

}