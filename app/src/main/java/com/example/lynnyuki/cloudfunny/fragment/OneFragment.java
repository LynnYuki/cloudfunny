package com.example.lynnyuki.cloudfunny.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TabLayout;


import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.adapter.OneFragmentPageAdapter;


import butterknife.BindView;
import butterknife.BindArray;
/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends BaseFragment {

    private static final String TAG = "OneFragment";
    @BindView(R.id.viewpager)
    ViewPager myViewPager;

    @BindView(R.id.tab_layout)
    TabLayout myTabLayout;
    private OneFragmentPageAdapter myAdapter;

    @BindArray(R.array.tittles)
    String[] myTitles;

    public OneFragment() {
        // Required empty public constructor
    }


    @Override
    public View initView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
       return inflater.inflate(R.layout.fragment_one,container,false);
    }

    @Override
    protected  void initListener(View view){
        myAdapter = new OneFragmentPageAdapter(getChildFragmentManager(),myTitles);

        myViewPager.setAdapter(myAdapter);
        myTabLayout.setupWithViewPager(myViewPager);
        myViewPager.setOffscreenPageLimit(3);
    }

}
