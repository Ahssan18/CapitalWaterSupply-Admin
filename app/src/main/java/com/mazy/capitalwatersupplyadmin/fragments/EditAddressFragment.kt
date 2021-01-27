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
import kotlinx.android.synthetic.main.fragment_edit_address2.*

class EditAddressFragment(val address: Addresses) : DialogFragment() {
    private val dbRef = FirebaseDatabase.getInstance().getReference("Addresses")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_address2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edtEditAddressName.setText(address.address)
        edtEditPrice.setText(address.price)
        btnEditAddress.setOnClickListener {
         if(checkValidation()){
             EditAddressPD.visibility= View.VISIBLE
             val addr = edtEditAddressName.text.toString()
             val price = edtEditPrice.text.toString()
             val addres = Addresses(address.id, addr, price)
             updateAddress(addres)
         }
        }
    }
    private fun updateAddress(address: Addresses) {
        dbRef.child(address.id!!).setValue(address).addOnCompleteListener {
            if (it.isSuccessful) {
                EditAddressPD.visibility = View.GONE
                dismiss()
                Toast.makeText(context, "Edited Successfully!", Toast.LENGTH_SHORT).show()
            }else {
                EditAddressPD.visibility = View.GONE
                Toast.makeText(context, "Something went wrong please try again later!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checkValidation():Boolean{
        if(edtEditAddressName.nonEmpty()){
            if(edtEditPrice.nonEmpty()){
                return true
            }else {
                edtEditPrice.error = "Please enter price here!"
                edtEditPrice.requestFocus()
            }
        }else{
            edtEditAddressName.error = "Address Required!"
            edtEditAddressName.requestFocus()
        }
        return false
    }
}