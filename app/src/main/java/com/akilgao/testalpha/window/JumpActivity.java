package com.akilgao.testalpha.window;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.akilgao.testalpha.R;

/**
 * create by akilgao on 2020/4/13
 */
public class JumpActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);
    }

    public void jump(View view) {
        Intent intent1 = new Intent();
        intent1.setAction("android.intent.action.VIEW");
        String url = ((EditText)findViewById(R.id.url_et)).getText().toString();
        Uri uri = Uri.parse(url);
        intent1.setData(uri);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent1);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
