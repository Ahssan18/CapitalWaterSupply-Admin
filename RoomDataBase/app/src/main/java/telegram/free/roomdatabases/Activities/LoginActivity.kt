package telegram.free.roomdatabases.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import telegram.free.roomdatabases.Db.NotesDatabase
import telegram.free.roomdatabases.Db.User
import telegram.free.roomdatabases.Utils.PrefrenceManager
import telegram.free.roomdatabases.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        clickListener()
        setContentView(binding.root)
    }

    private fun clickListener() {
        binding.btnLogin.setOnClickListener(this)
        binding.tvSignup.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var id = v!!.id
        when (id) {
            binding.btnLogin.id -> {
                var email = binding.etemail.text.toString()
                var pass = binding.etPassword.text.toString()
                if (!email.equals("")) {
                    if (!pass.equals("")) {
                        val db = NotesDatabase.invoke(this)
                        var list: List<User> = ArrayList<User>();
                        list = db.getNotesDao().loginUser(email, pass)
                        if (list.size > 0) {
                            val jsonString = Gson().toJson(list.get(0))
                            var p=PrefrenceManager(this)
                            p.SaveLogindata(jsonString)
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                            Toast.makeText(this, "Login Successfull !", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Wrong Cradentials !", Toast.LENGTH_LONG).show()
                        }

                    } else {
                        binding.etemail.setError("Enter you password !")

                    }
                } else {
                    binding.etemail.setError("Enter your email !")
                }

            }
            binding.tvSignup.id -> {
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
            }
        }
    }
}