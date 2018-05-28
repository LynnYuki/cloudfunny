package com.example.lynnyuki.cloudfunny.view.Unsplash;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.lynnyuki.cloudfunny.fragment.BaseFragment;
import com.example.lynnyuki.cloudfunny.view.Unsplash.adpter.ImageAdapter;
import com.example.lynnyuki.cloudfunny.base.BaseView;
import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.config.Constants;
import com.example.lynnyuki.cloudfunny.util.AppNetWorkUtil;
import com.kc.unsplash.Unsplash;
import com.kc.unsplash.api.Order;
import com.kc.unsplash.models.Photo;

import java.util.Random;
import java.util.List;

import butterknife.BindView;

/**
 * 图片加载Fragment
 */
public class ImageFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{



    private static final String TAG = "ImageFragment";
    ImageAdapter myAdapter;

    BaseView baseView;

    Context context=this.getContext();

    Unsplash unsplash = new Unsplash(Constants.CLIENT_ID);

    public static String KEY_ARG_POSITION = "KEY_ARG_POSITION";

    private int position = 0;

    Random random = new Random();

    private  int randomPage = random.nextInt(50);


    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView myRecyclerView;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void initialize() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        myRecyclerView.setLayoutManager(layoutManager);
        myAdapter = new ImageAdapter();
        ((SimpleItemAnimator)myRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        initPhoto();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image;
    }

    public ImageFragment() {
        // Required empty public constructor
    }



    // 获取Blankement实例
    public static ImageFragment newInstance(Bundle bundle) {
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        position = args.getInt(KEY_ARG_POSITION);//获取fragement位置
        Log.e(TAG,"创建");

    }

    /**
     * 初始化数据
     */
    public void initPhoto(){
        switch (position) {
            case 0:
                photos(randomPage, 30, Order.POPULAR);
                break;
            case 1:
                photos(randomPage, 30, Order.LATEST);
                break;
            case 2:
                photos(randomPage, 30, Order.OLDEST);
                break;
        }
    }

    /**
     * 获取图片
     * @param f
     * @param l
     * @param order
     */
    private void photos(int f, int l, Order order) {
            unsplash.getPhotos(f, l, order, new Unsplash.OnPhotosLoadedListener() {
                @Override
                public void onComplete(List<Photo> photos) {
                    if ( photos.size()!=0) {
                        myAdapter = new ImageAdapter(photos, getActivity());
                        myRecyclerView.setAdapter(myAdapter);
                    } else {
                        myAdapter.updateList(photos);
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    Log.d(TAG, "图片加载成功！");
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getContext(), "网络连接错误。", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });

    }

@Override
public void onRefresh() {
    final int randomPage2 = random.nextInt(50);
    if(!AppNetWorkUtil.isNetworkConnected(getContext())){
        Snackbar.make(swipeRefreshLayout, "当前无网络，无法刷新 %>_<% ",Snackbar.LENGTH_LONG).setAction("去设置网络", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置网络链接
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        }).show();
        Toast.makeText(getContext(),"当前无网络链接，请检查网络设置。",Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);
    }else {

        new Thread(new Runnable() {
            @Override
            public void run() {

                switch (position) {
                    case 0:
                        photos(randomPage2, 30, Order.POPULAR);
                        break;
                    case 1:
                        photos(randomPage2, 30, Order.LATEST);
                        break;
                    case 2:
                        photos(randomPage2, 30, Order.OLDEST);
                        break;
                }
            }
        }).start();

    }

}
@Override
public void onLoadMoreRequested() {

        // 防止上拉加载的时候可以下拉刷新
        swipeRefreshLayout.setEnabled(false);
    }
@Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"onActivityCreated");

}
@Override
    public void onStart(){
        super.onStart();
    Log.d(TAG,"onStart");

}

@Override
    public void onResume(){
        super.onResume();
    Log.d(TAG,"onResume");
}
@Override
    public void onPause(){
        super.onPause();
    Log.d(TAG,"onPause");
}
@Override
    public void onStop(){
        super.onStop();
    Log.d(TAG,"onStop");
}
@Override
    public void onDestroyView(){
        super.onDestroyView();
    Log.d(TAG,"onDestroyView");
}
@Override
    public void onDestroy(){
        super.onDestroy();
    Log.d(TAG,"onDesTroy");
}
@Override
    public void onDetach(){
        super.onDetach();
    Log.d(TAG,"onDetach");
}



/**
 * TO DO
 * 修复ViewPager切换Fragment出现的白屏
 * 下拉刷新加载图片的闪屏问题
 * E/RecyclerView: No adapter attached; skipping layout
 *
 */
}
