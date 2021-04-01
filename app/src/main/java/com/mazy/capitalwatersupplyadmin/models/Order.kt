package com.mazy.capitalwatersupplyadmin.models

import com.google.firebase.database.PropertyName

data class Order(
    @PropertyName("userId") var userId: String? = null,
    @PropertyName("id") var id: String? =null,
    @PropertyName("name") val name:String? = null,
    @PropertyName("number")val number: String? = null,
    @PropertyName("address") val address :String? = null,
    @PropertyName("quantity") val quantity: String? = null,
    @PropertyName("uPrice")var uPrice: String? = null,
    @PropertyName("tPrice")var tPrice: String? = null,
    @PropertyName("tankerName")val tankerName : String? = null,
    @PropertyName("tankerType")val tankerType: String? = null,
    @PropertyName("status")val status : String? = null
)