package com.mazy.capitalwatersupplyadmin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mazy.capitalwatersupplyadmin.R
import com.mazy.capitalwatersupplyadmin.interfaces.AddressClickListner
import com.mazy.capitalwatersupplyadmin.models.Addresses
import kotlinx.android.synthetic.main.tanker_addr_item_layout.view.*

class AddressesAdapter(private val addressClickListner: AddressClickListner): RecyclerView.Adapter<AddressesAdapter.AddressesViewHolder>() {
    class AddressesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    var addressList : List<Addresses> = listOf()
    fun setAddress(addrList: List<Addresses>){
        addressList = addrList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressesViewHolder {
        val View = LayoutInflater.from(parent.context).inflate(R.layout.tanker_addr_item_layout, parent, false)
        return AddressesViewHolder(View)
    }

    override fun onBindViewHolder(holder: AddressesViewHolder, position: Int) {
        val address = addressList[position]

        holder.itemView.tvAddr.text = address.address.toString()
        holder.itemView.tvPrice.text = "Rs. "+ address.price.toString()

        holder.itemView.btnEditAddressAddr.setOnClickListener {
           addressClickListner.onAddressItemClickListner(it, address)
        }
        holder.itemView.btnDeleteAddressAddr.setOnClickListener {
           addressClickListner.onAddressItemClickListner(it, address)
        }
    }

    override fun getItemCount(): Int {
        return addressList.size
    }
}