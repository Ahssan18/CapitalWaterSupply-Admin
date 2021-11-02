package telegram.free.roomdatabases.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import telegram.free.roomdatabases.Db.Notes
import telegram.free.roomdatabases.Interface.DeleteInterface
import telegram.free.roomdatabases.Interface.EditCallback
import telegram.free.roomdatabases.databinding.CustomNotesBinding

class NotesAdapter(
    var list: MutableList<Notes>,
    var context: Context,
    var callback: EditCallback,
    var del: DeleteInterface
) : RecyclerView.Adapter<NotesAdapter.CustomView>() {

    inner class CustomView(val binding: CustomNotesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView {
        val inflater = LayoutInflater.from(context)
        val binding = CustomNotesBinding.inflate(inflater)
        return CustomView(binding)
    }

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        try {
            holder.binding.tvtitle.setText(list.get(position).title)
            holder.binding.tvdescription.setText(list.get(position).content)
        } catch (e: Exception) {
        }
        holder.binding.delete.setOnClickListener {
            Toast.makeText(context.applicationContext, "Deleted", Toast.LENGTH_LONG).show()
            del.deleteItem(list.get(position))
            notifyDataSetChanged()
        }

        holder.binding.edit.setOnClickListener({
            callback.edit(list.get(position))
        })

    }

    override fun getItemCount(): Int {
        return list.size
    }
}