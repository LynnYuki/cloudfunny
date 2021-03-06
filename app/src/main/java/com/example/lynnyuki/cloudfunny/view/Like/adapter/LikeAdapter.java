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


/**
 * 收藏管理适配器
 */
public class LikeAdapter extends BaseQuickAdapter<LikeBean, BaseViewHolder> {

    public LikeAdapter() {
        super(R.layout.item_like);
    }

    /**
     * 收藏界面UI数据绑定
     * @param helper
     * @param item
     */
    @Override
    protected void convert(BaseViewHolder helper, LikeBean item) {
        helper.setText(R.id.txt_like_title, item.getTitle());
        if (item.getType() == Constants.TYPE_ZHI_HU) {
            helper.setText(R.id.txt_like_type, R.string.zhihu);
        } else if (item.getType() == Constants.TYPE_KAI_YAN) {
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
