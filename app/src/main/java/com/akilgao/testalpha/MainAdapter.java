package com.akilgao.testalpha;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private List<MainData> mData;
    private MainViewHolder lastViewHolder;

    public MainAdapter(List<MainData> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_main, viewGroup, false);
        return new MainViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
        mainViewHolder.bindData(mData.get(i), i);
        lastViewHolder = mainViewHolder;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull MainViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.onViewAttachedToWindow();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull MainViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onViewDetachedFromWindow();
    }
}
