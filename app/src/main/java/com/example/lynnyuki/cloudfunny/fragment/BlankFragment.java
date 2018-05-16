package com.example.lynnyuki.cloudfunny.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.lynnyuki.cloudfunny.adapter.MyAdapter;
import com.example.lynnyuki.cloudfunny.base.BaseView;
import com.example.lynnyuki.cloudfunny.fragment.BaseFragment;
import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.util.AppNetWorkUtil;
import com.example.lynnyuki.cloudfunny.util.LogUtil;
import com.kc.unsplash.Unsplash;
import com.kc.unsplash.api.Order;
import com.kc.unsplash.models.Photo;
import java.lang.Math;
import java.util.Random;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener{
    private final String CLIENT_ID = "b85ff30608ec972c8b81da4ea49580d0b9dc02653af9882bc7468ce3a4a04957";
    private static final String TAG = "BlankFragment";
    MyAdapter myAdapter;
    BaseView baseView;
    Context context=this.getContext();
    Unsplash unsplash = new Unsplash(CLIENT_ID);
    private int position = 0;
    Random random = new Random();
    private  int randomPage = random.nextInt(10);

    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView myRecyclerView;



    public BlankFragment() {
        // Required empty public constructor
    }

    public static String KEY_ARG_POSITION = "KEY_ARG_POSITION";

    // 获取Blankement实例
    public static BlankFragment newInstance(Bundle bundle) {
        BlankFragment fragment = new BlankFragment();
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
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initListener(View view) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        myRecyclerView.setLayoutManager(layoutManager);

        ((SimpleItemAnimator)myRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            int randomPage2 = random.nextInt(10);
            @Override
            public void onRefresh() {
                if(!AppNetWorkUtil.isNetworkConnected(getContext())){
                    Snackbar.make(swipeRefreshLayout, "当前无网络，无法刷新 %>_<% ",Snackbar.LENGTH_LONG).setAction("去设置网络", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //设置网络链接
                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            startActivity(intent);
                        }
                    }).show();
//                    Toast.makeText(getContext(),"当前无网络链接，请检查网络设置。",Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                }else {
            new Thread(new Runnable() {
                @Override
                public void run() {

                        switch (position) {
                        case 0:
                            photos(randomPage2, 15, Order.POPULAR);
                            break;
                        case 1:
                            photos(randomPage2, 15, Order.LATEST);
                            break;
                        case 2:
                            photos(randomPage2, 15, Order.OLDEST);
                            break;
                    }
                }
            }).start();
                      }
            }
        });

            initPhoto();
        }


    /**
     * 初始化数据
     */
    public void initPhoto(){
        switch (position) {
            case 0:
                photos(randomPage, 15, Order.POPULAR);
                break;
            case 1:
                photos(randomPage, 15, Order.LATEST);
                break;
            case 2:
                photos(randomPage, 15, Order.OLDEST);
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
                    if (myAdapter == null) {
                        myAdapter = new MyAdapter(photos, getActivity());
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
public void onLoadMoreRequested() {

    // 防止上拉加载的时候可以下拉刷新
    swipeRefreshLayout.setEnabled(false);
}

@Override
    public void onAattach(Context context){
        super.onAttach(context);
        Log.d(TAG,"onAttach");
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
