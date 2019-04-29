package com.akilgao.testalpha;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainViewHolder extends RecyclerView.ViewHolder {

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
            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(Uri.parse(data.url).getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
            mIvCover.setImageBitmap(bitmap);
        }
    }

    public void onStopScroll() {
        mTvVideo.onClick(null);
    }

    public void onViewAttachedToWindow() {

    }

    public void onViewDetachedFromWindow() {

    }
}
