package telegram.free.myapplicationkt.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import telegram.free.myapplicationkt.R
import telegram.free.myapplicationkt.Utils.Utilities

class LoginActivity : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var login: Button
    lateinit var signUp: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initWidgets()
        clickListener()
    }

    private fun clickListener() {
        login.setOnClickListener {
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnSuccessListener {
//                    Utilities.showToast(this, it.toString())
                    var intent = Intent(this, DataActivity::class.java)
                    startActivity(intent)
                }.addOnFailureListener {
                Utilities.showToast(this, it.message.toString())
            }
        }
        signUp.setOnClickListener {
            var register = Intent(this, MainActivity::class.java)
            startActivity(register)
        }

    }

    override fun onStart() {
        if(FirebaseAuth.getInstance().currentUser!=null)
        {
            var intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
        super.onStart()
    }
    private fun initWidgets() {
        signUp = findViewById(R.id.tv_signup)
        email = findViewById(R.id.etemail)
        password = findViewById(R.id.editTextTextPassword)
        login = findViewById(R.id.button)
    }
}