package com.akilgao.testalpha.feed;

import android.media.MediaPlayer;
import android.view.Surface;

public class MainMediaPlayer extends MediaPlayer {
    private Surface mSurface;
    @Override
    public void setSurface(Surface surface) {
        super.setSurface(surface);
        mSurface = surface;
    }

    public Surface getSurface() {
        return mSurface;
    }
}
