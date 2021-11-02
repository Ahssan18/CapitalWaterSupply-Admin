package telegram.free.myapplicationkt.Activities

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import telegram.free.myapplicationkt.Adapters.PersonnalAdapter
import telegram.free.myapplicationkt.Data.Personal
import telegram.free.myapplicationkt.R


class DataActivity : AppCompatActivity() {
    lateinit var recyclerView:RecyclerView
    lateinit var adapter:PersonnalAdapter
    lateinit var list:MutableList<Personal>
    lateinit var db:FirebaseFirestore
    val TAG="DataActivity"
    lateinit var progress:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        initview();
        getData()
    }

    private fun getData() {
        progress.show()
        FirebaseFirestore.getInstance().collection("user").get()
            .addOnSuccessListener {
                for(document in it)
                {

                   var person=document.toObject(Personal::class.java)
                   list.add(person)
                }
                progress.dismiss()
                setAdapter(list)
            }

    }

    private fun setAdapter(list: List<Personal>) {
        adapter= PersonnalAdapter(this,list)
        recyclerView.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        recyclerView.adapter=adapter
        adapter.notifyDataSetChanged()
    }

    private fun initview() {
        list=ArrayList<Personal>()
        progress= ProgressDialog(this)
        progress.setMessage("Loading please wait...")
        db=FirebaseFirestore.getInstance()
        recyclerView=findViewById(R.id.recycelview)
    }
}