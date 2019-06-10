package com.akilgao.testalpha.gl;

import android.content.Context;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import java.io.File;

/**
 * create by akilgao on 2019/5/29
 */
public class VideoGLSurfaceView extends GLSurfaceView {

	public VideoGLSurfaceView(Context context) {
		super(context);
		init();
	}

	public VideoGLSurfaceView(Context context, String path) {
		super(context);
		init(path);
	}

	public VideoGLSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		init("111.mp4");
	}

	private void init(String path) {
		setEGLContextClientVersion(2);
		this.setRenderer(new GLVideoRenderer(getContext(),
				new File(getContext().getExternalCacheDir(), path).toString()));
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}
}
