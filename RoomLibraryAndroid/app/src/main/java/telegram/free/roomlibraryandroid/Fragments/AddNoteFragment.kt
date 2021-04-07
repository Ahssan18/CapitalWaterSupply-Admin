package telegram.free.roomlibraryandroid.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import telegram.free.roomlibraryandroid.MainActivity
import telegram.free.roomlibraryandroid.R

class AddNoteFragment : Fragment() {
    lateinit var viewFrag:View
    lateinit var btn:Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewFrag= inflater.inflate(R.layout.fragment_add_note, container, false)
        init()
        clickListener()
        return viewFrag;
    }

    private fun clickListener() {
//        (activity as MainActivity).supportActionBar?.
          var actionbar= activity?.actionBar?.setTitle("Add Notes")

        btn.setOnClickListener({
            var action=AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment()
            Navigation.findNavController(it).navigate(action)
        })
    }

    private fun init() {
        btn=viewFrag.findViewById(R.id.btn_submit)
    }


}