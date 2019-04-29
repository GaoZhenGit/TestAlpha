package com.akilgao.testalpha;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;
    private List<MainData> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rv_list);
    }

    public void init(View view) {
        freeVideo("abc.mp4");
        freeVideo("abcd.mp4");
        freeVideo("abcde.mp4");

        mData = new ArrayList<>();
        mData.add(new MainData("first", file("abc.mp4")));
        mData.add(new MainData("second", file("abcd.mp4")));
        mData.add(new MainData("third", file("abcde.mp4")));
        mData.add(new MainData("forth", file("abc.mp4")));
        mData.add(new MainData("fifth", file("abcd.mp4")));
        mData.add(new MainData("sixth", file("abcde.mp4")));
        mData.add(new MainData("seventh", file("abc.mp4")));
        mData.add(new MainData("eighth", file("abcd.mp4")));

        mAdapter = new MainAdapter(mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    private String file(String name) {
        return Uri.fromFile(new File(getExternalCacheDir(), name)).toString();
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
}
