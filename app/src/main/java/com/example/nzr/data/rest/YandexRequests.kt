package com.example.nzr.data.rest

import com.example.nzr.data.rest.models.*
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface YandexRequests{

    @GET("boards")
    fun getAllBoards(): Observable<Response<List<YandexBoard>>>

    @GET("queues")
    fun getAllQueues(): Observable<Response<List<QueueShort>>>

    @GET("issues/{cardId}")
    fun getCardById(@Path("cardId") cardId:String) : Observable<Response<YandexCard>>

    @GET("issues/{cardId}/transitions")
    fun getTransitions(@Path("cardId")cardId:String) : Observable<Response<List<Transition>>>

    @POST("issues/_search")
    fun getCards(@Body filter: Map<String,String>) : Observable<Response<List<YandexCard>>>

    @POST("issues/")
    fun createCard(@Body params: RequestCreateCardYandexBody) : Observable<Response<YandexCard>>

    @POST("/v2/issues/{issueId}/transitions/{transitionId}/_execute")
    fun moveCard(@Path("issueId") issueId:String, @Path("transitionId") transitionId:String) : Observable<Response<List<Transition>>>

}