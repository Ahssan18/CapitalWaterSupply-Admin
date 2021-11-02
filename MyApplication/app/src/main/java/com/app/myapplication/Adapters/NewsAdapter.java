package com.app.myapplication.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.myapplication.Models.Article;
import com.app.myapplication.R;
import com.app.myapplication.databinding.CustomLayoutBinding;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.CustomView> {
    private final Context context;
    private final List<Article> articleList;

    public NewsAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public CustomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomView(CustomLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomView holder, int position) {
        holder.binding.tvAuthor.setText(articleList.get(position).getAuthor());
        holder.binding.tvDescription.setText(articleList.get(position).getDescription());
        holder.binding.tvTitle.setText(articleList.get(position).getTitle());
        SimpleDateFormat formatter, FORMATTER;
        formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZ");
        String oldDate = articleList.get(position).getPublishedAt();
        Date date = null;
        try {
            date = formatter.parse(oldDate.substring(0,22) + oldDate.substring(23, 25));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        FORMATTER = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        holder.binding.tvPublichat.setText(FORMATTER.format(date));
        holder.binding.tvSource.setText(articleList.get(position).getSource().getName());
        Picasso.get().load(articleList.get(position).getUrlToImage()).into(holder.binding.ivNews);
        Log.d("urls",articleList.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class CustomView extends RecyclerView.ViewHolder{
private CustomLayoutBinding binding;
        public CustomView(CustomLayoutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
