package com.example.nzr.data.rest.models

import com.google.gson.annotations.SerializedName


data class board(
     var id :String,
     var name :String,
     var desc :String,
     var descData :String,
     var closed :Boolean,
     var idOrganization: String,
     var pinned : Boolean,
     var url : Boolean,
     var shortUrl  :  Boolean
)

data class cardShort(
    var id:String,
    var name:String
)


data class cardDetail(
    var id:String,
    var name:String,
    var address:String,
    var pos:Float,
    var url:String,
    var subscribed:Boolean,
    var desc: String

)

data class listsCards(
    var id : String,
    var name:String,
    var cards : List<cardShort>
)

data class yandexBoard(
    var self : String,
    var id : String,
    var version : String,
    var name : String,
    var columns : List<yandexColumn>,
    //filter
    var useRanking : Boolean
)
data class yandexColumn(
    var self : String,
    var id : String,
    var display : String
)

data class yandexQueue(
    var self : String,
    var id : String,
    var key : String,
    var display : String
)

data class genericBoardShort(
    var id : String,
    var name : String,
    var vendor : Boolean
)

data class yandexCard(
    var self : String,
    var id : String,
    var key : String,
    var version : Int,
    var lastCommentUpdatedAt : String,
    var summary : String,
    var queue: yandexQueue


)

fun yandexToGeneric(board:yandexBoard) : genericBoardShort{
    return genericBoardShort(board.id , board.name, false)
}

fun trelloToGeneric(board:board) : genericBoardShort{
    return genericBoardShort(board.id , board.name, true)
}


