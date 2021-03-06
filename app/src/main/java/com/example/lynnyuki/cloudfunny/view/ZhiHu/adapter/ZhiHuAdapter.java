package com.example.lynnyuki.cloudfunny.view.ZhiHu.adapter;



import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.model.bean.ZhiHuBean;
import com.example.lynnyuki.cloudfunny.util.AppNetWorkUtil;
import com.example.lynnyuki.cloudfunny.util.DateUtil;
import com.example.lynnyuki.cloudfunny.util.ImageLoader;

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

    /**
     * 知乎UI数据绑定
     * @param helper
     * @param item
     */
    @Override
        protected void convert(BaseViewHolder helper,ZhiHuBean.StoriesBean item){
        if (item!=null && page ==1 ){
            helper.setText(R.id.txt_zhihu_date, DateUtil.Long2String(today));
            helper.setText(R.id.txt_zhihu_title,item.getTitle());
            if (  !AppNetWorkUtil.isNetworkConnected(mContext)) {
                ImageLoader.loadDefault(mContext,(ImageView) helper.getView(R.id.img_zhihu));
            } else if(item.getImages()!=null) {
                ImageLoader.loadAll(mContext,item.getImages().get(0),(ImageView) helper.getView(R.id.img_zhihu));
            }

        }else if(item!=null) {
            helper.setText(R.id.txt_zhihu_date, DateUtil.Long2String(mDate));
            helper.setText(R.id.txt_zhihu_title,item.getTitle());
            if (  !AppNetWorkUtil.isNetworkConnected(mContext)) {
                ImageLoader.loadDefault(mContext,(ImageView) helper.getView(R.id.img_zhihu));
            } else if(item.getImages()!=null){
                ImageLoader.loadAll(mContext,item.getImages().get(0),(ImageView) helper.getView(R.id.img_zhihu));
            }else if (item.getImages()==null){
                ImageLoader.loadDefault(mContext,(ImageView) helper.getView(R.id.img_zhihu));
            }

        }
    }



}
