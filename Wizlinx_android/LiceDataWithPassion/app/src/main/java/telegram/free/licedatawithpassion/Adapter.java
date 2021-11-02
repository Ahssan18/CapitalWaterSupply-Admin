package telegram.free.licedatawithpassion;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import telegram.free.licedatawithpassion.ModelClasses.Posts;
import telegram.free.licedatawithpassion.databinding.CustomPostsBinding;

public class Adapter extends RecyclerView.Adapter<Adapter.PostsHolder> {
    private List<Posts> postsList;
    private Context context;


    public Adapter(List<Posts> postsList, Context context) {
        this.postsList = postsList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomPostsBinding view= CustomPostsBinding.inflate(LayoutInflater.from(context),parent,false);
        return new PostsHolder(view.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull PostsHolder holder, int position) {
        holder.binding.textView.setText(postsList.get(position).getId().toString());
        holder.binding.textView2.setText(postsList.get(position).getUserId().toString());
        holder.binding.textView3.setText(postsList.get(position).getTitle());
        holder.binding.textView4.setText(postsList.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class PostsHolder extends RecyclerView.ViewHolder{
        CustomPostsBinding binding;
        public PostsHolder(@NonNull View itemView) {
            super(itemView);
            binding=CustomPostsBinding.bind(itemView);
        }
    }
}
