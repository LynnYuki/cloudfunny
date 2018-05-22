package com.example.lynnyuki.cloudfunny.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.base.BaseActivity;
import com.example.lynnyuki.cloudfunny.fragment.OneFragment;
import com.example.lynnyuki.cloudfunny.fragment.ThreeFragment;
import com.example.lynnyuki.cloudfunny.fragment.FourFragment;
import com.example.lynnyuki.cloudfunny.util.BottomNavigationViewHelper;
import com.example.lynnyuki.cloudfunny.view.Like.LikeFragment;
import com.example.lynnyuki.cloudfunny.view.ZhiHu.ZhiHuFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public  class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener,NavigationView.OnNavigationItemSelectedListener {

    List<String> permissionList  = new ArrayList<>();
    private static final String TAG = "MainActivity";
    private LikeFragment likeFragment;
    private OneFragment oneFragment;
    private ZhiHuFragment zhiHuFragment;
    private ThreeFragment threeFragment;
    private FourFragment fourFragment;
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


    private FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

        Fragment mFragment = null;

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    mFragment = oneFragment;
                    break;
                case 1:
                    mFragment = zhiHuFragment;
                    break;
                case 2:
                    mFragment = threeFragment;
                    break;
                case 3:
                    mFragment = fourFragment;
                    break;
                case 4:
                    mFragment = likeFragment;
            }
            return mFragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    private ActionBarDrawerToggle mToggle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 需要theme 设置成 NoActionBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        // 关联左上角图标和侧滑菜单
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);

        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);
        BottomNavigationViewHelper.disableShiftMode(mBottomNav);
        mBottomNav.setOnNavigationItemSelectedListener(this);
//        mBottomNav.setSelectedItemId(R.id.nav_one); // 设置首页展示的fragment;


        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.MOUNT_FORMAT_FILESYSTEMS);
        }
//        <uses-permission android:name="android.permission.MOUNT_FORMAT_FILESYSTEMS"/>
        //一次性申请所有运行时权限
        if (!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }
    }
//    /**
//     * 权限申请处理
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
//        switch (requestCode){
//            case 1:
//                if (grantResults.length > 0){
//                    for (int result:grantResults){
//                        if (result != PackageManager.PERMISSION_GRANTED){
//                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
//                            // 如果存在某个权限没有处理
////                            finish();
//                            return;
//                        }
//                    }
//                }else{
//                    // 发生未知错误
//                    Toast.makeText(this, "权限申请出现未知错误", Toast.LENGTH_SHORT).show();
//                    //finish();
//                }
//                break;
//            default:
//        }
//    }

    @Override
    public int getLayoutId() {

        return R.layout.activity_main;
    }

    @Override
    protected void initialize() {
        likeFragment = new LikeFragment();
        oneFragment =  new OneFragment();
        zhiHuFragment = new ZhiHuFragment();
        threeFragment = new ThreeFragment();
        fourFragment = new FourFragment();
        loadMultipleRootFragment(R.id.container, 0, oneFragment,zhiHuFragment,threeFragment,fourFragment,likeFragment);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        int index = 0;
        switch (item.getItemId()) {
            case R.id.nav_one:
                Log.e(TAG, "ONE");
                mToolbar.setTitle("首页");
                showHideFragment(oneFragment);
                index = 0;
                break;

            case R.id.nav_two:
                Log.e(TAG, "TWO");
                mToolbar.setTitle("热文");
                index = 1;
                showHideFragment(zhiHuFragment);
                break;

            case R.id.nav_three:
                Log.e(TAG, "THREE");
                mToolbar.setTitle("视频");
                showHideFragment(threeFragment);
                index = 2;
                break;

            case R.id.nav_four:
                Log.e(TAG, "FOUR");
                mToolbar.setTitle("我的");
                showHideFragment(zhiHuFragment);
                index = 3;
                break;
            case R.id.nav_info:
                Log.e(TAG,"收藏管理");
                mToolbar.setTitle("我的收藏");
                showHideFragment(likeFragment);
                break;
        }
        mDrawerLayout.closeDrawers();
//        // 判断是否持有过这个fragment 有直接返回，没有则创建
//        // 该方法会调用setMenuVisibility 显示和隐藏
//        fragment = (Fragment) mAdapter.instantiateItem(mContainer, index);
//        // 设置为当前的fragment  第二个参数没什么意义？？
//        mAdapter.setPrimaryItem(mContainer, 0, fragment);
//        // 提交更新
//        mAdapter.finishUpdate(mContainer);
        return true;
    }
}
