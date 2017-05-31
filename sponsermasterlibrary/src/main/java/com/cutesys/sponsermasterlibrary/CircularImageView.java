/*
 * Copyright 2016 Cutesys Technologies Pvt Ltd as an unpublished work. All Rights
 * Reserved.
 *
 * The information contained herein is confidential property of Cutesys Technologies
 * Pvt Ltd. The use, copying,transfer or disclosure of such information is prohibited
 * except by express written agreement with Company.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * File Name 					: CircularImageView
 * Since 						: 03/11/16
 * Version Code & Project Name	: v 1.0 sponsermasterlibrary
 * Author Name					: Athira Santhosh
 */

package com.cutesys.sponsermasterlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by athira on 03/11/16.
 */
public class CircularImageView extends ImageView {

	private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

	private static final float DEFAULT_BORDER_WIDTH = 4;
	private static final float DEFAULT_SHADOW_RADIUS = 8.0f;

	private float borderWidth;
	private int canvasSize;
	private float shadowRadius;
	private int shadowColor = Color.BLACK;

	private Bitmap image;
	private Drawable drawable;
	private Paint paint;
	private Paint paintBorder;

	public CircularImageView(final Context context) {
		this(context, null);
	}

	public CircularImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {

		paint = new Paint();
		paint.setAntiAlias(true);

		paintBorder = new Paint();
		paintBorder.setAntiAlias(true);

		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CircularImageView, defStyleAttr, 0);

		if (attributes.getBoolean(R.styleable.CircularImageView_civ_border, true)) {
			float defaultBorderSize = DEFAULT_BORDER_WIDTH * getContext().getResources().getDisplayMetrics().density;
			setBorderWidth(attributes.getDimension(R.styleable.CircularImageView_civ_border_width, defaultBorderSize));
			setBorderColor(attributes.getColor(R.styleable.CircularImageView_civ_border_color, Color.WHITE));
		}

		if (attributes.getBoolean(R.styleable.CircularImageView_civ_shadow, false)) {
			shadowRadius = DEFAULT_SHADOW_RADIUS;
			drawShadow(attributes.getFloat(R.styleable.CircularImageView_civ_shadow_radius, shadowRadius),
					attributes.getColor(R.styleable.CircularImageView_civ_shadow_color, shadowColor));
		}
	}

	public void setBorderWidth(float borderWidth) {
		this.borderWidth = borderWidth;
		requestLayout();
		invalidate();
	}

	public void setBorderColor(int borderColor) {
		if (paintBorder != null)
			paintBorder.setColor(borderColor);
		invalidate();
	}

	public void addShadow() {
		if (shadowRadius == 0)
			shadowRadius = DEFAULT_SHADOW_RADIUS;
		drawShadow(shadowRadius, shadowColor);
		invalidate();
	}

	public void setShadowRadius(float shadowRadius) {
		drawShadow(shadowRadius, shadowColor);
		invalidate();
	}

	public void setShadowColor(int shadowColor) {
		drawShadow(shadowRadius, shadowColor);
		invalidate();
	}

	@Override
	public ScaleType getScaleType() {
		return SCALE_TYPE;
	}

	@Override
	public void setScaleType(ScaleType scaleType) {
		if (scaleType != SCALE_TYPE) {
			throw new IllegalArgumentException(String.format("ScaleType %s not supported. " +
					"ScaleType.CENTER_CROP is used by default. So you don't need to use ScaleType.", scaleType));
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		loadBitmap();

		if (image == null)
			return;

		if (!isInEditMode()) {
			canvasSize = canvas.getWidth();
			if (canvas.getHeight() < canvasSize) {
				canvasSize = canvas.getHeight();
			}
		}

		int circleCenter = (int) (canvasSize - (borderWidth * 2)) / 2;
		canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter +
				borderWidth - (shadowRadius + shadowRadius / 2), paintBorder);
		canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter -
				(shadowRadius + shadowRadius / 2), paint);
	}

	private void loadBitmap() {
		if (this.drawable == getDrawable())
			return;

		this.drawable = getDrawable();
		this.image = drawableToBitmap(this.drawable);
		updateShader();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		canvasSize = w;
		if (h < canvasSize)
			canvasSize = h;
		if (image != null)
			updateShader();
	}

	private void drawShadow(float shadowRadius, int shadowColor) {
		this.shadowRadius = shadowRadius;
		this.shadowColor = shadowColor;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			setLayerType(LAYER_TYPE_SOFTWARE, paintBorder);
		}
		paintBorder.setShadowLayer(shadowRadius, 0.0f, shadowRadius / 2, shadowColor);
	}

	private void updateShader() {
		if (image == null)
			return;

		image = cropBitmap(image);

		BitmapShader shader = new BitmapShader(image, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

		Matrix matrix = new Matrix();
		matrix.setScale((float) canvasSize / (float) image.getWidth(), (float) canvasSize / (float) image.getHeight());
		shader.setLocalMatrix(matrix);

		paint.setShader(shader);
	}

	private Bitmap cropBitmap(Bitmap bitmap) {
		Bitmap bmp;
		if (bitmap.getWidth() >= bitmap.getHeight()) {
			bmp = Bitmap.createBitmap(
					bitmap,
					bitmap.getWidth() / 2 - bitmap.getHeight() / 2,
					0,
					bitmap.getHeight(), bitmap.getHeight());
		} else {
			bmp = Bitmap.createBitmap(
					bitmap,
					0,
					bitmap.getHeight() / 2 - bitmap.getWidth() / 2,
					bitmap.getWidth(), bitmap.getWidth());
		}
		return bmp;
	}

	private Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable == null) {
			return null;
		} else if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		int intrinsicWidth = drawable.getIntrinsicWidth();
		int intrinsicHeight = drawable.getIntrinsicHeight();

		if (!(intrinsicWidth > 0 && intrinsicHeight > 0))
			return null;

		try {
			Bitmap bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);
			return bitmap;
		} catch (OutOfMemoryError e) {
			return null;
		}
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = measureWidth(widthMeasureSpec);
		int height = measureHeight(heightMeasureSpec);
		setMeasuredDimension(width, height);
	}

	private int measureWidth(int measureSpec) {
		int result;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else if (specMode == MeasureSpec.AT_MOST) {
			result = specSize;
		} else {
			result = canvasSize;
		}

		return result;
	}

	private int measureHeight(int measureSpecHeight) {
		int result;
		int specMode = MeasureSpec.getMode(measureSpecHeight);
		int specSize = MeasureSpec.getSize(measureSpecHeight);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else if (specMode == MeasureSpec.AT_MOST) {
			result = specSize;
		} else {
			result = canvasSize;
		}
		return (result + 2);
	}
}