package com.mazy.capitalwatersupplyadmin.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mazy.capitalwatersupplyadmin.R
import com.mazy.capitalwatersupplyadmin.adapters.AddressesAdapter
import com.mazy.capitalwatersupplyadmin.fragments.AddAddressFragment
import com.mazy.capitalwatersupplyadmin.fragments.EditAddressFragment
import com.mazy.capitalwatersupplyadmin.interfaces.AddressClickListner
import com.mazy.capitalwatersupplyadmin.models.Addresses
import kotlinx.android.synthetic.main.activity_addresses.*

class AddressesActivity : AppCompatActivity(), AddressClickListner {

    private val dbRef = FirebaseDatabase.getInstance().getReference("Addresses")
    private lateinit var adapter: AddressesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addresses)
        val toolbar = supportActionBar
        toolbar?.title = "Addresses"
        toolbar?.setDisplayHomeAsUpEnabled(true)

        addressAddrPB.visibility = View.VISIBLE
        val manager = LinearLayoutManager(this)
        addressesAddrRV.layoutManager = manager
        adapter = AddressesAdapter(this)
        addressesAddrRV.adapter = adapter
        getAddresses()

        FAB_Address.setOnClickListener {
            AddAddressFragment().show(supportFragmentManager, " ")
        }
    }

    override fun onAddressItemClickListner(view: View, address: Addresses) {
        when(view.id){
            R.id.btnEditAddressAddr->{
                EditAddressFragment(address).show(supportFragmentManager, " ")
            }
            R.id.btnDeleteAddressAddr->{
                showAlertDialogue(address.id!!)
            }
        }
    }
    private fun getAddresses(){
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val addressList : ArrayList<Addresses> = arrayListOf()
                    addressAddrPB.visibility = View.GONE
                    for (address in snapshot.children){
                        val addr  = address.getValue(Addresses::class.java)
                        addr?.let {
                            addressList.add(it)
                        }
                    }
                    adapter.setAddress(addressList)
                }else{
                    addressAddrPB.visibility = View.GONE
                    tvNoAddress.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun showAlertDialogue(addressId:String) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Warning!")
        alertDialog.setMessage("Are you sure you want to delete!")
        alertDialog.setPositiveButton("yes") { dialog, which ->
            deleteAddress(addressId)
            dialog.dismiss()
        }
        alertDialog.setNegativeButton("no"){ dialogue, which->
            dialogue.dismiss()
        }
        alertDialog.create()
        alertDialog.show()

    }

    private fun deleteAddress(addressId: String){
        dbRef.child(addressId).removeValue().addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(baseContext, "Deleted Successfully!", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(baseContext, "Something went wrong please try again later!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        this.finish()
        return super.onSupportNavigateUp()
    }
}