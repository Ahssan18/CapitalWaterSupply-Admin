package com.app.retrofitsampleproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.retrofitsampleproject.Models.Data;
import com.app.retrofitsampleproject.Models.Responce;
import com.app.retrofitsampleproject.databinding.CustomDesignBinding;

import java.util.List;

public class AdapterRecycle extends RecyclerView.Adapter<AdapterRecycle.CustomView> {
    private Context context;
    private List<Data> list;

    public AdapterRecycle(Context context, List<Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CustomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       CustomDesignBinding view= CustomDesignBinding.inflate(LayoutInflater.from(context),parent,false);
        return new CustomView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomView holder, int position) {
        holder.binding.id.setText(list.get(position).getId()+"");
        holder.binding.title.setText(list.get(position).getFirstName());
        holder.binding.body.setText(list.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomView extends RecyclerView.ViewHolder{
        CustomDesignBinding binding;
        public CustomView(CustomDesignBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
