package com.akilgao.testalpha;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class PlayerPool {

    private Map<String, MediaPlayer> mPlayerPool;
    private MainMediaPlayer mediaPlayer;

    private static class SingleHolder {
        private static final PlayerPool INSTANCE = new PlayerPool();
    }

    public static PlayerPool getInstance() {
        return SingleHolder.INSTANCE;
    }
    private PlayerPool(){
        mPlayerPool = new HashMap<>();
        mediaPlayer = new MainMediaPlayer();
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("playerpool", " " + mediaPlayer.isPlaying());
                handler.postDelayed(this, 100);
            }
        }, 100);
    }

    public MediaPlayer getPlayer(String url) {
        MediaPlayer player = mPlayerPool.get(url);
        if (player == null) {
            player = new MediaPlayer();
            try {
                player.setDataSource(url);
                player.prepare();
            } catch (Throwable ignore){
                return null;
            }
            mPlayerPool.put(url, player);
        } else {
            player.seekTo(0);
            player.pause();
        }
        return player;
    }

    public MainMediaPlayer getPlayer() {
        return mediaPlayer;
    }
}
