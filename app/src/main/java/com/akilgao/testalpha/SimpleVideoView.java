package com.akilgao.testalpha;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import java.io.IOException;


public class SimpleVideoView extends TextureView implements TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener, View.OnClickListener {
    private Surface mSurface;
    private String mUrl;
    private View cover;

    public SimpleVideoView(Context context) {
        super(context);
        init();
    }

    public SimpleVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setSurfaceTextureListener(this);
        setOnClickListener(this);
    }

    public void setData(String url) {
        mUrl = url;
    }

    public void setCover(View view) {
        cover = view;
    }

    @Override
    public void onClick(View v) {
        MainMediaPlayer player = PlayerPool.getInstance().getPlayer();
        if (player.isPlaying()) {
            player.stop();
            player.setSurface(null);
        }
        player.reset();
        try {
            player.setDataSource(mUrl);
            player.setLooping(true);
            player.setOnPreparedListener(this);
            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        long start = System.currentTimeMillis();
        mSurface = new Surface(surface);
        Log.i("cost", "onSurfaceTextureAvailable:" + (System.currentTimeMillis() - start));
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        long start = System.currentTimeMillis();
        MainMediaPlayer player = PlayerPool.getInstance().getPlayer();
        if (player.getSurface() == mSurface) {
            player.setSurface(null);
        }
        if (cover != null) {
            cover.setVisibility(VISIBLE);
        }
        Log.i("cost", "onSurfaceTextureDestroyed:" + (System.currentTimeMillis() - start));
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        long start = System.currentTimeMillis();
        Log.i("cost", "onSurfaceTextureUpdated:" + (System.currentTimeMillis() - start));
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        long start = System.currentTimeMillis();
        if (isAvailable()) {
            mp.setSurface(mSurface);
            mp.start();
            if (cover != null) {
                cover.setVisibility(GONE);
            }
        }
        Log.i("cost", "onPrepared:" + (System.currentTimeMillis() - start));
    }
}
