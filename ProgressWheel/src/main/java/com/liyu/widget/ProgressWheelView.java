package com.liyu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/** 
 * Created by liyu on 2015-8-6
 */
public class ProgressWheelView extends View {
	
    private Paint paint;
      
    private int roundColor;
      
    private int roundProgressColor;
      
    private int textColor;
      
    private float textSize;
      
    private float roundWidth;
      
    private int maxProgress;
      
    private int progress = 0;
    
    private int displayStyle;
    
    private static onProgressListener mProgressListener;
    
	public ProgressWheelView(Context context) {
		this(context, null);
	}

	public ProgressWheelView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ProgressWheelView(Context context, AttributeSet attrs,
							 int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		paint = new Paint();
		
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,R.styleable.SuperProgressWheel);
		
		roundColor = mTypedArray.getColor(R.styleable.SuperProgressWheel_roundColor, Color.BLUE);
		roundProgressColor = mTypedArray.getColor(R.styleable.SuperProgressWheel_roundProgressColor, Color.GRAY);
		textColor = mTypedArray.getColor(R.styleable.SuperProgressWheel_textColor, Color.BLACK);
		textSize = mTypedArray.getDimension(R.styleable.SuperProgressWheel_textSize, 45);
		roundWidth = mTypedArray.getDimension(R.styleable.SuperProgressWheel_roundWidth, 35);
		maxProgress = mTypedArray.getInteger(R.styleable.SuperProgressWheel_maxProgress, 100);
		displayStyle = mTypedArray.getInt(R.styleable.SuperProgressWheel_displayStyle, 0);
		
		mTypedArray.recycle();
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
        int centre = getWidth()/2;
        int radius = (int) (centre - roundWidth/2);
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        paint.setAntiAlias(true);
        RectF oval = new RectF(centre - radius, centre - radius, centre  
                + radius, centre + radius);
        
        float startAngle = -90;
		float sweepAngle = (float) 360 / (float) ( 2 * maxProgress);
        for(int i = 0;i < 100;i++){
            canvas.drawArc(oval,startAngle,sweepAngle, false, paint);
            startAngle = sweepAngle * 2 * ( i + 1 ) - 90;
        }

        paint.setColor(roundProgressColor);
        float ProgressStartAngle = -90;
        for(int i = 0;i < progress;i++){
            canvas.drawArc(oval,ProgressStartAngle,sweepAngle, false, paint);
            ProgressStartAngle = sweepAngle * 2 * ( i + 1 ) - 90;
        }

        switch(displayStyle){
            case 0:
                break;
            case 1:
        	    paint.setStrokeWidth(0);   
                paint.setColor(textColor);  
                paint.setTextSize(textSize);  
                paint.setTypeface(Typeface.DEFAULT_BOLD);
                int percent = (int)(((float)progress / (float)maxProgress) * 100);
                float textWidth = paint.measureText(percent + "%");
                canvas.drawText(percent + "%", centre - textWidth / 2, centre + textSize/2, paint);
        	    break;
        }
       
	}
	
	public synchronized int getProgress() {
		return progress;
	}

	public synchronized void setProgress(int progress) {
		if(progress<maxProgress){
		    this.progress = progress;
		    postInvalidate();
		}
		else{
			this.progress = maxProgress;
			postInvalidate();
			if(mProgressListener != null) {
				this.post(new Runnable() {
					@Override
					public void run() {
						mProgressListener.onCompleted(ProgressWheelView.this);
					}
				});
			}
		}
	}
	
	public int getRoundColor() {
		return roundColor;
	}

	public void setRoundColor(int roundColor) {
		this.roundColor = roundColor;
	}

	public int getRoundProgressColor() {
		return roundProgressColor;
	}

	public void setRoundProgressColor(int roundProgressColor) {
		this.roundProgressColor = roundProgressColor;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public float getRoundWidth() {
		return roundWidth;
	}

	public void setRoundWidth(float roundWidth) {
		this.roundWidth = roundWidth;
	}

	public int getMaxProgress() {
		return maxProgress;
	}

	public void setMaxProgress(int maxProgress) {
		this.maxProgress = maxProgress;
	}

	public int getDisplayStyle() {
		return displayStyle;
	}

	public void setDisplayStyle(int displayStyle) {
		this.displayStyle = displayStyle;
	}

	public void setOnProgressListener(onProgressListener mProgressListener){
		this.mProgressListener = mProgressListener;
	}

    public interface onProgressListener{
    	void onCompleted(View v);
    }

}
