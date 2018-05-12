package com.example.lynnyuki.cloudfunny.view.Unsplash;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.config.GlideApp;
import com.kc.unsplash.models.Photo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageActivity extends AppCompatActivity{
    @BindView(R.id.Photo)
    ImageView imageView;
    Photo photo;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_photo);


        ButterKnife.bind(this);
        photo = getIntent().getParcelableExtra("URLS");
        setTitle(photo.getUser().getName());

        GlideApp.with(context)
                .load(photo.getUrls().getRegular())
                .priority(Priority.LOW)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView);
    }
}
