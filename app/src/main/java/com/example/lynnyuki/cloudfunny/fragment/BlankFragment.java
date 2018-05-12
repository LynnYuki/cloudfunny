package com.example.lynnyuki.cloudfunny.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import com.example.lynnyuki.cloudfunny.adapter.MyAdapter;
import com.example.lynnyuki.cloudfunny.fragment.BaseFragment;
import com.example.lynnyuki.cloudfunny.R;
import com.kc.unsplash.Unsplash;
import com.kc.unsplash.api.Order;
import com.kc.unsplash.models.Photo;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends BaseFragment {
    private final String CLIENT_ID = "254783d80c4d6c96ad32d075e9b7792a603a60f8aa9220de95889946daa57395";

    Unsplash unsplash = new Unsplash(CLIENT_ID);
    private int position = 0;

    @BindView(R.id.recycler_view)
    RecyclerView myRescyclerView;

    public BlankFragment() {
        // Required empty public constructor
    }

    public static String KEY_ARG_POSITION = "KEY_ARG_POSITION";

    public static BlankFragment newInstance(Bundle bundle) {
        BlankFragment fragment = new BlankFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        position = args.getInt(KEY_ARG_POSITION);

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    protected void initListener(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        myRescyclerView.setLayoutManager(layoutManager);
        switch (position) {
            case 0:
                Photos(1, 30, Order.POPULAR);
                break;
            case 1:
                Photos(1, 30, Order.LATEST);
                break;
            default:
                Photos(1, 30, Order.OLDEST);
                break;
        }

    }

    private void Photos(int f, int l, Order order) {
        unsplash.getPhotos(f, l, order, new Unsplash.OnPhotosLoadedListener() {
            @Override
            public void onComplete(List<Photo> photos) {
                MyAdapter myAdapter = new MyAdapter(photos, getActivity());
                myRescyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onError(String error) {
            }
        });
    }

}
