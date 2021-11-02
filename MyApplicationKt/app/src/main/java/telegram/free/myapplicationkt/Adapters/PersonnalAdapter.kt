package telegram.free.myapplicationkt.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.solver.state.State
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView
import telegram.free.myapplicationkt.Activities.MainActivity
import telegram.free.myapplicationkt.Data.Personal
import telegram.free.myapplicationkt.R

class PersonnalAdapter(var ref:Context,var list:List<Personal>) :
    RecyclerView.Adapter<PersonnalAdapter.customview>() {



    class customview(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val name:TextView=itemView.findViewById(R.id.et_name)
         val email:TextView=itemView.findViewById(R.id.et_email)
         val phone:TextView=itemView.findViewById(R.id.et_phone)
         val main: View? =itemView.findViewById(R.id.constraint)
         val del: ImageView =itemView.findViewById(R.id.iv_del)
         val edit: ImageView =itemView.findViewById(R.id.iv_edit)
         val profile: CircleImageView =itemView.findViewById(R.id.imageView2)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): customview {
        var view:View=LayoutInflater.from(ref).inflate(R.layout.custom_data,parent,false)
        return customview(view)
    }

    override fun onBindViewHolder(holder: customview, position: Int) {
        setdata(list.get(position),holder)
        holder.main?.setOnClickListener {
            Toast.makeText(ref,position.toString(),Toast.LENGTH_LONG).show()
        }
        holder.edit.setOnClickListener {
            val edit = Intent (ref, MainActivity::class.java)
            edit.putExtra("name",list.get(position).name)
            edit.putExtra("email",list.get(position).email)
            edit.putExtra("phone",list.get(position).phone)
            edit.putExtra("id",list.get(position).id)
            edit.putExtra("password",list.get(position).id)
            ref.startActivity(edit)

        }
        holder.del.setOnClickListener {
            FirebaseFirestore.getInstance().collection("user").document(list.get(position).id)
                .delete().addOnSuccessListener {
                Toast.makeText(ref, "Delete Successfully", Toast.LENGTH_LONG).show()
                notifyDataSetChanged()
            }.addOnFailureListener {
                Toast.makeText(ref, "Failed to Delete!", Toast.LENGTH_LONG).show()

            }
        }

    }

    private fun setdata(get: Personal, holder: customview) {
        holder.name.text=get.name
        holder.email.text=get.email
        holder.phone.text=get.phone
        Glide.with(ref).load(get.imgurl).into(holder.profile)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}