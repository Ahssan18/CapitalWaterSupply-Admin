package telegram.free.roomdatabase.Activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import telegram.free.roomdatabase.Adapter.NotesAdapter
import telegram.free.roomdatabase.Db.Notes
import telegram.free.roomdatabase.Db.NotesDatabase
import telegram.free.roomdatabase.Interface.EditCallback
import telegram.free.roomdatabase.R

class MainActivity : AppCompatActivity(), EditCallback {
    lateinit var etname: EditText
    lateinit var etemail: EditText
    lateinit var submit: Button
    lateinit var recycle: RecyclerView
    lateinit var list: List<Notes>
    lateinit var db: NotesDatabase
     var id:Int = -1
    lateinit var notesAdapter: NotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        getData()
        clickListener()
    }

    private fun getData() {
        list = db.getNotesDao().getNotesData()
        setAdapter(list)


    }

    private fun setAdapter(list: List<Notes>) {
        notesAdapter = NotesAdapter(list, this, this)
        recycle.layoutManager = LinearLayoutManager(this)
        recycle.adapter = notesAdapter
        notesAdapter.notifyDataSetChanged()

    }

    private fun clickListener() {
        submit.setOnClickListener({
            var name: String = etname.text.toString()
            var email: String = etemail.text.toString()
            var note = Notes(name, email)
            note.id=id;
            if (submit.text.equals("Edit")) {
                db.getNotesDao().update(note)
                submit.setText("Submit")
                Toast.makeText(this@MainActivity, "data Edited Successfully", Toast.LENGTH_SHORT).show()
            } else {
                db.getNotesDao().addNones(note)
                list.toMutableList().add(note)
                Toast.makeText(this@MainActivity, "data Save Successfully", Toast.LENGTH_SHORT).show()

            }
            setAdapter(list)
            etname.setText("")
            etemail.setText("")
        })
    }

    private fun init() {
        db = NotesDatabase(this@MainActivity)
        list = ArrayList()
        recycle = findViewById(R.id.recycle_data)
        etname = findViewById(R.id.editTextTextPersonName)
        etemail = findViewById(R.id.editTextTextEmailAddress)
        submit = findViewById(R.id.button)
    }

    override fun edit(notes: Notes) {
        etname.setText(notes.title)
        etemail.setText(notes.content)
        id=notes.id
        submit.setText("Edit")
        Toast.makeText(this@MainActivity, ""+notes.id, Toast.LENGTH_SHORT).show()

    }

}