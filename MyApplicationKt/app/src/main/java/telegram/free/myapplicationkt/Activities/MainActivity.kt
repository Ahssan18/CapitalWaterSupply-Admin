package telegram.free.myapplicationkt.Activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import telegram.free.myapplicationkt.Data.Personal
import telegram.free.myapplicationkt.R
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var name: EditText
    private lateinit var phone: EditText
    private lateinit var password: EditText
    private lateinit var submit: Button
    private lateinit var msg: String
    lateinit var db: DocumentReference
    lateinit var userid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeWidgets()
        clickListener()
    }

    private fun clickListener() {
        submit.setOnClickListener {
            if (userid.isEmpty()) {
                userid = UUID.randomUUID().toString()
                password.visibility = View.VISIBLE
                msg = "Data Added Successfully"

            } else {
                msg = "Edited Successfully"

            }
            var data = Personal(
                userid,
                name = name.text.toString(),
                email = email.text.toString(),
                phone = phone.text.toString(),
                password.text.toString()
            )
            db = FirebaseFirestore.getInstance().collection("user").document(userid)
            db.set(data).addOnSuccessListener {
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            }.addOnFailureListener({
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            })
        }
    }

    private fun initializeWidgets() {
        FirebaseApp.initializeApp(this)
        email = findViewById(R.id.etemail)
        name = findViewById(R.id.etname)
        phone = findViewById(R.id.editTextPhone)
        password = findViewById(R.id.editTextTextPassword)
        submit = findViewById(R.id.button)
        try {
            name.setText(intent.getStringExtra("name"))
            email.setText(intent.getStringExtra("email"))
            phone.setText(intent.getStringExtra("phone"))
            password.setText(intent.getStringExtra("password"))
            password.visibility = View.INVISIBLE
            userid = intent.getStringExtra("id").toString()
            submit.setText("Update")
        } catch (e: Exception) {
        }
    }
}