package com.example.lynnyuki.cloudfunny.view.ZhiHu;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.base.BaseMVPFragment;
import com.example.lynnyuki.cloudfunny.config.CloudFunnyApplication;
import com.example.lynnyuki.cloudfunny.config.Constants;
import com.example.lynnyuki.cloudfunny.contract.ZhiHuContract;
import com.example.lynnyuki.cloudfunny.dagger.component.DaggerZhiHuFragmentComponent;
import com.example.lynnyuki.cloudfunny.dagger.module.ZhiHuFragmentModule;
import com.example.lynnyuki.cloudfunny.model.bean.ZhiHuBean;
import com.example.lynnyuki.cloudfunny.model.bean.ZhiHuContentBean;
import com.example.lynnyuki.cloudfunny.presenter.ZhiHuPresenter;
import com.example.lynnyuki.cloudfunny.util.AppNetWorkUtil;
import com.example.lynnyuki.cloudfunny.util.DateUtil;
import com.example.lynnyuki.cloudfunny.view.Web.WebActivity;
import com.example.lynnyuki.cloudfunny.view.ZhiHu.adapter.ZhiHuAdapter;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 知乎日报
 */
public class ZhiHuFragment extends BaseMVPFragment<ZhiHuPresenter> implements ZhiHuContract.View,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener  {
    private  static  final String TAG = "ZhiHuFragment";
    boolean isOK = false;
    private Long date_primary = System.currentTimeMillis();
    private Long TimeMillis = date_primary ;
    private String date  ;

    private  int newsId;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private ZhiHuBean.StoriesBean bean;
    private int page = 1;
    private static final int PAGE_SIZE = 30;
    private static final int NULLNEWS = 0;
    private ZhiHuAdapter zhiHuAdapter;
    private ZhiHuContentBean zhiHuContentBean;


