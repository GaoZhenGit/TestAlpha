package com.akilgao.testalpha;

import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainViewHolder extends RecyclerView.ViewHolder{

    private static final String TAG = "MainViewHolder";
    private TextView mTvTitle;
    private SimpleVideoView mTvVideo;
    private ImageView mIvCover;

    private int index = -1;

    public MainViewHolder(@NonNull View itemView) {
        super(itemView);
        mTvTitle = this.itemView.findViewById(R.id.tv_title);
        mTvVideo = this.itemView.findViewById(R.id.tv_video);
        mIvCover = this.itemView.findViewById(R.id.iv_cover);
        mTvVideo.setCover(mIvCover);
    }

    public void bindData(MainData data, int i) {
        index = i;
        mTvTitle.setText(data.title);
        if (!TextUtils.isEmpty(data.url)) {
            mTvVideo.setData(data.url);
        }
    }

    public void onViewAttachedToWindow() {

    }

    public void onViewDetachedFromWindow() {
        mIvCover.setVisibility(View.VISIBLE);
    }
}
