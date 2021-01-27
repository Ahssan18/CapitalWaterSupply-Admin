package com.mazy.capitalwatersupplyadmin.interfaces

import android.view.View
import com.mazy.capitalwatersupplyadmin.models.Order

interface OrderClickListner {

    fun onOrderItemClickListner(view: View, order:Order)

}