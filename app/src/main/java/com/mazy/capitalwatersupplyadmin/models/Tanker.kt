package com.mazy.capitalwatersupplyadmin.models

import com.google.firebase.database.PropertyName

data class Tanker(
    @PropertyName("id") var id:String? = null,
    @PropertyName("tankerName") val tankerName: String? = null,
    @PropertyName("tankerType") val tankerType : String? = null,
//    val tankerPrice: String? = null,
//    val address:String? = null
)