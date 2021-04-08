package telegram.free.roomlibraryandroid.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import telegram.free.roomlibraryandroid.Fragments.RoomDatabase.Notes
import telegram.free.roomlibraryandroid.R

class AddNoteFragment : Fragment() {
    private lateinit var viewFrag:View
    private lateinit var btn:Button
    private lateinit var etTitle:EditText
    private lateinit var etDescription:EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewFrag= inflater.inflate(R.layout.fragment_add_note, container, false)
        init()
        clickListener()
        return viewFrag
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Add Notes"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }



    private fun clickListener() {
        btn.setOnClickListener {
            val title=etTitle.text.toString()
            val detail=etDescription.text.toString()
            var notes=Notes(title,detail)

            /*NotesDataBase.buildDatabase(requireContext()).getDao().addNotes(notes);

            var action=AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment()
            Navigation.findNavController(it).navigate(action)*/
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Toast.makeText(context,"Back",Toast.LENGTH_LONG).show()
            }
        }
        return true
    }
    private fun init() {
        etTitle=viewFrag.findViewById(R.id.et_title)
        etDescription=viewFrag.findViewById(R.id.et_description)
        btn=viewFrag.findViewById(R.id.btn_submit)
    }



}