package com.akshar.flickrqueryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public abstract class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<String> UrlsList;
    Context context;

    public RecyclerViewAdapter(ArrayList<String> UrlList, Context context) {
        this.UrlsList = UrlList;
        // Log.e("constructor", "reaching here " + UrlList.size());
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        // private final TextView textView;

        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.img_view);
            // textView = (TextView) v.findViewById(R.id.img_txt_view);
        }

        public ImageView getImage() {
            return this.imageView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        // v.setLayoutParams(new RecyclerView.LayoutParams(1000,600));
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("onBindViewHolder", "" + UrlsList.get(position));
        Glide.with(this.context)
                .load(UrlsList.get(position))
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.getImage());

        /*
        if (position >= getItemCount() - 1) {
            loadMore();
        }
         */
    }

    public abstract void loadMore();

    @Override
    public int getItemCount() {
        return UrlsList.size();
    }
}
