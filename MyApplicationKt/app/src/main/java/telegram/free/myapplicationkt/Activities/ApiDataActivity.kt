package telegram.free.myapplicationkt.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.newFixedThreadPoolContext
import retrofit2.Call
import retrofit2.Callback
import telegram.free.myapplicationkt.Adapters.ApiAdapter
import telegram.free.myapplicationkt.ApiServices
import telegram.free.myapplicationkt.Data.Response
import telegram.free.myapplicationkt.R
import telegram.free.myapplicationkt.Utils.RestClient

class ApiDataActivity : AppCompatActivity() {
    lateinit var recycle:RecyclerView
    lateinit var adapter:ApiAdapter
    lateinit var list:MutableList<Response>
    lateinit var apiservices:ApiServices
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api_data)
        initWidgets()
        getData()
    }

    private fun getData() {
        apiservices.getPosts().enqueue(object :Callback<List<Response>>{
            override fun onResponse(
                call: Call<List<Response>>,
                response: retrofit2.Response<List<Response>>
            ) {
                list= response.body() as MutableList<Response>
                setAdapter(list)
            }

            override fun onFailure(call: Call<List<Response>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun setAdapter(list: MutableList<Response>) {
        adapter= ApiAdapter(this,list)
        recycle.layoutManager=LinearLayoutManager(this)
        recycle.adapter=adapter
        adapter.notifyDataSetChanged()
    }

    private fun initWidgets() {
        recycle=findViewById(R.id.recycle_posts)
        list=ArrayList()
        apiservices=RestClient.getRetrofit()
        val data=apiservices.getPosts()

    }
}