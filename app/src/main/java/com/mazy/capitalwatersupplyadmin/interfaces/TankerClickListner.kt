package com.mazy.capitalwatersupplyadmin.interfaces

import android.view.View
import com.mazy.capitalwatersupplyadmin.models.Tanker

interface TankerClickListner {

    fun onAddressItemClickListner(view: View, tanker: Tanker)

}