package com.juli.imageloaderdemo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.juli.imageloaderdemo.R;
import com.juli.imageloaderdemo.model.entity.ImageEntity;
import com.juli.library.ImageHelper.imageLoader.ImageLoaderManager;

import java.util.List;

/**
 * Created by stormbaron on 17-7-12.
 */

public class GridAdapterRecyclerView extends RecyclerView.Adapter<GridAdapterRecyclerView.ViewHolder> {

    private List<ImageEntity> mDatas;
    Context mContext;

    public GridAdapterRecyclerView(Context mContext, List<ImageEntity> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_main_grid, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ImageLoaderManager.getInstance().load(mDatas.get(position).getUrl())
                .setPlaceHolder(R.mipmap.ic_launcher)
                .setBigUrl(mDatas.get(position).getUrl())
                .setIsCrossFade(true)
                .setIsSkipMemoryCache(true)
                .into(holder.mImg);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImg;

        public ViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.id_main_recycle_item_img);
        }
    }
}
