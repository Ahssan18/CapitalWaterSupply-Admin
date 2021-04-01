package com.mazy.capitalwatersupplyadmin.models

import com.google.firebase.database.PropertyName

data class Addresses(
    @PropertyName("id") var id:String? = null,
    @PropertyName("address") val address:String? = null,
    @PropertyName("price") val price : String? = null
)