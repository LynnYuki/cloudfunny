package com.example.lynnyuki.cloudfunny.view.Like;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.fragment.BaseFragment;
import com.example.lynnyuki.cloudfunny.config.Constants;
import com.example.lynnyuki.cloudfunny.config.CloudFunnyApplication;
import com.example.lynnyuki.cloudfunny.model.bean.LikeBean;
import com.example.lynnyuki.cloudfunny.model.db.LikeBeanGreenDaoManager;
import com.example.lynnyuki.cloudfunny.model.prefs.SharePrefManager;
import com.example.lynnyuki.cloudfunny.view.ImageSearch.ImageSearchActivity;
import com.example.lynnyuki.cloudfunny.view.Like.adapter.LikeAdapter;
import com.example.lynnyuki.cloudfunny.view.Web.WebActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 我的收藏
 *
 */

public class LikeFragment extends BaseFragment {
    public final String TAG = "LikeFragment";
    @BindView(R.id.recyclerview_like)
    RecyclerView recyclerView;

    @Inject
    SharePrefManager sharePrefManager;

    private LikeAdapter likeAdapter;

    private LikeBeanGreenDaoManager daoManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_like;
    }

    /**
     * 初始化收藏数据
     */
    @Override
    protected void initialize() {
        setHasOptionsMenu(true);
        daoManager = CloudFunnyApplication.getAppComponent().getGreenDaoManager();
        likeAdapter = new LikeAdapter();
        //查询所有收藏记录
        likeAdapter.setNewData(daoManager.queryAll());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(likeAdapter);
        //当我们确定Item的改变不会影响RecyclerView的宽高的时候可以设置setHasFixedSize(true)，并通过Adapter的增删改插方法去刷新RecyclerView，而不是通过notifyDataSetChanged()。
        recyclerView.setHasFixedSize(true);
        likeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //获取当前RecyclerView中点击的Item的位置
                LikeBean bean = (LikeBean) adapter.getData().get(position);
                if (bean.getType() == Constants.TYPE_KAI_YAN) {
                    JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    JZVideoPlayerStandard.startFullscreen(mContext, JZVideoPlayerStandard.class, bean.getUrl(), bean.getTitle());
                } else {
                    WebActivity.open(new WebActivity.Builder()
                            .setGuid(bean.getGuid())
                            .setImgUrl(bean.getImageUrl())
                            .setType(bean.getType())
                            .setUrl(bean.getUrl())
                            .setIsZhiHuUrl(true)
                            .setTitle(bean.getTitle())
                            .setShowLikeIcon(true)
                            .setContext(mContext)
                    );
                }
            }
        });
        /**
         * 收藏列表长按点击事件
         */
        likeAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int  position) {
                final LikeBean bean = (LikeBean) adapter.getData().get(position);
                new MaterialDialog.Builder(mContext)
                        .content("确认要删除该收藏吗？")
                        .negativeText("取消")
                        .negativeColorRes(R.color.colorNegative)
                        .positiveText("确定")
                        .positiveColorRes(R.color.colorPositive)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                daoManager.delete(bean);
                                likeAdapter.remove(position);
                            }
                        })
                        .show();
                return true;
            }
        });
        likeAdapter.setEmptyView(R.layout.view_empty, recyclerView);
    }
    /**
     * Fragment重建菜单栏
     * @param menu
     * @param inflater
     */
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
//        inflater.inflate(R.menu.delete_like, menu);
    }

//    /**
//     * 菜单点击事件
//     * @param item
//     * @return
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.search_delete:
//                daoManager.deleteAllData();
//                likeAdapter.notifyDataSetChanged();
//
//                }
//        return true;
//    }

    @Override
    public void onResume() {
        super.onResume();
        if (isInited) {
            likeAdapter.setNewData(daoManager.queryAll());
        }
        if (null != sharePrefManager && null != likeAdapter) {

            likeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        if (JZVideoPlayer.backPress()) {
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}