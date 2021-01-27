package com.mazy.capitalwatersupplyadmin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.FirebaseDatabase
import com.mazy.capitalwatersupplyadmin.R
import com.mazy.capitalwatersupplyadmin.models.Tanker
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import kotlinx.android.synthetic.main.fragment_edit_address.*

class EditTankerFragment(var tanker: Tanker) :DialogFragment() {

    val dbRef = FirebaseDatabase.getInstance().getReference("Tankers")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edtEditTankerName.setText(tanker.tankerName)
        if(tanker.tankerType != null || tanker.tankerType != " "){
            edtEditTankerType.setText(tanker.tankerType)
        }
//        edtEditTankerPrice.setText(tanker.tankerPrice)
//        edtEditAddress.setText(tanker.address)

        btnEditTanker.setOnClickListener {
            if(checkValidation()){
                EditAddressProgressDialog.visibility = View.VISIBLE
                val tName = edtEditTankerName.text.toString()
                val tType = edtEditTankerType.text.toString()
//                val tPrice = edtEditTankerPrice.text.toString()
//                val tAddress = edtEditAddress.text.toString()
                val tanker = Tanker(tanker.id, tName, tType)
                updateTanker(tanker)
            }
        }
    }
    private fun checkValidation():Boolean{
        if(edtEditTankerName.nonEmpty()){
//            if(edtEditTankerPrice.nonEmpty()){
//                if(edtEditAddress.nonEmpty()){
                    return true
//                }else{
//                    edtEditAddress.error = "Address Required!"
//                    edtEditAddress.requestFocus()
//                }
//            }else{
//                edtEditTankerPrice.error = "Tanker Price Required!"
//                edtEditTankerPrice.requestFocus()
//            }
        }else{
            edtEditTankerName.error = "Tanker Name Required!"
            edtEditTankerName.requestFocus()
        }
        return false
    }
    private fun updateTanker(tanker: Tanker) {
        dbRef.child(tanker.id!!).setValue(tanker).addOnCompleteListener {
            if (it.isSuccessful) {
                    EditAddressProgressDialog.visibility = View.GONE
                    dismiss()
                Toast.makeText(context, "Edited Successfully!", Toast.LENGTH_SHORT).show()
            }else {
                EditAddressProgressDialog.visibility = View.GONE
                Toast.makeText(context, "Something went wrong please try again later!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}