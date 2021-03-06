package com.example.lynnyuki.cloudfunny.view.Eyepetizer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.base.BaseActivity;
import com.example.lynnyuki.cloudfunny.config.CloudFunnyApplication;
import com.example.lynnyuki.cloudfunny.config.Constants;
import com.example.lynnyuki.cloudfunny.model.bean.LikeBean;
import com.example.lynnyuki.cloudfunny.model.bean.VideoBean;
import com.example.lynnyuki.cloudfunny.model.db.LikeBeanGreenDaoManager;
import com.example.lynnyuki.cloudfunny.util.ImageLoader;
import com.example.lynnyuki.cloudfunny.util.SnackBarUtils;
import com.example.lynnyuki.cloudfunny.view.Eyepetizer.adapter.EyepetizerTagAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 视频播放界面
 *
 */

public class EyepetizerDetailActivity extends BaseActivity {
    /**
     * 初始化UI
     */
    @BindView(R.id.txt_video_title)
    TextView txtVideoTitle;
    @BindView(R.id.txt_video_subtitle)
    TextView txtVideoSubtitle;
    @BindView(R.id.txt_video_content)
    TextView txtVideoContent;
    @BindView(R.id.img_video_collection)
    ImageView imgVideoCollection;
    @BindView(R.id.txt_video_collection)
    TextView txtVideoCollection;
    @BindView(R.id.txt_video_share)
    TextView txtVideoShare;
    @BindView(R.id.txt_video_reply)
    TextView txtVideoReply;
    @BindView(R.id.videoplayer)
    JZVideoPlayerStandard videoPlayerStandard;
    @BindView(R.id.recyclerview_tag)
    RecyclerView recyclerviewTag;
    @BindView(R.id.img_video_author)
    RoundedImageView imgVideoAuthor;
    @BindView(R.id.txt_video_author_name)
    TextView txtVideoAuthorName;
    @BindView(R.id.txt_video_author_description)
    TextView txtVideoAuthorDescription;

    private VideoBean.ItemListBean.DataBeanX videoBean;

    private EyepetizerTagAdapter tagAdapter;

    private LikeBeanGreenDaoManager daoManager;

    private Boolean isLiked;

    /**
     * 加载布局
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_eyepetizer_detail;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initialize() {
//        initPalette();
        initData();
        initVideoPlayer();
    }

//    /**
//     * 使用Palette改变状态颜色
//     */
//    private void initPalette() {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.video_bg);
//        Palette.Builder builder = Palette.from(bitmap);
//        builder.generate();
//        // 提取颜色
//        builder.generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(@NonNull Palette palette) {
//                // 柔和的颜色
//                Palette.Swatch muted = palette.getMutedSwatch();
//                if (Build.VERSION.SDK_INT >= 21) {
//                    Window window = getWindow();
//                    // 很明显，这两货是新API才有的。
//                    window.setStatusBarColor(ColorUtil.colorBurn(muted.getRgb()));
//                    window.setNavigationBarColor(ColorUtil.colorBurn(muted.getRgb()));
//                }
//            }
//        });
//    }

