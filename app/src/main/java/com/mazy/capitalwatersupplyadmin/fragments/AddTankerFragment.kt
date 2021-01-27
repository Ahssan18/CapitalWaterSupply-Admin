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
import kotlinx.android.synthetic.main.fragment_add_addredd.*
import kotlinx.android.synthetic.main.fragment_add_addredd.AddAddressProgressDialog

class AddTankerFragment : DialogFragment(){

    val dbRef = FirebaseDatabase.getInstance().getReference("Tankers")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_addredd, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAddTanker.setOnClickListener {
            if (checkValidation()){
                AddAddressProgressDialog.visibility = View.VISIBLE
                val name = edtAddTankerName.text.toString()
                val type = edtAddTankerType.text.toString()
//                val price = edtAddTankerPrice.text.toString()
//                val addr = edtAddAddress.text.toString()
                val address = Tanker(" ", name, type)
                addAddress(address)
            }
        }
    }


    private fun addAddress(tanker: Tanker){
        tanker.id = dbRef.push().key
        dbRef.child(tanker.id!!).setValue(tanker).addOnCompleteListener {
            if(it.isSuccessful){
                AddAddressProgressDialog.visibility = View.GONE
                dismiss()
                Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show()
            }else{
                AddAddressProgressDialog.visibility = View.GONE
                Toast.makeText(context, "Something went wrong please try again later!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checkValidation():Boolean{
        if(edtAddTankerName.nonEmpty()){
//            if(edtAddTankerPrice.nonEmpty()){
//                if(edtAddAddress.nonEmpty()){
                    return true
//                }else{
//                    edtAddAddress.error = "Address Required!"
//                    edtAddAddress.requestFocus()
//                }
//            }else{
//                edtAddTankerPrice.error = "Tanker Price Required!"
//                edtAddTankerPrice.requestFocus()
//            }
        }else{
            edtAddTankerName.error = "Tanker Name Required!"
            edtAddTankerName.requestFocus()
        }
        return false
    }

}