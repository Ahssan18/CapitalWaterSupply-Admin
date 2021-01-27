package com.mazy.capitalwatersupplyadmin.interfaces

import android.view.View
import com.mazy.capitalwatersupplyadmin.models.Addresses

interface AddressClickListner {

    fun onAddressItemClickListner(view: View, address: Addresses)

}