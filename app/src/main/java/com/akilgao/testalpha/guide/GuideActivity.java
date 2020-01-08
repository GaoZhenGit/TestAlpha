package com.akilgao.testalpha.guide;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.akilgao.testalpha.R;

import zhy.com.highlight.HighLight;
import zhy.com.highlight.interfaces.HighLightInterface;
import zhy.com.highlight.position.OnBaseCallback;
import zhy.com.highlight.position.OnBottomPosCallback;
import zhy.com.highlight.position.OnLeftPosCallback;
import zhy.com.highlight.position.OnRightPosCallback;
import zhy.com.highlight.position.OnTopPosCallback;
import zhy.com.highlight.shape.OvalLightShape;
import zhy.com.highlight.shape.RectLightShape;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

/**
 * create by akilgao on 2020-01-07
 */
public class GuideActivity extends Activity {
	private HighLight mHightLightFirst;
	private HighLight mHightLightSecond;
	private HighLight mHightLightThird;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				getWindow().setStatusBarColor(Color.TRANSPARENT);
			} else {
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			}
		}

		setContentView(R.layout.activity_guide);

		showInit();
	}

	private void showInit() {
		final View view = findViewById(R.id.charlie);
		view.post(new Runnable() {
			@Override
			public void run() {
				mHightLightThird = new HighLight(GuideActivity.this)
						.addHighLight(view, R.layout.guide_first,
								new BottomPos(20, false),
								new RectLightShape(-20,-20, 0, 0, 0));
				mHightLightThird.show();
			}
		});
	}

	public void showFirst(View view) {
		mHightLightFirst = new HighLight(GuideActivity.this)//
				.autoRemove(false)//设置背景点击高亮布局自动移除为false 默认为true
//				.intercept(false)//设置拦截属性为false 高亮布局不影响后面布局的滑动效果 而且使下方点击回调失效
				.enableNext()
				.setClickCallback(new HighLight.OnClickCallback() {
					@Override
					public void onClick() {
						Toast.makeText(GuideActivity.this, "clicked and remove HightLight view by yourself", Toast.LENGTH_SHORT).show();
						mHightLightFirst.next();
					}
				})
//				.anchor(view)//如果是Activity上增加引导层，不需要设置anchor
				.addHighLight(view, R.layout.guide_first, new OnRightPosCallback(5), new OvalLightShape())
				.addHighLight(findViewById(R.id.charlie), R.layout.guide_first, new BottomPos(5, true), new RectLightShape());
//				.addHighLight(R.id.btn_bottomLight,R.layout.info_known,new OnTopPosCallback(),new CircleLightShape())
//				.addHighLight(view,R.layout.info_known,new OnBottomPosCallback(10),new OvalLightShape(5,5,20));
		mHightLightFirst.show();
	}

	public void showSecond(View view) {
		mHightLightSecond = new HighLight(GuideActivity.this)//
				.autoRemove(false)//设置背景点击高亮布局自动移除为false 默认为true
//				.intercept(false)//设置拦截属性为false 高亮布局不影响后面布局的滑动效果 而且使下方点击回调失效
				.setClickCallback(new HighLight.OnClickCallback() {
					@Override
					public void onClick() {
						Toast.makeText(GuideActivity.this, "clicked and remove HightLight view by yourself", Toast.LENGTH_SHORT).show();
						mHightLightSecond.remove();
					}
				})
//				.anchor(view)//如果是Activity上增加引导层，不需要设置anchor
				.addHighLight(view, R.layout.guide_first, new BottomPos(20, false), new RectLightShape())
				.addHighLight(findViewById(R.id.charlie), R.layout.guide_first, new BottomPos(5, true), new RectLightShape());
//				.addHighLight(R.id.btn_bottomLight,R.layout.info_known,new OnTopPosCallback(),new CircleLightShape())
//				.addHighLight(view,R.layout.info_known,new OnBottomPosCallback(10),new OvalLightShape(5,5,20));
		mHightLightSecond.show();
	}

	public void showCharlie(View view) {
		showInit();
	}
}
