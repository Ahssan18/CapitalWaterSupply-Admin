package telegram.free.myapplicationkt.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import telegram.free.myapplicationkt.R
import telegram.free.myapplicationkt.Utils.Utilities

class ProfileActivity : AppCompatActivity() {
    lateinit var email: TextView
    private lateinit var verified: TextView
    lateinit var clicktoverfify: TextView
    lateinit var logout: ImageView
    lateinit var delete_account: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initWidget()
        setData()
        clickListener()
    }

    fun deleteAccount() {
        Utilities.showprogress(this, "deleting data...")
        try {
            var user = FirebaseAuth.getInstance().currentUser
            user?.delete()?.addOnSuccessListener {
                Utilities.showToast(this, "User Deleted Successfully")
                var login = Intent(this, LoginActivity::class.java)
                startActivity(login)
                Utilities.hideProgress()
                finish()
            }?.addOnFailureListener {
                Utilities.showToast(this, it.message.toString())
            }
            Log.e("deleted_status", FirebaseAuth.getInstance().currentUser?.delete().toString())

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun clickListener() {
        delete_account.setOnClickListener {
            deleteAccount()
        }
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            var login = Intent(this, LoginActivity::class.java)
            startActivity(login)
            finish()
        }
        clicktoverfify.setOnClickListener {
            FirebaseAuth.getInstance().currentUser?.sendEmailVerification()?.addOnSuccessListener {
                Utilities.showToast(this, "Email send successfully")
            }?.addOnFailureListener {
                Utilities.showToast(this, it.message.toString())
            }
        }
    }

    private fun setData() {
        try {
            var user = FirebaseAuth.getInstance().currentUser
            var emailData = user?.email
            var status = user?.isEmailVerified
            email.setText(emailData)
            verified.setText(status.toString())
            if (!status!!) {
                clicktoverfify.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
        }

    }

    private fun initWidget() {
        delete_account = findViewById(R.id.iv_delete_account)
        logout = findViewById(R.id.iv_logout)
        clicktoverfify = findViewById(R.id.verify)
        email = findViewById(R.id.etemail_profile)
        verified = findViewById(R.id.et_verified_status)
    }
}