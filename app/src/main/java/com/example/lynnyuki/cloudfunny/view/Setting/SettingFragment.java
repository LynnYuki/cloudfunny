package com.example.lynnyuki.cloudfunny.view.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;

import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.model.prefs.SharePrefManager;

import com.example.lynnyuki.cloudfunny.config.Constants;
import com.example.lynnyuki.cloudfunny.model.prefs.SharePrefManager;
import com.example.lynnyuki.cloudfunny.util.AppApplicationUtil;
import com.example.lynnyuki.cloudfunny.util.SnackBarUtils;
import com.example.lynnyuki.cloudfunny.util.SystemUtil;
import com.example.lynnyuki.cloudfunny.view.Web.WebActivity;

/**
 * 设置Fragment
 */

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {


    private SwitchPreference nightModePreference;

    private Preference cleanCachePreference;

    private Preference versionPreference;

    private Preference homepagePreference;

    private Preference appNamePreference;

    private Preference programmerPreference;

    private SharePrefManager sharePrefManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        sharePrefManager = new SharePrefManager(getActivity());

        nightModePreference = (SwitchPreference) findPreference(getResources().getString(R.string.key_night_mode));
        cleanCachePreference = findPreference(getResources().getString(R.string.key_clear_cache));
        versionPreference = findPreference(getResources().getString(R.string.key_version));
        homepagePreference = findPreference(getResources().getString(R.string.key_project_page));
        appNamePreference = findPreference(getResources().getString(R.string.key_appName));
        programmerPreference = findPreference(getResources().getString(R.string.key_project_programmer));

        nightModePreference.setOnPreferenceChangeListener(this);
        cleanCachePreference.setOnPreferenceClickListener(this);
        versionPreference.setOnPreferenceClickListener(this);
        homepagePreference.setOnPreferenceClickListener(this);
        appNamePreference.setOnPreferenceClickListener(this);
        programmerPreference.setOnPreferenceClickListener(this);

        // 设置当前版本号
        versionPreference.setSummary("V " + AppApplicationUtil.getVersionName(getActivity()));

        // 设置缓存大小
        cleanCachePreference.setSummary("缓存大小：" + SystemUtil.getTotalCacheSize(getActivity()));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == nightModePreference) {
            sharePrefManager.setNightMode((Boolean) newValue);
            int currentMode = (Boolean) newValue ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
            AppCompatDelegate.setDefaultNightMode(currentMode);
            // recreate()会产生闪屏
//            if (getActivity() instanceof SettingActivity) {
//                getActivity().recreate();
//            }
            // 调用startActivity启动，并为其添加了一个透明渐变的启动动画，最后调用finish结束掉旧的页面。
            getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
            getActivity().overridePendingTransition(R.anim.alpha_start, R.anim.alpha_out);
            getActivity().finish();
        }
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference == cleanCachePreference) {
            SystemUtil.clearAllCache(getActivity());
            SnackBarUtils.show(getView(), "缓存已清除 (*^__^*)");
            cleanCachePreference.setSummary("缓存大小：" + SystemUtil.getTotalCacheSize(getActivity()));
        } else if (preference == programmerPreference) {
            WebActivity.open(new WebActivity.Builder()
                    .setGuid("")
                    .setImgUrl("")
                    .setType(Constants.TYPE_DEFAULT)
                    .setUrl("http://music.163.com/#/user/home?id=326897114")
                    .setTitle("网易云音乐主页")
                    .setShowLikeIcon(false)
                    .setContext(getActivity()));

        } else if (preference == homepagePreference) {
            WebActivity.open(new WebActivity.Builder()
                    .setGuid("")
                    .setImgUrl("")
                    .setType(Constants.TYPE_DEFAULT)
                    .setUrl("https://github.com/LynnYuki/cloudfunny")
                    .setTitle("项目主页")
                    .setShowLikeIcon(false)
                    .setContext(getActivity()));
        }
        return false;
    }
}