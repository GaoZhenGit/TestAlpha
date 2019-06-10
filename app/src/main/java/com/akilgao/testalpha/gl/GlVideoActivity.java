package com.akilgao.testalpha.gl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.akilgao.testalpha.R;

public class GlVideoActivity extends AppCompatActivity {

	private LinearLayout mContainer;
	private VideoGLSurfaceView  glview1;
	private VideoGLSurfaceView  glview2;
	private int matIndex = 0;
	private float[][] mat = new float[][]{{
			2f, 0f, 0f, 0f,
			0f, 0.5f, 0f, 0f,
			0f, 0f, 1f, 0f,
			0f, 0f, 0f, 1f,
	}, {
			2f, 0f, 0f, 0f,
			0f, 0.5f, 0f, 0f,
			0f, 0f, 1f, 0f,
			0.5f, 0f, 0f, 1f,
	}, {
			1f, 0f, 0f, 0f,
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
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(600, 800);
		params.topMargin = 50;
		glview1.setLayoutParams(params);
		mContainer.addView(glview1);

		glview2 = new VideoGLSurfaceView(this, "222.mp4");
		glview2.setLayoutParams(params);
		mContainer.addView(glview2);
	}

	public void rotate(View view) {
		glview1.getRenderer().setMatrix(mat[matIndex]);
		matIndex = (matIndex + 1) % mat.length;
	}
}
