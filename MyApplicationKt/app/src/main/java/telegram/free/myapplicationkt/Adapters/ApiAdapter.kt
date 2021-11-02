package telegram.free.myapplicationkt.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import telegram.free.myapplicationkt.Data.Response
import telegram.free.myapplicationkt.R

class ApiAdapter(var context:Context,var list:List<Response>) :
    RecyclerView.Adapter<ApiAdapter.CustomView2>() {
    class CustomView2(itemview:View) :RecyclerView.ViewHolder(itemview)
    {
        var id:TextView=itemview.findViewById(R.id.tv_id)
        var title:TextView=itemview.findViewById(R.id.tv_title)
        var body:TextView=itemview.findViewById(R.id.tv_body)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView2 {
        var view=LayoutInflater.from(context).inflate(R.layout.custom_data2,parent,false)
        return CustomView2(view)
    }

    override fun onBindViewHolder(holder: CustomView2, position: Int) {
       setData(holder,list.get(position))
    }

    private fun setData(holder:CustomView2, get: Response) {
        holder.id.text=get.id.toString()
        holder.title.text=get.title.toString()
        holder.body.text=get.body.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}