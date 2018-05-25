package com.example.lynnyuki.cloudfunny.view.Eyepetizer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.base.BaseMVPFragment;
import com.example.lynnyuki.cloudfunny.config.CloudFunnyApplication;
import com.example.lynnyuki.cloudfunny.config.Constants;
import com.example.lynnyuki.cloudfunny.contract.EyepetizerContract;
import com.example.lynnyuki.cloudfunny.dagger.component.DaggerEyepetizerHotFragmentComponent;
import com.example.lynnyuki.cloudfunny.dagger.module.EyepetizerHotFragmentModule;
import com.example.lynnyuki.cloudfunny.model.bean.VideoBean;
import com.example.lynnyuki.cloudfunny.presenter.EyepetizerPresenter;
import com.example.lynnyuki.cloudfunny.view.Eyepetizer.adapter.EyepetizerAdapter;

import butterknife.BindView;

/**
 * 热门视频排行子Fragment
 */
public class EyepetizerHotFragment extends BaseMVPFragment<EyepetizerPresenter> implements EyepetizerContract.View,BaseQuickAdapter.RequestLoadMoreListener,BaseQuickAdapter.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener{

    public static final String TAG = "EypetizerHotFragment";
    public static String KEY_ARG_POSITION = "KEY_ARG_POSITION";

    private int position = 0;
    private int page = 1;

    private EyepetizerAdapter dailyAdapter;

    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerview_eyepetizer)
    RecyclerView myRecyclerView;

    @Override
    protected void initInject() {
        DaggerEyepetizerHotFragmentComponent
                .builder()
                .appComponent(CloudFunnyApplication.getAppComponent())
                .eyepetizerHotFragmentModule(new EyepetizerHotFragmentModule())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_eypetizer_hot;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        position = args.getInt(KEY_ARG_POSITION);//获取fragement位置
        Log.e(TAG,"创建");
    }
    public EyepetizerHotFragment(){

    }
    @SuppressLint("ResourceAsColor")
    @Override
    protected void initialize() {
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);

        mPresenter.getVideoData(page, Constants.EYEPETIZER_UDID,"weekly", "256", "XXX");
        dailyAdapter = new EyepetizerAdapter();
        dailyAdapter.setOnItemClickListener(this);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        myRecyclerView.setAdapter(dailyAdapter);
//        dailyAdapter.setOnLoadMoreListener(this, myRecyclerView);

        initData();

    }

    public static EyepetizerHotFragment newInstance(Bundle bundle) {
        EyepetizerHotFragment fragment = new EyepetizerHotFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        VideoBean.ItemListBean bean = (VideoBean.ItemListBean) adapter.getData().get(position);
        VideoBean.ItemListBean.DataBeanX beanX = bean.getData();
        Intent intent = new Intent(mContext, EyepetizerDetailActivity.class);
        intent.putExtra("data", beanX);
        mContext.startActivity(intent);
    }




    public void initData(){
        switch (position){
            case 0:
                mPresenter.getVideoData(page, Constants.EYEPETIZER_UDID,"weekly", "256", "XXX");
                break;
            case 1:
                mPresenter.getVideoData(page, Constants.EYEPETIZER_UDID,"monthly", "256", "XXX");
                break;
            case 2:
                mPresenter.getVideoData(page, Constants.EYEPETIZER_UDID,"historical", "256", "XXX");
                break;
        }

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        page = 1;
        new Thread(new Runnable() {
            @Override
            public void run() {

                switch (position) {
                    case 0:
                        mPresenter.getVideoData(page, Constants.EYEPETIZER_UDID,"weekly", "256", "XXX");
                        break;
                    case 1:
                        mPresenter.getVideoData(page, Constants.EYEPETIZER_UDID,"monthly", "256", "XXX");
                        break;
                    case 2:
                        mPresenter.getVideoData(page, Constants.EYEPETIZER_UDID,"historical", "256", "XXX");
                        break;
                }
            }
        }).start();
        dailyAdapter.setEnableLoadMore(false);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMoreRequested() {
        page++;
        swipeRefreshLayout.setEnabled(false);

    }

    @Override
    public void showDailyVideoData(VideoBean dailyBean) {
    }

    @Override
    public void failGetDailyData() {

    }

    @Override
    public void showHotVideoData(VideoBean hotBean) {
        if (null != swipeRefreshLayout && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            // 下拉刷新后可以上拉加载
            dailyAdapter.setEnableLoadMore(true);
        }
        if (null != dailyAdapter && dailyAdapter.isLoading()) {
            // 上拉加载后可以下拉刷新
            swipeRefreshLayout.setEnabled(true);
        }
        if (page == 1) {
            dailyAdapter.setNewData(hotBean.getItemList());
        } else {
            dailyAdapter.addData(hotBean.getItemList());
        }
        if (hotBean.getItemList() != null) {
            dailyAdapter.loadMoreComplete();
            Log.e(TAG,"继续加载数据");
        } else if (hotBean.getItemList().size() == 0 || hotBean.getItemList() == null) {
            dailyAdapter.loadMoreEnd();
            Log.e(TAG,"无更多数据了");
        }
    }

    @Override
    public void failGetHotData() {
        dailyAdapter.loadMoreFail();
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(mContext,"服务器错误，无法获取到数据。",Toast.LENGTH_SHORT).show();

    }


}
