package com.akilgao.testalpha.guide;

import android.graphics.RectF;

import zhy.com.highlight.HighLight;
import zhy.com.highlight.position.OnBaseCallback;

/**
 * create by akilgao on 2020-01-07
 */
public class BottomPos extends OnBaseCallback {
	private boolean isLeft;

	public BottomPos(float offset, boolean isLeft) {
		super(offset);
		this.isLeft = isLeft;
	}

	@Override
	public void getPosition(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
		marginInfo.topMargin = rectF.bottom + offset;
		if (isLeft) {
			marginInfo.leftMargin = rectF.left + offset;
		} else {
			marginInfo.rightMargin = rightMargin + offset;
		}
	}
}
