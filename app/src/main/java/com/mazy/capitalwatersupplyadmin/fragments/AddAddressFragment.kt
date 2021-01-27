package com.mazy.capitalwatersupplyadmin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.FirebaseDatabase
import com.mazy.capitalwatersupplyadmin.R
import com.mazy.capitalwatersupplyadmin.models.Addresses
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import kotlinx.android.synthetic.main.fragment_add_address.*

class AddAddressFragment : DialogFragment() {
    private val dbRef = FirebaseDatabase.getInstance().getReference("Addresses")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAddAddress.setOnClickListener {
            if(checkValidation()){
                AddAddressPD.visibility = View.VISIBLE
               val addr = edtAddAddressName.text.toString()
                val price = edtAddPrice.text.toString()
                val address = Addresses("", addr, price)
                addAddress(address)
            }
        }
    }
    private fun addAddress(address: Addresses){
        address.id = dbRef.push().key
        dbRef.child(address.id!!).setValue(address).addOnCompleteListener {
            if(it.isSuccessful){
                AddAddressPD.visibility = View.GONE
                dismiss()
                Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show()
            }else{
                AddAddressPD.visibility = View.GONE
                Toast.makeText(context, "Something went wrong please try again later!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checkValidation():Boolean{
        if(edtAddAddressName.nonEmpty()){
            if(edtAddPrice.nonEmpty()){
                return true
            }else{
                edtAddPrice.error="Please enter price here!"
                edtAddPrice.requestFocus()
            }
        }else{
            edtAddAddressName.error = "Address Required!"
            edtAddAddressName.requestFocus()
        }
        return false
    }

}