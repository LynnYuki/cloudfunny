package com.example.lynnyuki.cloudfunny.view.ImageSearch;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.base.BaseActivity;
import com.example.lynnyuki.cloudfunny.config.Constants;
import com.example.lynnyuki.cloudfunny.util.AppNetWorkUtil;
import com.example.lynnyuki.cloudfunny.view.Unsplash.adpter.ImageAdapter;
import com.kc.unsplash.Unsplash;
import com.kc.unsplash.api.Order;
import com.kc.unsplash.models.Photo;
import com.kc.unsplash.models.SearchResults;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import butterknife.BindView;

/**
 * 图片搜索
 */
public class ImageSearchActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    ImageAdapter adapter;
    @BindView(R.id.custom_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Random random = new Random();
    final int randomPage = random.nextInt(50);
    Unsplash unsplash = new Unsplash(Constants.CLIENT_ID);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_search;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initialize() {
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle("图片搜索");
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        Photos(randomPage,30,Order.LATEST);
        recyclerView.setLayoutManager(new LinearLayoutManager(ImageSearchActivity.this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_search, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.image_action_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchQuery(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onRefresh() {
        if(!AppNetWorkUtil.isNetworkConnected(getApplicationContext())){
            Toast.makeText(this,"当前无网络链接，请检查网络设置。",Toast.LENGTH_SHORT).show();
        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Photos(randomPage,30,Order.LATEST);
                }

            }).start();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    private void Photos(int f, int l, Order order) {
        unsplash.getPhotos(f, l, order, new Unsplash.OnPhotosLoadedListener() {
            @Override
            public void onComplete(List<Photo> photos) {
                if(adapter==null){
                adapter = new ImageAdapter(photos, ImageSearchActivity.this);
                recyclerView.setAdapter(adapter);
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "网络连接错误。", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void SearchQuery(String query) {
        unsplash.searchPhotos(query, new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                List<Photo> photos = results.getResults();
                if(photos.size()!=0){
                ImageAdapter adapter = new  ImageAdapter(photos, ImageSearchActivity.this);
                recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(getApplicationContext(), "没有找到相关图片。", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "网络连接错误。", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


}
