package com.mazy.capitalwatersupplyadmin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mazy.capitalwatersupplyadmin.R
import com.mazy.capitalwatersupplyadmin.interfaces.TankerClickListner
import com.mazy.capitalwatersupplyadmin.models.Tanker
import kotlinx.android.synthetic.main.addresses_item_layout.view.*

class TankerAdapter(val tankerClickListner: TankerClickListner): RecyclerView.Adapter<TankerAdapter.AddressesViewHolder>() {
    class AddressesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    var addressList : List<Tanker> = listOf()
    fun setAddress(addrList: List<Tanker>){
        addressList = addrList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressesViewHolder {
        val View = LayoutInflater.from(parent.context).inflate(R.layout.addresses_item_layout, parent, false)
        return AddressesViewHolder(View)
    }

    override fun onBindViewHolder(holder: AddressesViewHolder, position: Int) {
        val address = addressList[position]

        holder.itemView.tvTankerName.text = address.tankerName.toString()
        if(address.tankerType != ""){
            holder.itemView.tvTankerType.text = "("+address.tankerType.toString()+")"
        }else{
         holder.itemView.tvTankerType.visibility = View.GONE
        }
//        holder.itemView.tvTankerPrice.text = "Rs. " + address.tankerPrice.toString()
//        holder.itemView.tvAddress.text = address.address.toString()

        holder.itemView.btnEditAddress.setOnClickListener {
            tankerClickListner.onAddressItemClickListner(it, address)
        }
        holder.itemView.btnDeleteAddress.setOnClickListener {
            tankerClickListner.onAddressItemClickListner(it, address)
        }
    }

    override fun getItemCount(): Int {
        return addressList.size
    }
}