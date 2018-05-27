package com.example.lynnyuki.cloudfunny.view.ZhiHu.adapter;



import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.model.bean.ZhiHuBean;
import com.example.lynnyuki.cloudfunny.util.AppNetWorkUtil;
import com.example.lynnyuki.cloudfunny.util.DateUtil;
import com.example.lynnyuki.cloudfunny.util.ImageLoader;
import com.example.lynnyuki.cloudfunny.view.Unsplash.adpter.ImageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhiHuAdapter extends BaseQuickAdapter<ZhiHuBean.StoriesBean, BaseViewHolder> {
    private Long today = System.currentTimeMillis();

    private int page;
    private Long mDate;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }


    public void setmDate(Long mDate) {
        this.mDate = mDate;
    }

    public Long getmDate() {

        return mDate;
    }

    public ZhiHuAdapter() {

        super(R.layout.story_item);

    }

    @Override
        protected void convert(BaseViewHolder helper,ZhiHuBean.StoriesBean item){
        if (item!=null && page ==1 ){
            helper.setText(R.id.txt_zhihu_date, DateUtil.Long2String(today));
            helper.setText(R.id.txt_zhihu_title,item.getTitle());
            if (  !AppNetWorkUtil.isNetworkConnected(mContext)) {
                ImageLoader.loadDefault(mContext,(ImageView) helper.getView(R.id.img_zhihu));
            } else {
                ImageLoader.loadAll(mContext,item.getImages().get(0),(ImageView) helper.getView(R.id.img_zhihu));
            }

        }else if(item!=null) {
            helper.setText(R.id.txt_zhihu_date, DateUtil.Long2String(mDate));
            helper.setText(R.id.txt_zhihu_title,item.getTitle());
            if (  !AppNetWorkUtil.isNetworkConnected(mContext)) {
                ImageLoader.loadDefault(mContext,(ImageView) helper.getView(R.id.img_zhihu));
            } else {
                ImageLoader.loadAll(mContext,item.getImages().get(0),(ImageView) helper.getView(R.id.img_zhihu));
            }

        }
    }



}
