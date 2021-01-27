package com.mazy.capitalwatersupplyadmin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mazy.capitalwatersupplyadmin.R
import com.mazy.capitalwatersupplyadmin.adapters.TankerAdapter
import com.mazy.capitalwatersupplyadmin.fragments.AddTankerFragment
import com.mazy.capitalwatersupplyadmin.fragments.EditTankerFragment
import com.mazy.capitalwatersupplyadmin.interfaces.TankerClickListner
import com.mazy.capitalwatersupplyadmin.models.Tanker
import kotlinx.android.synthetic.main.activity_manage_addresses.*

class ManageTankersActivity : AppCompatActivity(), TankerClickListner {

    val dbRef = FirebaseDatabase.getInstance().getReference("Tankers")
    private lateinit var adapter: TankerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_addresses)
        val toolbar = supportActionBar
        toolbar?.title = "Tankers"
        toolbar?.setDisplayHomeAsUpEnabled(true)
        addressPB.visibility= View.VISIBLE

        val managere = LinearLayoutManager(this)
        addressesRV.layoutManager = managere
        adapter = TankerAdapter(this)
        addressesRV.adapter = adapter
        getTankers()
//        adapter.setAddress(addresses)
        FAB_AddAddress.setOnClickListener {
            AddTankerFragment().show(supportFragmentManager, " ")
        }
    }

    override fun onAddressItemClickListner(view: View, tanker: Tanker) {
        when(view.id){
            R.id.btnEditAddress -> {
                EditTankerFragment(tanker).show(supportFragmentManager, " ")
            }
            R.id.btnDeleteAddress -> {
                showAlertDialogue(tanker.id!!)
            }
        }
    }
    private fun getTankers(){
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val tankerList : ArrayList<Tanker> = arrayListOf()
                    addressPB.visibility = View.GONE
                    for (tankers in snapshot.children){
                        val tanker  = tankers.getValue(Tanker::class.java)
                        tanker?.let {
                            tankerList.add(it)
                        }
                    }
                    adapter.setAddress(tankerList)
                }else{
                    addressPB.visibility = View.GONE
                    tvNoAddress.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun showAlertDialogue(tankerId:String) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Warning!")
        alertDialog.setMessage("Are you sure you want to delete!")
        alertDialog.setPositiveButton("yes") { dialog, which ->
            deleteTanker(tankerId)
            dialog.dismiss()
        }
        alertDialog.setNegativeButton("no"){ dialogue, which->
            dialogue.dismiss()
        }
        alertDialog.create()
        alertDialog.show()

    }

    private fun deleteTanker(tankerId: String){
        dbRef.child(tankerId).removeValue().addOnCompleteListener {
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