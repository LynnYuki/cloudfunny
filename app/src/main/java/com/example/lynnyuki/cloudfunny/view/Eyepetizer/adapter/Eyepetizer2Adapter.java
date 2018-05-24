package com.example.lynnyuki.cloudfunny.view.Eyepetizer.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.model.bean.VideoBean;
import com.example.lynnyuki.cloudfunny.util.ImageLoader;
import com.example.lynnyuki.cloudfunny.util.SystemUtil;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by xiarh on 2018/2/7.
 */

public class Eyepetizer2Adapter extends BaseQuickAdapter<VideoBean.ItemListBean, BaseViewHolder> {

    public Eyepetizer2Adapter() {
        super(R.layout.item_eyepetizer_hot);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean.ItemListBean item) {
        helper.setText(R.id.txt_video_duration, SystemUtil.second2Minute(item.getData().getContent().getData().getDuration()));
        helper.setText(R.id.txt_video_title, item.getData().getContent().getData().getTitle());
        helper.setText(R.id.txt_video_content, item.getData().getHeader().getTitle() + " / "
                + item.getData().getHeader().getDescription());
        ImageLoader.loadAllNoPlaceHolder(mContext, item.getData().getContent().getData().getCover().getDetail()
                , (RoundedImageView) helper.getView(R.id.img_video));
        ImageLoader.loadAllNoPlaceHolder(mContext, item.getData().getHeader().getIcon()
                , (RoundedImageView) helper.getView(R.id.img_video_author));
        ImageView imgDaily = helper.getView(R.id.img_dialy);
        if (item.getData().getHeader().getDescription().contains("每日编辑精选")) {
            imgDaily.setVisibility(View.VISIBLE);
        } else {
            imgDaily.setVisibility(View.GONE);
        }
    }
}