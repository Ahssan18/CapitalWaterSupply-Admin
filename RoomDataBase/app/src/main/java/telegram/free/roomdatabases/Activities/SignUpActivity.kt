package telegram.free.roomdatabases.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import telegram.free.roomdatabases.Db.NotesDatabase
import telegram.free.roomdatabases.Db.User
import telegram.free.roomdatabases.Utils.PrefrenceManager
import telegram.free.roomdatabases.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() , View.OnClickListener {
    lateinit var signUpBinding:ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding= ActivitySignUpBinding.inflate(layoutInflater)
        clickListener()
        setContentView(signUpBinding.root)

    }

    override fun onStart() {
        var p=PrefrenceManager(this)
        if(!p.getLoginData().equals(""))
        {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        super.onStart()
    }

    private fun clickListener() {
        signUpBinding.tvLogin.setOnClickListener(this)
        signUpBinding.btnSignup.setOnClickListener {
            val name: String
            val email: String
            val password: String
            name = signUpBinding.etname.text.toString()
            email = signUpBinding.etemail.text.toString()
            password = signUpBinding.etPassword.text.toString()
            if (!name.equals("")) {
                if (!email.equals("")) {
                    if (!password.equals("")) {
                        val user = User(name, email, password)
                        val db = NotesDatabase.invoke(this)
                        db.getNotesDao().signUP(user)
                        setEmpty()
                        Toast.makeText(this, "SignUp Successfully!", Toast.LENGTH_LONG).show()
                    } else {
                        signUpBinding.etPassword.setError("Password is empty!")
                    }
                } else {
                    signUpBinding.etemail.setError("Email is empty!")
                }
            } else {
                signUpBinding.etname.setError("Name is empty!")
            }
        }
    }

    private fun setEmpty() {
        signUpBinding.etname.setText("")
       signUpBinding.etemail.setText("")
        signUpBinding.etPassword.setText("")
    }

    override fun onClick(v: View?) {
        var id:Int=v!!.id
        when(id)
        {
            signUpBinding.tvLogin.id ->
            {
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }


        }
    }
}