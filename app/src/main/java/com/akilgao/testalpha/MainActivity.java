package com.akilgao.testalpha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.akilgao.testalpha.feed.FeedActivity;
import com.akilgao.testalpha.gl.GlVideoActivity;
import com.akilgao.testalpha.guide.GuideActivity;
import com.akilgao.testalpha.window.JumpActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void freeVideo(String name) {
        try {
            InputStream is = getAssets().open(name);
            OutputStream os = new FileOutputStream(new File(getExternalCacheDir(), name));
            byte[] bytes = new byte[4096];
            int len;
            while ((len = is.read(bytes)) > 0) {
                os.write(bytes, 0, len);
            }
            os.flush();
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGlVideo(View view) {
        startActivity(new Intent(this, GlVideoActivity.class));
    }

    public void feed(View view) {
        startActivity(new Intent(this, FeedActivity.class));
    }

    public void startGuild(View view) {
        startActivity(new Intent(this, GuideActivity.class));
    }

    public void startJump(View view) {
        startActivity(new Intent(this, JumpActivity.class));
    }

    public void init(View view) {
        freeVideo("111.mp4");
        freeVideo("222.mp4");
        freeVideo("333.mp4");
        freeVideo("444.mp4");
        freeVideo("555.mp4");
    }
}
