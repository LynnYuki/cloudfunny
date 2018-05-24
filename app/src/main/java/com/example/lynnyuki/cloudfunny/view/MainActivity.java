package com.example.lynnyuki.cloudfunny.view;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.base.BaseMVPActivity;
import com.example.lynnyuki.cloudfunny.config.CloudFunnyApplication;
import com.example.lynnyuki.cloudfunny.config.Constants;
import com.example.lynnyuki.cloudfunny.contract.MainContract;
import com.example.lynnyuki.cloudfunny.dagger.component.DaggerMainActivityComponent;
import com.example.lynnyuki.cloudfunny.dagger.module.MainActivityModule;
import com.example.lynnyuki.cloudfunny.fragment.OneFragment;
import com.example.lynnyuki.cloudfunny.model.prefs.SharePrefManager;
import com.example.lynnyuki.cloudfunny.presenter.MainPresenter;
import com.example.lynnyuki.cloudfunny.util.AppExitUtil;
import com.example.lynnyuki.cloudfunny.util.BottomNavigationViewHelper;
import com.example.lynnyuki.cloudfunny.view.About.AboutActivity;
import com.example.lynnyuki.cloudfunny.view.Eyepetizer.EyepetizerFragment;
import com.example.lynnyuki.cloudfunny.view.Like.LikeFragment;
import com.example.lynnyuki.cloudfunny.view.Setting.SettingActivity;
import com.example.lynnyuki.cloudfunny.view.Web.WebActivity;
import com.example.lynnyuki.cloudfunny.view.ZhiHu.ZhiHuFragment;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;

public  class MainActivity extends BaseMVPActivity<MainPresenter> implements MainContract.View,BottomNavigationView.OnNavigationItemSelectedListener,NavigationView.OnNavigationItemSelectedListener {

    private static final int PERMISSION_CODE = 1000;
    private static final String TAG = "MainActivity";

    private LikeFragment likeFragment;
    private OneFragment oneFragment;
    private ZhiHuFragment zhiHuFragment;
    private EyepetizerFragment eyepetizerFragment;
    private ActionBarDrawerToggle mToggle;

    // 权限获取提示框
    private MaterialDialog dialog;

    @BindView(R.id.slide_menu)
    NavigationView navigationView;

    @BindView(R.id.bottom_nav)
    BottomNavigationView mBottomNav;

    @BindView(R.id.container)
    FrameLayout mContainer;

    @BindView(R.id.custom_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Inject
    SharePrefManager sharePrefManager;

    @Override
    public int getLayoutId() {

        return R.layout.activity_main;
    }

    @Override
    protected void initInject() {
        DaggerMainActivityComponent
                .builder()
                .appComponent(CloudFunnyApplication.getAppComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }




    @Override
    protected void initialize() {
        // 需要theme 设置成 NoActionBar
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        // 关联左上角图标和侧滑菜单
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
        BottomNavigationViewHelper.disableShiftMode(mBottomNav);
        mBottomNav.setOnNavigationItemSelectedListener(this);

        mPresenter.checkPermissions();
        mPresenter.setDayOrNight();
        initDialog();

    }

    private  void initFragment(){

        likeFragment = new LikeFragment();
        oneFragment =  new OneFragment();
        zhiHuFragment = new ZhiHuFragment();
        eyepetizerFragment = new EyepetizerFragment();
        loadMultipleRootFragment(R.id.container, 0, oneFragment,zhiHuFragment,eyepetizerFragment,likeFragment);

    }

    /**
     * 获取权限提示框
     */
    private void initDialog() {
        dialog = new MaterialDialog.Builder(mContext)
                .title(R.string.permission_application)
                .content(R.string.permission_application_content)
                .cancelable(false)
                .positiveText(R.string.setting)
                .positiveColorRes(R.color.colorPositive)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivityForResult(intent, PERMISSION_CODE);
                    }
                })
                .negativeText(R.string.no)
                .negativeColorRes(R.color.colorNegative)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // 不给权限就直接退出
                        AppExitUtil.exitAPP(mContext);
                    }
                })
                .build();
    }

    /**
     * 返回键监听
     */
    @Override
    public void onBackPressedSupport() {
        AppExitUtil.exitApp(this, mToolbar);
    }

    /**
     * 权限获取失败
     */
    @Override
    public void showPermissionDialog() {
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }


    /**
     * 权限获取成功后
     */
    @Override
    public void getPermissionSuccess() {
        initFragment();
        //设置抽屉菜单Item选中
//       navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);
    }



    /**
     * 设置夜间模式
     * @param changed
     */
    @Override
    public void changeDayOrNight(boolean changed) {
        if (changed) {
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_CODE) {
            mPresenter.checkPermissions();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_one:
                Log.e(TAG, "ONE");
                mToolbar.setTitle("首页");
                showHideFragment(oneFragment);
                break;
            case R.id.nav_two:
                Log.e(TAG, "TWO");
                mToolbar.setTitle("热文");
                showHideFragment(zhiHuFragment);
                break;
            case R.id.nav_three:
                Log.e(TAG, "THREE");
                mToolbar.setTitle("开眼视频");
                showHideFragment(eyepetizerFragment);
                break;
            case R.id.nav_about:
                Log.e(TAG,"关于云趣");
                startActivity(new Intent(mContext, AboutActivity.class));
                break;
            case R.id.nav_info:
                Log.e(TAG,"收藏管理");
                mToolbar.setTitle("我的收藏");
                showHideFragment(likeFragment);
                break;
            case R.id.nav_person:
                Log.e(TAG,"个人主页");
                WebActivity.open(new WebActivity.Builder()
                    .setGuid("")
                    .setImgUrl("")
                    .setType(Constants.TYPE_DEFAULT)
                    .setUrl("https://github.com/LynnYuki")
                    .setTitle("个人主页")
                    .setShowLikeIcon(false)
                    .setContext(mContext));
                break;
            case R.id.nav_setting:
                Log.e(TAG,"系统设置");
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
        }
        mDrawerLayout.closeDrawers();

        return true;
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
