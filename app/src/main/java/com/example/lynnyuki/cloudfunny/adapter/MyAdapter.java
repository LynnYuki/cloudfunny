package com.example.lynnyuki.cloudfunny.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.kc.unsplash.models.Photo;
import com.bumptech.glide.Priority;
import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.config.GlideApp;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lynnyuki.cloudfunny.view.Unsplash.ImageActivity;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>implements View.OnClickListener{
    private final List<Photo> photoList;
    private Context mContext;

    public MyAdapter(List<Photo> photos,Context context) {
        photoList = photos;
        mContext = context;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Photo photo = photoList.get(position);
        GlideApp.with(mContext)
                .load(photo.getUrls().getRegular())
                .priority(Priority.LOW)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ImageActivity.class);
                intent.putExtra("URLS", photo);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView imageView;

        public ViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.rvPhoto);
        }
    }
}
