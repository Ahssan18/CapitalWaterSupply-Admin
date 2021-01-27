package com.mazy.capitalwatersupplyadmin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mazy.capitalwatersupplyadmin.R
import kotlinx.android.synthetic.main.activity_tankers.*

class TankersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tankers)
        val toolbar = supportActionBar
        toolbar?.title = "Manage Tankers"
        toolbar?.setDisplayHomeAsUpEnabled(true)

        manageAddress.setOnClickListener {
            startActivity((Intent(this, AddressesActivity::class.java)))
        }
        managetanker.setOnClickListener {
            startActivity(Intent(this, ManageTankersActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        this.finish()
        return super.onSupportNavigateUp()
    }

}