package com.akilgao.testalpha;

import android.app.Application;
import android.content.Context;

import com.akilgao.testalpha.sign.SignatureHookTool;

public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        SignatureHookTool.hook(this);
    }
}
