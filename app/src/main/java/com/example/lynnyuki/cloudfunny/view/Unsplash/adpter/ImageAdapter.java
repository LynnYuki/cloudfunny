package com.example.lynnyuki.cloudfunny.view.Unsplash.adpter;
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

/**
 * 图片加载适配器
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>implements View.OnClickListener{
    private List<Photo> photoList;
    private Context mContext;

    public ImageAdapter(List<Photo> photos, Context context) {
        photoList = photos;
        mContext = context;

    }

    public void updateList(List<Photo> newList) {
        if (newList.size() != photoList.size()
                || !newList.containsAll(photoList)
                || !photoList.containsAll(newList)) {
            photoList.clear();
            photoList.addAll(newList);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        final Photo photo = photoList.get(position);
        GlideApp.with(mContext)
                .load(photo.getUrls().getRegular())
                .priority(Priority.LOW)
                .fitCenter()
                .error(R.drawable.ic_empty0)
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
        return photoList == null ? 0 : photoList.size();

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
