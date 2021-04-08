package telegram.free.roomlibraryandroid.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import telegram.free.roomlibraryandroid.R

class HomeFragment : Fragment() {

    private lateinit var button:FloatingActionButton
    private lateinit var v:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       v= inflater.inflate(R.layout.fragment_home, container, false)
        init()
        clickListener()
        return v
    }

    private fun clickListener() {
        button.setOnClickListener {
            val action=HomeFragmentDirections.actionHomeFragmentToAddNoteFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun init() {
        button=v.findViewById(R.id.floatingActionButton)
    }


}