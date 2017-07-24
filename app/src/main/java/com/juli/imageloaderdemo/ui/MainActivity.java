package com.juli.imageloaderdemo.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.juli.imageloaderdemo.R;
import com.juli.imageloaderdemo.model.api.Api;
import com.juli.imageloaderdemo.model.api.apiservice.GankApiService;
import com.juli.imageloaderdemo.model.entity.ImageEntity;
import com.juli.imageloaderdemo.model.entity.ResultMode;
import com.juli.imageloaderdemo.ui.adapter.GridAdapterRecyclerView;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Retrofit retrofit;
    private Call<ResultMode<ImageEntity>> call;
    private Context mContext = this;
    private List<ImageEntity> mImageEntitys=new LinkedList<>();

    private GridAdapterRecyclerView mGridAdapterRecyclerView;
    private int REQUEST_CODE_CHOOSE = 1100;
    private ResultMode<ImageEntity> result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initConfig();
        initView();
    }

    private void initConfig() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.API_Gank_Image_base)
                .addConverterFactory(GsonConverterFactory.create())//添加 json 转换器
                .build();
        GankApiService apiService = retrofit.create(GankApiService.class);
        call = apiService.getImageList(30, 1);
    }

    private void initView() {
        mRecyclerView = (RecyclerView)findViewById(R.id.id_main_recycle_view);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, VERTICAL));
        mGridAdapterRecyclerView= new GridAdapterRecyclerView(mContext, mImageEntitys);
        mRecyclerView.setAdapter(mGridAdapterRecyclerView);
        initData();
    }

    public void initData() {
        Log.e("TAG", call.request().url().toString());
        Observable.create(new Observable.OnSubscribe<ResultMode<ImageEntity>>() {
            @Override
            public void call(Subscriber s) {
                Log.e("TAG", call.request().url().toString());
                try {
                    result = call.execute().body();
                } catch (Exception e) {
                    Log.e("TAG",e.getMessage()+e.toString());
                    e.printStackTrace();
                }
                Log.e("TAG",result.toString()+result.count);
                s.onNext(result);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ResultMode<ImageEntity>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ResultMode<ImageEntity> s) {
                        Log.e("TAG",result.toString()+result.count);
                        mImageEntitys.clear();
                        mImageEntitys.addAll(s.results);
                        mGridAdapterRecyclerView.notifyDataSetChanged();
                    }
                });
    }
}
