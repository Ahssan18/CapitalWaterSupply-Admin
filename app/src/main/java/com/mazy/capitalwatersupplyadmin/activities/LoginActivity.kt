package com.mazy.capitalwatersupplyadmin.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.mazy.capitalwatersupplyadmin.R
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.notContains
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val toolbar= supportActionBar
        toolbar?.title = "Login"
        auth = FirebaseAuth.getInstance()
        btnLogin.setOnClickListener {
            if(checkValidation()){
                if(checkInternetConnection()) {
                    loginProgress.visibility = View.VISIBLE
                    val email = edtEmail.text.toString()
                    val password = edtPassword.text.toString()
                    signIn(email, password)
                }else{
                    showConnectionAlertDialogue()
                }
            }
        }
        tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    private fun checkValidation():Boolean{
        if(edtEmail.nonEmpty()){
            if(edtEmail.notContains(" ")){
                if(edtEmail.validEmail()){
                    if(edtPassword.nonEmpty()){
                        if(edtPassword.notContains(" ")){
                            return  true
                        }else{
                            edtPassword.error = "Spaces are not allowed!!"
                            edtPassword.requestFocus()
                        }
                    }else{
                        edtPassword.error = "Password Required!"
                        edtPassword.requestFocus()
                    }
                }else{
                    edtEmail.error = "Please enter valid email!"
                    edtEmail.requestFocus()
                }
            }else{
                edtEmail.error = "Spaces are not allowed!"
                edtEmail.requestFocus()
            }
        }else{
            edtEmail.error = "Email Required!"
            edtEmail.requestFocus()
        }
        return false
    }

    private fun signIn(email: String, password:String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                loginProgress.visibility = View.INVISIBLE
                val intent= Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                this.finish()
            }else{
                loginProgress.visibility = View.INVISIBLE
                Toast.makeText(this, "Invalid email or password!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkInternetConnection(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
    private fun showConnectionAlertDialogue() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Error!")
        alertDialog.setMessage("Something went wrong with your internet check your internet connection and try again!")
        alertDialog.setPositiveButton("ok") { dialog, which ->
            dialog.dismiss()
        }
        alertDialog.create()
        alertDialog.show()

    }
}