    @Override
    protected void initInject() {
        DaggerZhiHuFragmentComponent
                .builder()
                .appComponent(CloudFunnyApplication.getAppComponent())
                .zhiHuFragmentModule(new ZhiHuFragmentModule())
                .build()
                .inject(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zhihu;
    }
    @SuppressLint("ResourceAsColor")

    @Override
    protected void initialize() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(false);
        //给RecyclerView Item之间加上间隔线
//      recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        mPresenter.getZhiHuData();
        Log.e(TAG,"时间"+DateUtil.LongString(TimeMillis));
        setHasOptionsMenu(true);
        zhiHuAdapter = new ZhiHuAdapter();
        zhiHuAdapter.setmDate(TimeMillis);
        recyclerView.setAdapter(zhiHuAdapter);
        zhiHuAdapter.setOnLoadMoreListener(this,recyclerView);
        zhiHuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                bean = (ZhiHuBean.StoriesBean) adapter.getData().get(position);
                newsId = bean.getId();
                mPresenter.getZhiHuContent(newsId);//根据点击知乎日报位置获取当前网页
                Log.e(TAG,"知乎item位置："+newsId);
            }
        });


    }

    /**
     * Fragment重建菜单栏
     * @param menu
     * @param inflater
     */
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }
    /**
     * 跳转知乎详情页面
     */
    public void openWebActivity(ZhiHuBean.StoriesBean storiesBean,ZhiHuContentBean zhiHuContentBean){

        if (zhiHuContentBean != null && (Integer.parseInt(zhiHuContentBean.getShare_url().substring(zhiHuContentBean.getShare_url().lastIndexOf("/")+1)) == newsId))
        {
            WebActivity.open(new WebActivity.Builder()
                    .setGuid(zhiHuContentBean.getShare_url())
                    .setType(Constants.TYPE_ZHI_HU)
                    .setIsZhiHuUrl(true)
                    .setUrl(loadHtml(zhiHuContentBean.getBody()))
                    .setImgUrl(storiesBean.getImages().get(0))
                    .setTitle(storiesBean.getTitle())
                    .setShowLikeIcon(true)
                    .setContext(mContext));
            Log.e(TAG, zhiHuContentBean.getShare_url());
        }
    }
    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        page = 1;
        mPresenter.getZhiHuData();
        TimeMillis = date_primary;
        zhiHuAdapter.setPage(page);
        zhiHuAdapter.setmDate(TimeMillis);
        zhiHuAdapter.setEnableLoadMore(false);
        Log.e(TAG,"TimeMills"+TimeMillis);
        Log.e(TAG,"Page"+page);
        Log.e(TAG,"调用了下拉刷新");
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMoreRequested() {
        page++;
       TimeMillis-=86400000;
        date = DateUtil.LongString(TimeMillis);
        mPresenter.getZhiHuBefore(date);
        zhiHuAdapter.setmDate(TimeMillis);
        zhiHuAdapter.setPage(page);
        swipeRefreshLayout.setEnabled(false);
        Log.e(TAG,"TimeMills"+TimeMillis);
        Log.e(TAG,"Page"+page);
        Log.e(TAG,"调用了上拉加载");

    }


    /**
     * 加载成功
     * @param zhiHuBean
     */
    @Override
    public void showZhiHuData(ZhiHuBean zhiHuBean) {

        if(null!= swipeRefreshLayout && swipeRefreshLayout.isRefreshing() ){
            swipeRefreshLayout.setRefreshing(false);
            zhiHuAdapter.setEnableLoadMore(true);
        }
        if (null!=zhiHuAdapter && zhiHuAdapter.isLoading()){
            swipeRefreshLayout.setEnabled(true);
        }
        if (TimeMillis.equals(date_primary)){
            zhiHuAdapter.setNewData(zhiHuBean.getStories());
            Log.e(TAG,"设置首页新数据");

        }else {
            zhiHuAdapter.addData(zhiHuBean.getStories());
            Log.e(TAG,"添加新数据");

        }
        if (zhiHuBean.getStories().size() <= PAGE_SIZE && zhiHuBean.getStories().size()!=NULLNEWS ){
            zhiHuAdapter.loadMoreComplete();
            Log.e(TAG,"返回数据项数"+zhiHuBean.getStories().size());
        }else if(zhiHuBean.getStories().size() == NULLNEWS){
            zhiHuAdapter.loadMoreEnd();
            Log.e(TAG,"无更多数据了");
            Log.e(TAG,"返回数据项数"+zhiHuBean.getStories().size());
        }else {
            zhiHuAdapter.loadMoreEnd();
            Log.e(TAG,"无更多数据了");
        }


    }

    @Override
    public void showZhiHuContent(ZhiHuContentBean zhiHuContentBean) {
        isOK = true;
        this.zhiHuContentBean = zhiHuContentBean;
        //当根据newsID获取到详情页面后调用

        openWebActivity(bean,zhiHuContentBean);
        }

    /**
     * 知乎详情页面网页格式处理
     * @return
     */
    public String loadHtml(String url) {
        if (url != null) {
            url = url.replace("<div class=\"img-place-holder\">", "");
            url = url.replace("<div class=\"headline\">", "");

            String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";

            String theme = "<body className=\"\" onload=\"onLoaded()\">";

            url = "<!DOCTYPE html>\n"
                    + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                    + "<head>\n"
                    + "\t<meta charset=\"utf-8\" />"
                    + css
                    + "\n</head>\n"
                    + theme
                    + url
                    + "</body></html>";
        } else {
            url = zhiHuContentBean.getShare_url();

        }
        return url;

    }
//    protected String loadHtml(ZhiHuContentBean zhiHuContentBean) {
//    StringBuilder htmlSb = new StringBuilder("<!doctype html>\n<html><head>\n<meta charset=\"utf-8\">\n" +
//            "\t<meta name=\"viewport\" content=\"width=device-width,user-scalable=no\">");
//
//    String content = zhiHuContentBean.getBody();
//    String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/zhihu_daily.css\" type=\"text/css\">\n";
//    String img_replace = "<script src=\"file:///android_asset/img_replace.js\"></script>\n";
//    String video = "<script src=\"file:///android_asset/video.js\"></script>\n";
//    String zepto = "<script src=\"file:///android_asset/zepto.min.js\"></script>\n";
//    String autoLoadImage = "onload=\"onLoaded()\"";
//    boolean autoLoad = AppNetWorkUtil.getNetworkSubType(getContext()).equals("WIFI") ;
//    htmlSb.append(css)
//            .append(zepto)
//            .append(img_replace)
//            .append(video)
//            .append("</head><body className=\"\"")
//            .append(autoLoad ? autoLoadImage : "")
//            .append(" >")
//            .append(content);
//    htmlSb.append("</body></html>");
//    String html = htmlSb.toString();
//
//    html = html.replace("<div class=\"img-place-holder\">", "");
//    Log.e("html1", html);
//    return html;
//}



    /**
     * 加载失败
     */
    @Override
    public void failGetData() {
        zhiHuAdapter.loadMoreFail();
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(false);

    }




}
