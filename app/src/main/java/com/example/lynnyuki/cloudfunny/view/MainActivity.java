package com.example.lynnyuki.cloudfunny.view;


import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.base.BaseMVPActivity;
import com.example.lynnyuki.cloudfunny.config.CloudFunnyApplication;
import com.example.lynnyuki.cloudfunny.config.Constants;
import com.example.lynnyuki.cloudfunny.contract.MainContract;
import com.example.lynnyuki.cloudfunny.dagger.component.DaggerMainActivityComponent;
import com.example.lynnyuki.cloudfunny.dagger.module.MainActivityModule;
import com.example.lynnyuki.cloudfunny.fragment.OneFragment;
import com.example.lynnyuki.cloudfunny.model.bean.WeatherBean;
import com.example.lynnyuki.cloudfunny.model.prefs.SharePrefManager;
import com.example.lynnyuki.cloudfunny.presenter.MainPresenter;
import com.example.lynnyuki.cloudfunny.util.AppExitUtil;
import com.example.lynnyuki.cloudfunny.util.BottomNavigationViewHelper;
import com.example.lynnyuki.cloudfunny.util.ImageLoader;
import com.example.lynnyuki.cloudfunny.util.WeatherUtil;
import com.example.lynnyuki.cloudfunny.view.About.AboutActivity;
import com.example.lynnyuki.cloudfunny.view.Eyepetizer.EyepetizerFragment;
import com.example.lynnyuki.cloudfunny.view.Eyepetizer.EyepetizerHotFragment;
import com.example.lynnyuki.cloudfunny.view.ImageSearch.ImageSearchActivity;
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
//    private ActionBarDrawerToggle mToggle;
    private EyepetizerHotFragment eyepetizerHotFragment;

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

    private View mHeaderView;

    private TextView mTxtCity;

    private TextView mTxtWeather;

    private ImageView mImgWeather;

    private TextView mTextTemperature;

    private ImageView mImgWeatherBg;


    // 百度定位初始化
    public LocationClient mlocationClient;
    public static String currentPosition = "";

    /**
     * 加载布局
     * @return
     */
    @Override
    public int getLayoutId() {

        return R.layout.activity_main;
    }

    /**
     * 初始化Dagger组件
     */
    @Override
    protected void initInject() {
        DaggerMainActivityComponent
                .builder()
                .appComponent(CloudFunnyApplication.getAppComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }


    /**
     * 初始化
     */

    @Override
    protected void initialize() {
        // 需要theme 设置成 NoActionBar
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        WeatherUtil.init(mContext);

        mHeaderView = navigationView.getHeaderView(0);
        mTxtCity = mHeaderView.findViewById(R.id.txt_city);
        mTxtWeather = mHeaderView.findViewById(R.id.txt_weather);
        mImgWeather = mHeaderView.findViewById(R.id.img_weather);
        mTextTemperature = mHeaderView.findViewById(R.id.txt_temperature);
        mImgWeatherBg = mHeaderView.findViewById(R.id.img_weather_bg);

        if (sharePrefManager.getNightMode()) {
            ImageLoader.loadAll(mContext, R.drawable.bg_weather_night, mImgWeatherBg);
        } else {
            ImageLoader.loadAll(mContext, R.drawable.bg_weather_day, mImgWeatherBg);
        }
        // 关联左上角图标和侧滑菜单
//        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
//        mToggle.syncState();
//        mDrawerLayout.addDrawerListener(mToggle);

        BottomNavigationViewHelper.disableShiftMode(mBottomNav);
        mBottomNav.setOnNavigationItemSelectedListener(this);
        mPresenter.checkPermissions();
        mPresenter.setDayOrNight();
//        initDialog();

    }

    /**
     * 定位初始化
     */
    private void initLocation() {
        // 定位权限初始化
        mlocationClient = new LocationClient(getApplicationContext());
        mlocationClient.registerLocationListener(new MyLocationListener());
        LocationClientOption option = new LocationClientOption();
        //返回定位地址信息
        option.setIsNeedAddress(true);
        mlocationClient.setLocOption(option);
        mlocationClient.start();
    }

    /**
     * 创建菜单
     * @param search
     * @return
     */

    @Override
    public boolean onCreateOptionsMenu(Menu search) {
        getMenuInflater().inflate(R.menu.menu_main, search);
        return true;
    }

    /**
     * 菜单点击事件
     * @param item
     * @return
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search_image:
                startActivity(new Intent(mContext, ImageSearchActivity.class));
            break;
            case R.id.relocation:
               mlocationClient.restart();
               if (currentPosition!=null){
               mPresenter.getWeather(currentPosition);
               Toast.makeText(this,"刷新天气成功",Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(this,"刷新天气失败",Toast.LENGTH_SHORT).show();
               }
        }
        return true;
    }

    /**
     * 初始化Fragment
     */

    private  void initFragment(){

        likeFragment = new LikeFragment();
        oneFragment =  new OneFragment();
        zhiHuFragment = new ZhiHuFragment();
        eyepetizerFragment = new EyepetizerFragment();
        eyepetizerHotFragment = new EyepetizerHotFragment();
        loadMultipleRootFragment(R.id.container, 0, oneFragment,zhiHuFragment,eyepetizerFragment,likeFragment,eyepetizerHotFragment);

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
     * 百度定位回调监听器
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            currentPosition = bdLocation.getCity();
            if (bdLocation.getCity()!=null){
                mPresenter.getWeather(bdLocation.getCity());//传入获得的城市名称
                Toast.makeText(mContext,currentPosition+"定位成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(mContext, "定位错误，可能没有获取到定位权限，请打开定位权限后重新下打开此应用", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 获取天气信息成功
     *
     * @param weatherBean
     */
    @Override
    public void showWeather(WeatherBean weatherBean) {
        if (weatherBean!=null){
        mTxtCity.setText(weatherBean.getHeWeather6().get(0).getBasic().getLocation());
        mTxtWeather.setText(weatherBean.getHeWeather6().get(0).getNow().getCond_txt() + " " + weatherBean.getHeWeather6().get(0).getNow().getWind_dir());
        mTextTemperature.setText(weatherBean.getHeWeather6().get(0).getNow().getTmp() + "°");
        ImageLoader.loadAllAsBitmap(mContext, WeatherUtil.getImageUrl(weatherBean.getHeWeather6().get(0).getNow().getCond_code()), mImgWeather);
        Log.e(TAG,"获取天气信息成功");
        }
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
        initLocation();
        Log.e(TAG,"定位初始化");
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

    /**
     * 底部导航栏点击事件
     * @param item
     * @return
     */
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
                    .setUrl("https://github.com/LynnYuki")
                    .setType(Constants.TYPE_DEFAULT)
                    .setIsZhiHuUrl(false)
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
        /**
         * 销毁定位
         * 如果AMapLocationClient是在当前Activity实例化的，
         * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
         */
        mlocationClient.stop();

    }



}
