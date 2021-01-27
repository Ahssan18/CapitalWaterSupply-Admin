package com.mazy.capitalwatersupplyadmin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.FirebaseDatabase
import com.mazy.capitalwatersupplyadmin.R
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.notContains
import kotlinx.android.synthetic.main.fragment_add_number.*

class AddNumberFragment : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAddNumber.setOnClickListener {
            if(checkValidation()){
                AddNumberProgressDialog.visibility = View.VISIBLE
                val number = edtAddNumber.text.toString()
                uploadNumber(number)
            }
        }
    }

    private fun uploadNumber(number: String) {
        val dbNumberRef = FirebaseDatabase.getInstance().getReference("AdminNumber")
        dbNumberRef.child("number").setValue(number).addOnCompleteListener {
            if(it.isSuccessful){
                AddNumberProgressDialog.visibility = View.GONE
                Toast.makeText(requireContext(), "Number Added!", Toast.LENGTH_SHORT).show()
                dismiss()
            }else{
                AddNumberProgressDialog.visibility = View.GONE
                Toast.makeText(requireContext(), "Something went wrong please try again later!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private  fun checkValidation():Boolean{
        if(edtAddNumber.nonEmpty()){
            if(edtAddNumber.notContains(" ")){
                if(edtAddNumber.text.length == 11){
                    return true
                }else{
                    edtAddNumber.error = "Please enter valid number!"
                    edtAddNumber.requestFocus()
                }
            }else{
                edtAddNumber.error = "Spaces are not allowed!"
                edtAddNumber.requestFocus()
            }
        }else{
            edtAddNumber.error = "Number Required!"
            edtAddNumber.requestFocus()
        }
        return false
    }
}