    /**
     * 初始化数据
     */
    @SuppressLint("SetTextI18n")
    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        videoBean = (VideoBean.ItemListBean.DataBeanX) bundle.get("data");
        assert videoBean != null;
        if(videoBean.getContent()!=null){
        txtVideoTitle.setText(videoBean.getContent().getData().getTitle());
        txtVideoSubtitle.setText(videoBean.getHeader().getDescription());
        txtVideoContent.setText(videoBean.getContent().getData().getDescription());
        txtVideoShare.setText(videoBean.getContent().getData().getConsumption().getShareCount() + "");
        txtVideoReply.setText(videoBean.getContent().getData().getConsumption().getReplyCount() + "");
        ImageLoader.loadAllNoPlaceHolder(mContext, videoBean.getContent().getData().getAuthor().getIcon(),imgVideoAuthor);
        txtVideoAuthorName.setText(videoBean.getContent().getData().getAuthor().getName());
        txtVideoAuthorDescription.setText(videoBean.getContent().getData().getAuthor().getDescription());
        tagAdapter = new EyepetizerTagAdapter();
        tagAdapter.setNewData(videoBean.getContent().getData().getTags().size() > 3
                ? videoBean.getContent().getData().getTags().subList(0, 3) : videoBean.getContent().getData().getTags());
        recyclerviewTag.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerviewTag.setAdapter(tagAdapter);
        daoManager = CloudFunnyApplication.getAppComponent().getGreenDaoManager();
        setLikeState(daoManager.queryByGuid(videoBean.getHeader().getId() + ""));
        }
    }

    /**
     * 设置视频播放码率
     */
    private void initVideoPlayer() {
        LinkedHashMap map = new LinkedHashMap();

        if (videoBean.getContent()!=null && videoBean.getContent().getData().getPlayInfo().size() == 3) {
            map.put("流畅", videoBean.getContent().getData().getPlayInfo().get(0).getUrl());
            map.put("标清", videoBean.getContent().getData().getPlayInfo().get(1).getUrl());
            map.put("高清", videoBean.getContent().getData().getPlayInfo().get(2).getUrl());
        } else if (videoBean.getContent()!=null &&videoBean.getContent().getData().getPlayInfo().size() == 2) {
            map.put("标清", videoBean.getContent().getData().getPlayInfo().get(0).getUrl());
            map.put("高清", videoBean.getContent().getData().getPlayInfo().get(1).getUrl());
        } else if (videoBean.getContent()!=null &&videoBean.getContent().getData().getPlayInfo().size() == 1) {
            map.put("高清", videoBean.getContent().getData().getPlayInfo().get(0).getUrl());
        }
        Object[] objects = new Object[3];
        objects[0] = map;
        objects[1] = false;//looping
        objects[2] = new HashMap<>();
        videoPlayerStandard.backButton.setVisibility(View.VISIBLE);
        videoPlayerStandard.titleTextView.setTextSize(16);
        if (videoBean.getContent()!=null){
        videoPlayerStandard.setUp(objects, 0,
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, videoBean.getContent().getData().getTitle());
        ImageLoader.loadAllNoPlaceHolder(mContext, videoBean.getContent().getData().getCover().getFeed()
                ,videoPlayerStandard.thumbImageView);
        }
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    /**
     * 视频分享
     */
    @OnClick(R.id.layout_share)
    public void onLayoutShareClick() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if (videoBean.getContent() != null) {
            intent.putExtra(Intent.EXTRA_SUBJECT, videoBean.getContent().getData().getTitle());
            intent.putExtra(Intent.EXTRA_TEXT, videoBean.getContent().getData().getPlayUrl());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(Intent.createChooser(intent, videoBean.getContent().getData().getTitle()));
        }
    }

    /**
     * 视频收藏
     */
    @OnClick(R.id.img_video_collection)
    public void onImgVideoCollectionClick() {
        if (isLiked) {
            imgVideoCollection.setImageResource(R.drawable.icon_uncollect);
            txtVideoCollection.setText("收藏");
            daoManager.deleteByGuid(videoBean.getHeader().getId() + "");
            isLiked = false;
            SnackBarUtils.show(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), "成功从收藏中移除");

        } else {
            imgVideoCollection.setImageResource(R.drawable.icon_collect);
            txtVideoCollection.setText("已收藏");
            LikeBean bean = new LikeBean();
            bean.setId(null);
            if(videoBean.getContent()!=null){
            bean.setGuid(videoBean.getHeader().getId() + "");
            bean.setImageUrl(videoBean.getContent().getData().getCover().getDetail());
            bean.setTitle(videoBean.getContent().getData().getTitle());
            bean.setUrl(videoBean.getContent().getData().getPlayUrl());
            bean.setType(Constants.TYPE_KAI_YAN);
            bean.setTime(System.currentTimeMillis());
            daoManager.insert(bean);
            isLiked = true;
            SnackBarUtils.show(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), "成功添加到收藏");
            }
        }
    }

    private void setLikeState(boolean state) {
        if (state) {
            imgVideoCollection.setImageResource(R.drawable.icon_collect);
            txtVideoCollection.setText("已收藏");
            isLiked = true;
        } else {
            imgVideoCollection.setImageResource(R.drawable.icon_uncollect);
            txtVideoCollection.setText("收藏");
            isLiked = false;
        }
    }

    @Override
    public void onBackPressedSupport() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressedSupport();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
//        Change these two variables back
//        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
//        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }
}