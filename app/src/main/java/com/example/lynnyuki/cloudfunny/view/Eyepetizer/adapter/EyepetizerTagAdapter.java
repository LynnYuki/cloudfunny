package com.example.lynnyuki.cloudfunny.view.Eyepetizer.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.model.bean.VideoBean;
import com.example.lynnyuki.cloudfunny.util.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * 视频标签适配器
 */
public class EyepetizerTagAdapter extends BaseQuickAdapter<VideoBean.ItemListBean.DataBeanX.ContentBean.DataBean.TagBean, BaseViewHolder> {

    public EyepetizerTagAdapter() {
        super(R.layout.item_eyepetizer_tag);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean.ItemListBean.DataBeanX.ContentBean.DataBean.TagBean item) {
        if(item!=null){
        helper.setText(R.id.txt_video_tag_name, "#" + item.getName() + "#");
        RoundedImageView imageView = helper.getView(R.id.img_video_tag);
        ImageLoader.loadAllNoPlaceHolder(mContext,item.getBgPicture(),imageView);
        imageView.setAlpha(0.9f);
        }
    }
}
