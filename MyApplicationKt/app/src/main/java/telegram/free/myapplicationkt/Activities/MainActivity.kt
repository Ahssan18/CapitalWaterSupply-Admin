package telegram.free.myapplicationkt.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import telegram.free.myapplicationkt.Data.Personal
import telegram.free.myapplicationkt.R
import telegram.free.myapplicationkt.Utils.Utilities
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var name: EditText
    private lateinit var phone: EditText
    private lateinit var password: EditText
    private lateinit var submit: Button
    private lateinit var msg: String
    private lateinit var record:ImageView
    lateinit var db: DocumentReference
    lateinit var userid: String
    lateinit var picker:ImageView
    lateinit var profile:CircleImageView
    lateinit var launcher: ActivityResultLauncher<Intent>
    lateinit var imguri:Uri
    lateinit var imgUrl:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeWidgets()
        clickListener()
    }

    private fun clickListener() {
        picker.setOnClickListener{
            var intent=Intent()
            intent.setType("image/*")
            intent.action=Intent.ACTION_GET_CONTENT
            launcher.launch(intent)

        }
        record.setOnClickListener{
            var record= Intent(this,DataActivity::class.java)
            startActivity(record)
        }
        submit.setOnClickListener {
            Utilities.showprogress(this)
            if (userid.isEmpty()) {
                userid = UUID.randomUUID().toString()
                password.visibility = View.VISIBLE
                msg = "Data Added Successfully"

            } else {
                msg = "Edited Successfully"

            }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnSuccessListener {

                val ref=FirebaseStorage.getInstance().reference.child("img_"+UUID.randomUUID())
                ref.putFile(imguri).addOnSuccessListener {
                    taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            imgUrl=it.toString()
                            UploadImageToFirestorage()
                        }

                }.addOnFailureListener{
                    print(it.message)
                    Utilities.hideProgress()
                    Utilities.showToast(this,it.message.toString())
                }

            }.addOnFailureListener {
                Log.e("Exception",it.message.toString())
                Utilities.hideProgress()
                Utilities.showToast(this,it.message.toString())
            }

        }
    }

    private fun UploadImageToFirestorage() {
        var data = Personal(
            userid,
            name = name.text.toString(),
            email = email.text.toString(),
            phone = phone.text.toString(),
            password.text.toString(),
            imgUrl
        )
        db = FirebaseFirestore.getInstance().collection("user").document(userid)
        db.set(data).addOnSuccessListener {
            Utilities.hideProgress()
            Utilities.showToast(this,msg)
        }.addOnFailureListener({
            Utilities.hideProgress()
            Utilities.showToast(this,it.message.toString())
        })
    }

    private fun initializeWidgets() {
        FirebaseApp.initializeApp(this)
        email = findViewById(R.id.etemail)
        name = findViewById(R.id.etname)
        phone = findViewById(R.id.editTextPhone)
        password = findViewById(R.id.editTextTextPassword)
        submit = findViewById(R.id.button)
        record=findViewById(R.id.data_base)
        profile=findViewById(R.id.imageView)
        picker=findViewById(R.id.ic_picker)
        try {var n=intent.getStringExtra("name")
            Log.d("valofN",n.toString())
            if(n!=null) {
                name.setText(intent.getStringExtra("name"))
                email.setText(intent.getStringExtra("email"))
                phone.setText(intent.getStringExtra("phone"))
                password.setText(intent.getStringExtra("password"))
                password.visibility = View.INVISIBLE
                userid = intent.getStringExtra("id").toString()
                submit.setText("Update")
            }else
            {
                userid=""
                submit.setText("Submit")
                password.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()


        }
        launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode== RESULT_OK)
            {
                imguri= it.data?.data!!
                profile.setImageURI(imguri)
            }
        }
    }
}