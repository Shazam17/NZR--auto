package com.example.nzr.data.rest

import com.example.nzr.data.rest.models.board
import com.example.nzr.data.rest.models.listsCards
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
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

}