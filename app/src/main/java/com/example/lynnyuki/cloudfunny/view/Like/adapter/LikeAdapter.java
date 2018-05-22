package com.example.lynnyuki.cloudfunny.view.Like.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.config.Constants;
import com.example.lynnyuki.cloudfunny.model.bean.LikeBean;
import com.example.lynnyuki.cloudfunny.util.AppNetWorkUtil;
import com.example.lynnyuki.cloudfunny.util.DateUtil;
import com.example.lynnyuki.cloudfunny.util.ImageLoader;



public class LikeAdapter extends BaseQuickAdapter<LikeBean, BaseViewHolder> {

    public LikeAdapter() {
        super(R.layout.item_like);
    }

    @Override
    protected void convert(BaseViewHolder helper, LikeBean item) {
        helper.setText(R.id.txt_like_title, item.getTitle());
        if (item.getType() == Constants.TYPE_ZhiHu) {
            helper.setText(R.id.txt_like_type, R.string.zhihu);
//        } else if (item.getType() == Constants.TYPE_GANK) {
//            helper.setText(R.id.txt_like_type, R.string.gank);
        } else if (item.getType() == Constants.TYPE_KaiYan) {
            helper.setText(R.id.txt_like_type, R.string.kaiyan);
        }
        helper.setText(R.id.txt_like_date, DateUtil.Long2String(item.getTime()));
        if (!AppNetWorkUtil.isNetworkConnected(mContext)) {
            ImageLoader.loadDefault(mContext,(ImageView) helper.getView(R.id.img_like));
        } else {
            ImageLoader.loadAll(mContext,item.getImageUrl(),(ImageView) helper.getView(R.id.img_like));
        }
    }
}
