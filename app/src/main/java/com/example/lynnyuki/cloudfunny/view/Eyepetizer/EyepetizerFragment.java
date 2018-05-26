package com.example.lynnyuki.cloudfunny.view.Eyepetizer;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.base.BaseMVPFragment;
import com.example.lynnyuki.cloudfunny.config.CloudFunnyApplication;
import com.example.lynnyuki.cloudfunny.config.Constants;
import com.example.lynnyuki.cloudfunny.contract.EyepetizerContract;
import com.example.lynnyuki.cloudfunny.dagger.component.DaggerEyepetizerFragmentComponent;
import com.example.lynnyuki.cloudfunny.dagger.module.EyepetizerFragmentModule;
import com.example.lynnyuki.cloudfunny.model.bean.VideoBean;
import com.example.lynnyuki.cloudfunny.model.prefs.SharePrefManager;
import com.example.lynnyuki.cloudfunny.presenter.EyepetizerPresenter;
import com.example.lynnyuki.cloudfunny.view.Eyepetizer.adapter.EyepetizerAdapterTwo;
import com.example.lynnyuki.cloudfunny.view.Eyepetizer.adapter.EyepetizerAdapter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 视频首页
 */

public class EyepetizerFragment extends BaseMVPFragment<EyepetizerPresenter> implements EyepetizerContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerview_eyepetizer)
    RecyclerView recyclerView;

    @Inject
    SharePrefManager sharePrefManager;

    private TextView tvHot;

    private TextView tvLike;

    private LinearLayout llMore;

    private RecyclerView recyclerViewTop;

    private EyepetizerAdapter dailyAdapter;

    private EyepetizerAdapterTwo hotAdapter;

    private VideoBean hotVideoBean = new VideoBean();

    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_eyepetizer;
    }

    @Override
    protected void initInject() {
        DaggerEyepetizerFragmentComponent
                .builder()
                .appComponent(CloudFunnyApplication.getAppComponent())
                .eyepetizerFragmentModule(new EyepetizerFragmentModule())
                .build()
                .inject(this);
    }

    @Override
    protected void initialize() {
        setHasOptionsMenu(true);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);

        mPresenter.getVideoData(page, Constants.EYEPETIZER_UDID,"weekly", "256", "XXX");


        View headerView = getActivity().getLayoutInflater().inflate(R.layout.header_eyepetizer, null);
        recyclerViewTop = headerView.findViewById(R.id.recyclerview_eyepetizer_top);
        tvHot = headerView.findViewById(R.id.txt_hot);
        tvLike = headerView.findViewById(R.id.txt_like);
        llMore = headerView.findViewById(R.id.layout_more);

        llMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EyepetizerHotActivity.class);
                mContext.startActivity(intent);
            }
        });

        hotAdapter = new EyepetizerAdapterTwo();
        hotAdapter.setOnItemClickListener(this);
        recyclerViewTop.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTop.setAdapter(hotAdapter);
        recyclerViewTop.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        swipeRefreshLayout.setEnabled(false);
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:
                        swipeRefreshLayout.setEnabled(true);
                        break;
                }
            }
        });
        new PagerSnapHelper().attachToRecyclerView(recyclerViewTop);

        dailyAdapter = new EyepetizerAdapter();
        dailyAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(dailyAdapter);
        dailyAdapter.addHeaderView(headerView);
        dailyAdapter.setOnLoadMoreListener(this, recyclerView);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        VideoBean.ItemListBean bean = (VideoBean.ItemListBean) adapter.getData().get(position);
        VideoBean.ItemListBean.DataBeanX beanX = bean.getData();
        Intent intent = new Intent(mContext, EyepetizerDetailActivity.class);
        intent.putExtra("data", beanX);
        mContext.startActivity(intent);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }
    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        page = 1;
        mPresenter.getVideoData(page, Constants.EYEPETIZER_UDID,"weekly", "256", "XXX");
        mPresenter.getVideoData(page, Constants.EYEPETIZER_UDID,"monthly", "256", "XXX");
        mPresenter.getVideoData(page, Constants.EYEPETIZER_UDID,"historical", "256", "XXX");
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        dailyAdapter.setEnableLoadMore(false);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMoreRequested() {
        page++;
        mPresenter.getDailyVideoData(page, Constants.EYEPETIZER_UDID);
        // 防止上拉加载的时候可以下拉刷新
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void showDailyVideoData(VideoBean dailyBean) {
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
            dailyAdapter.setNewData(dailyBean.getItemList());
        } else {
            dailyAdapter.addData(dailyBean.getItemList());
        }
        if (dailyBean.getItemList() != null) {
            dailyAdapter.loadMoreComplete();
        } else if (dailyBean.getItemList().size() == 0 || dailyBean.getItemList() == null) {
            dailyAdapter.loadMoreEnd();
        }
    }

    @Override
    public void failGetDailyData() {
        dailyAdapter.loadMoreFail();
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showHotVideoData(VideoBean hotBean) {
        if (hotBean != null) {
            hotVideoBean = hotBean;
            hotAdapter.setNewData(hotBean.getItemList().subList(0, 5));
            tvHot.setVisibility(View.VISIBLE);
            tvLike.setVisibility(View.VISIBLE);
            llMore.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void failGetHotData() {
        Toast.makeText(mContext,"服务器错误，无法获取到数据。",Toast.LENGTH_SHORT).show();
    }

}