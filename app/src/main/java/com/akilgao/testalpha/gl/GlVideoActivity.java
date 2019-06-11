package com.akilgao.testalpha.gl;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.akilgao.testalpha.R;

public class GlVideoActivity extends AppCompatActivity {

    private LinearLayout mContainer;
    private VideoGLSurfaceView glview1;
    private VideoGLSurfaceView glview2;
    private int matIndex = 0;
    private float[][] mat = new float[][]{{
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f,
    }, {
            0.5f, 0f, 0f, 0f,
            0f, 0.5f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f,
    }, {
            0.5f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f,
    }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gl_video);
        mContainer = findViewById(R.id.ll_container);

        glview1 = new VideoGLSurfaceView(this, "111.mp4");
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(dip2px(this, 240), dip2px(this, 320));
        params.topMargin = dip2px(this, 50);
        glview1.setLayoutParams(params);
        glview1.getRenderer().setMatrix(mat[0]);
        mContainer.addView(glview1);

        glview2 = new VideoGLSurfaceView(this, "222.mp4");
        glview2.setLayoutParams(params);
        glview2.getRenderer().setMatrix(mat[0]);
        mContainer.addView(glview2);
    }

    public void rotate(View view) {
        matIndex = (matIndex + 1) % mat.length;
        glview1.getRenderer().setMatrix(mat[matIndex]);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
