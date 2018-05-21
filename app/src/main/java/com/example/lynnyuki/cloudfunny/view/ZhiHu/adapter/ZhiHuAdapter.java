package com.example.lynnyuki.cloudfunny.view.ZhiHu.adapter;



import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.model.bean.ZhiHuBean;
import com.example.lynnyuki.cloudfunny.util.AppNetWorkUtil;
import com.example.lynnyuki.cloudfunny.util.ImageLoader;

public class ZhiHuAdapter extends BaseQuickAdapter<ZhiHuBean.StoriesBean, BaseViewHolder> {




    public ZhiHuAdapter( ) {
        super(R.layout.item_zhihu);

    }
        @Override
        protected void convert(BaseViewHolder helper,ZhiHuBean.StoriesBean item){

        helper.setText(R.id.txt_zhihu_title,item.getTitle());
            if (  !AppNetWorkUtil.isNetworkConnected(mContext)) {
                ImageLoader.loadDefault(mContext,(ImageView) helper.getView(R.id.img_zhihu));
            } else {
                ImageLoader.loadAll(mContext,item.getImages().get(0),(ImageView) helper.getView(R.id.img_zhihu));
            }

        }

}
