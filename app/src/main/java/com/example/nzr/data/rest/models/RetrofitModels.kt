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