package telegram.free.roomdatabases.Activities

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import telegram.free.roomdatabases.Adapter.NotesAdapter
import telegram.free.roomdatabases.Db.Notes
import telegram.free.roomdatabases.Db.NotesDatabase
import telegram.free.roomdatabases.Db.User
import telegram.free.roomdatabases.Interface.DeleteInterface
import telegram.free.roomdatabases.Interface.EditCallback
import telegram.free.roomdatabases.R
import telegram.free.roomdatabases.Utils.PrefrenceManager
import telegram.free.roomdatabases.ViewModels.NotesViewModel
import telegram.free.roomdatabases.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), EditCallback, DeleteInterface {
    lateinit var etname: EditText
    lateinit var etemail: EditText
    lateinit var submit: Button
    lateinit var recycle: RecyclerView
    lateinit var list: MutableList<Notes>
    lateinit var db: NotesDatabase
    lateinit var notesViewModel: NotesViewModel
    var id: Int = -1
    lateinit var notesAdapter: NotesAdapter
    lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        init()
        fetchRecord()
        clickListener()
    }


    private fun fetchRecord() {
        notesViewModel.getAllData().observe(this, Observer {
            setAdapter(it)
        })
    }

    private fun setAdapter(list: MutableList<Notes>) {
        notesAdapter = NotesAdapter(list, this, this, this)
        recycle.layoutManager = LinearLayoutManager(this)
        recycle.adapter = notesAdapter
        notesAdapter.notifyDataSetChanged()

    }

    private fun clickListener() {
        mainBinding.toolbar.logout.setOnClickListener {
            var p = PrefrenceManager(this)
            p.editor.clear().commit()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        submit.setOnClickListener {
            var name: String = etname.text.toString()
            var email: String = etemail.text.toString()
            var gson = Gson()
            var dataString = PrefrenceManager(this).getLoginData();
            var data: User = gson.fromJson(dataString, User::class.java)
            var note = Notes(name, email, data.userid.toLong())
            if (submit.text.equals("Edit")) {
                note.id = id;
                notesViewModel.editNotes(note)
                submit.setText("Submit")
                Toast.makeText(this@MainActivity, "data Edited Successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                notesViewModel.insertData(note)
                Toast.makeText(this@MainActivity, "data Save Successfully", Toast.LENGTH_SHORT)
                    .show()

            }
            etname.setText("")
            etemail.setText("")
        }
    }

    private fun init() {
        supportActionBar?.hide()
        notesViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(NotesViewModel::class.java)
        db = NotesDatabase(this@MainActivity)
        recycle = findViewById(R.id.recycle_data)
        etname = findViewById(R.id.editTextTextPersonName)
        etemail = findViewById(R.id.editTextTextEmailAddress)
        submit = findViewById(R.id.button)
    }

    override fun edit(notes: Notes) {
        etname.setText(notes.title)
        etemail.setText(notes.content)
        id = notes.id
        submit.setText("Edit")

    }

    override fun deleteItem(notes: Notes) {
        notesViewModel.deleteNotes(notes)
    }


}