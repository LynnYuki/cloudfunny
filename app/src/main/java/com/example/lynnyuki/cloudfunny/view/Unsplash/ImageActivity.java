package com.example.lynnyuki.cloudfunny.view.Unsplash;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;


import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.base.BaseActivity;
import com.example.lynnyuki.cloudfunny.base.BaseView;
import com.example.lynnyuki.cloudfunny.config.GlideApp;
import com.example.lynnyuki.cloudfunny.util.AppFileUtil;
import com.example.lynnyuki.cloudfunny.util.ImageLoader;
import com.example.lynnyuki.cloudfunny.util.ImgDownLoad;
import com.kc.unsplash.Unsplash;
import com.kc.unsplash.models.Photo;
import com.kc.unsplash.models.Urls;


import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;


public class ImageActivity extends BaseActivity {
    private String TAG = "ImageActivity";
    private String imgUrl;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.Photo)
    ImageView imageView;
    Photo photo;
    Context context = this;//必须初始化否则引起空指针异常

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        hideOrShowToolbar();

//        ButterKnife.bind(this);
        photo = getIntent().getParcelableExtra("URLS");
        setTitle(photo.getUser().getName());
        imgUrl = photo.getUrls().getRegular();
        Log.d(TAG, imgUrl);
        ImageLoader.loadUnsplash(context,imgUrl,imageView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.full_photo;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_imag, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享图片");
                intent.putExtra(Intent.EXTRA_TEXT, imgUrl);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(intent, "分享图片"));
                break;
            case R.id.item_save:
                ImgDownLoad.donwloadImg(this, imgUrl);
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.Photo)
    public void onImgClick() {
        //长按保存图片
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ImgDownLoad.donwloadImg(context, imgUrl);
                return false;
            }
        });
        hideOrShowToolbar();
    }

    private void hideOrShowToolbar() {
        if (Objects.requireNonNull(getSupportActionBar()).isShowing()) {
            getSupportActionBar().hide();
            hideSystemUI();
        } else {
            showSystemUI();
            getSupportActionBar().show();
        }
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

}



