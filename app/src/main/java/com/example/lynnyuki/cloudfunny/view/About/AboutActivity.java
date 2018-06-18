package com.example.lynnyuki.cloudfunny.view.About;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.base.BaseActivity;
import com.example.lynnyuki.cloudfunny.config.Constants;
import com.example.lynnyuki.cloudfunny.util.AppApplicationUtil;
import com.example.lynnyuki.cloudfunny.view.Web.WebActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 关于Activity
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.img_about)
    ImageView mImgAbout;

    @BindView(R.id.txt_version)
    TextView textView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initialize() {
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("关于");
       textView.setText(getResources().getString(R.string.version) + AppApplicationUtil.getVersionName(mContext));

    }

    /**
     * 菜单点击事件
     */
    @OnClick(R.id.txt_github)
    public void onTxtGithubClicked() {
        WebActivity.open(new WebActivity.Builder()
                .setGuid("")
                .setImgUrl("")
                .setType(Constants.TYPE_DEFAULT)
                .setUrl("https://github.com/LynnYuki/cloudfunny")
                .setIsZhiHuUrl(false)
                .setTitle("项目主页")
                .setShowLikeIcon(false)
                .setContext(mContext)
        );
    }

    @OnClick(R.id.txt_update)
    public  void onTxtUpdateClicked(){
        Toast.makeText(getApplicationContext(),"当前已是最新版本", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.txt_email)
    public void onTxtEmailClicked() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "lynnyuku9527@163.com", null));
        intent.putExtra(Intent.EXTRA_EMAIL, "lynnyuki9527@163.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "意见反馈");
        startActivity(Intent.createChooser(intent, "意见反馈"));
    }

    @OnClick(R.id.txt_share)
    public void onTxtShareClicked() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "https://github.com/LynnYuki/cloudfunny");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(Intent.createChooser(intent, "分享"));
    }
}
