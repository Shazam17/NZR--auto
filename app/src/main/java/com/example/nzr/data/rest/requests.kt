package com.example.nzr.data.rest

import com.example.nzr.data.rest.models.board
import com.example.nzr.data.rest.models.cardDetail
import com.example.nzr.data.rest.models.listsCards
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrelloRequests{


    @GET("boards/{boardID}")
    fun getBoard(@Path("boardID")boardId:String,@Query("key")key:String , @Query("token")token:String) :Single<Response<board>>

    @GET("boards/{boardID}/lists")
    fun getListsOfBoard(@Path("boardID")boardId:String,
                        @Query("key")key:String ,
                        @Query("token")token:String,
                        @Query("cards")cards:String,
                        @Query("card_fields")cardFields:String,
                        @Query("filter")filter:String,
                        @Query("fields")fields:String) :Single<Response<List<listsCards>>>

    @GET("cards/{cardId}")
    fun getCardById(@Path("cardId")cardId:String,
                    @Query("key")key:String ,
                    @Query("token")token:String,
                    @Query("fields")fields:String) : Single<Response<cardDetail>>
    @GET("boards/")
    fun getAllBoards(@Query("boards")boards:String):Single<Response<List<board>>>
}
interface YandexRequests{

}

class RetrofitFabric{
    fun getRetrofit() :Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.trello.com/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }


    fun getTrello() : TrelloRequests{
        return getRetrofit().create(TrelloRequests::class.java)
    }
    fun getYandex() : YandexRequests{
        return getRetrofit().create(YandexRequests::class.java)
    }
}
