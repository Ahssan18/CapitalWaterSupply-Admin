package com.mazy.capitalwatersupplyadmin.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mazy.capitalwatersupplyadmin.R
import com.mazy.capitalwatersupplyadmin.adapters.OrdersAdapter
import com.mazy.capitalwatersupplyadmin.fragments.AddNumberFragment
import com.mazy.capitalwatersupplyadmin.interfaces.OrderClickListner
import com.mazy.capitalwatersupplyadmin.models.Order
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OrderClickListner {

    private  lateinit var adapter: OrdersAdapter
    private val dbOrderRef = FirebaseDatabase.getInstance().getReference("AdminOrders")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar= supportActionBar
        toolbar?.title="Home"

        OrdersPB.visibility = View.VISIBLE
        val manager  = LinearLayoutManager(this)
        ordersRV.layoutManager = manager
        adapter = OrdersAdapter(this)
        ordersRV.adapter = adapter
        getOrders()
    }



    private fun getOrders(){
        dbOrderRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    OrdersPB.visibility = View.GONE
                    val orderList :  ArrayList<Order> = arrayListOf()
                    for(orders in snapshot.children){
                        val order = orders.getValue(Order::class.java)
                        order?.let{
                            orderList.add(it)
                        }
                    }
                    adapter.setOrders(orderList)
                }else{
                    OrdersPB.visibility = View.GONE
                    noOrderFount.visibility = View.VISIBLE
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.manageTankers->{
               startActivity(Intent(this, TankersActivity::class.java))
            }
            R.id.logout->{
                showAlertDialogue()
            }
            R.id.addNumber->{
                AddNumberFragment().show(supportFragmentManager, " ")
            }
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu?.findItem(R.id.manageTankers)
        return true
        }
    private fun showAlertDialogue() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Warning!")
        alertDialog.setMessage("Are you sure you want to delete!")
        alertDialog.setPositiveButton("yes") { dialog, which ->
            FirebaseAuth.getInstance().signOut()
            dialog.dismiss()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            this.finish()

        }
        alertDialog.setNegativeButton("no"){ dialogue, which->
            dialogue.dismiss()
        }
        alertDialog.create()
        alertDialog.show()

    }

    override fun onOrderItemClickListner(view: View, order: Order) {
        when(view.id){
            R.id.btnOrderAccept -> {
                OrdersPB.visibility = View.VISIBLE
                val dbCRef = FirebaseDatabase.getInstance().getReference("Orders")
                val acceptStatus = HashMap<String, Any>()
                acceptStatus["status"] = "Accepted"
                dbOrderRef.child(order.id!!).updateChildren(acceptStatus).addOnCompleteListener {
                    if (it.isSuccessful) {
                        dbCRef.child(order.userId!!).child(order.id!!).updateChildren(acceptStatus)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    OrdersPB.visibility = View.GONE
                                    Toast.makeText(this, "Order Accepted", Toast.LENGTH_SHORT).show()
                                } else {
                                    OrdersPB.visibility = View.GONE
                                    Toast.makeText(this, "Something went wrong please try again later!", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        OrdersPB.visibility = View.GONE
                        Toast.makeText(this, "Something went wrong please try again later!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            R.id.btnOrderCancel->{
                OrdersPB.visibility = View.VISIBLE
                val dbCRef = FirebaseDatabase.getInstance().getReference("Orders")
                val acceptStatus = HashMap<String, Any>()
                acceptStatus["status"] = "Canceled"
                dbOrderRef.child(order.id!!).updateChildren(acceptStatus).addOnCompleteListener {
                    if (it.isSuccessful) {
                        dbCRef.child(order.userId!!).child(order.id!!).updateChildren(acceptStatus)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    OrdersPB.visibility = View.GONE
                                    Toast.makeText(this, "Order Canceled", Toast.LENGTH_SHORT).show()
                                } else {
                                    OrdersPB.visibility = View.GONE
                                    Toast.makeText(this, "Something went wrong please try again later!", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        OrdersPB.visibility = View.GONE
                        Toast.makeText(this, "Something went wrong please try again later!", Toast.LENGTH_SHORT).show()
                    }
                }

            }
            R.id.btnOrderCall->{
                if(checkPermission()){
                    callPhone(order.number!!)
                }
            }
        }
    }
    fun callPhone(number:String){
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number))
        startActivity(intent)
    }
    private fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.CALL_PHONE
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.CALL_PHONE
                )
            ) {
                requestPermission()

            } else {
                requestPermission()

            }
            return false
        }
        return true
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.CALL_PHONE),
            PERMISSION_REQUEST_CODE
        )

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if (ContextCompat.checkSelfPermission(
                            this, android.Manifest.permission.CALL_PHONE
                        ) ==
                        PackageManager.PERMISSION_GRANTED
                    ) {

                    }
                } else {
//                    requestPermission()
//                    ActivityCompat.requestPermissions(
//                        this,
//                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                        PERMISSION_REQUEST_CODE
//                    )
                }
                return
            }
        }
    }
    companion object{
       const val PERMISSION_REQUEST_CODE = 100
    }

}