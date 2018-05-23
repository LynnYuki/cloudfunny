package com.example.lynnyuki.cloudfunny.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TabLayout;


import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.adapter.OneFragmentPageAdapter;


import butterknife.BindView;
import butterknife.BindArray;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends BaseFragment {
    private static final String TAG = "OneFragment";
    @BindView(R.id.viewpager)
    ViewPager myViewPager;
    @BindView(R.id.tab_layout)
    TabLayout myTabLayout;
    private OneFragmentPageAdapter oneFPAapter;
    @BindArray(R.array.tittles)
    String[] myTitles;


    @Override
    protected void initialize() {
        oneFPAapter = new OneFragmentPageAdapter(getChildFragmentManager(),myTitles);
        myViewPager.setAdapter(oneFPAapter);
        myTabLayout.setupWithViewPager(myViewPager);
        myViewPager.setOffscreenPageLimit(3);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    public OneFragment() {
        // Required empty public constructor
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"onActivityCreated");

    }
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart");

    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop");
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.d(TAG,"onDestroyView");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDesTroy");
    }
    @Override
    public void onDetach(){
        super.onDetach();
        Log.d(TAG,"onDetach");
    }

}
