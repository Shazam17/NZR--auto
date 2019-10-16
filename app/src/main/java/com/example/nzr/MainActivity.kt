package com.example.nzr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nzr.common.adapters.CardListAdapter
import com.example.nzr.data.rest.RetrofitFabric
import com.example.nzr.data.rest.TrelloRequests
import com.example.nzr.data.rest.models.cardShort

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception


//936bbab43463e479a095c368eb847f35


// trelo server token dbaca998bd52ec777318a316442f4997c9441537b97f22e5fb9663288b5aa56d

// oAuth1 trello e04dff0b9d02b6259e5aa1e61731c3a250448539c0d44aa5dbd172854ad2569d


class NZRInterceptor :Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()

        var requestBuiler = original.newBuilder()

        var req = requestBuiler.build()
        Log.d("Urll",req.url().toString())
        return chain.proceed(req)
    }
}


class MainActivity : AppCompatActivity(){

    lateinit var adapter :CardListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RetrofitFabric()
            .getTrello()
            .getBoard("30uyYyEU")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
            {
                Log.d("main","successs")
                Log.d("main",it.body()?.name)
            },{
                Log.d("main","error")
                Log.d("main",it.localizedMessage)
            })

        var listCards : List<cardShort>
        var listOserver  = trello
            .getListsOfBoard("30uyYyEU",
            "all",
            "name",
            "open",
            "name")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if(it.isSuccessful){
                        Log.d("main","successs")

                        it.body()?.get(0)!!.cards.forEach{Log.d("main",it.name)}
                        listCards = it.body()?.get(0)!!.cards
                        adapter = CardListAdapter(listCards ,this)
                        list.adapter = adapter
                        list.layoutManager = LinearLayoutManager(this)
                    }
                },{
                    Log.d("main","error")
                    Log.d("main",it.localizedMessage)
                })


    }



}
