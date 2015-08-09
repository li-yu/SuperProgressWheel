package com.liyu.widget;

import com.liyu.superprogresswheel.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/** 
 * Created by liyu on 2015-8-6
 */
public class SuperProgressWheel extends View {
	
    /** 
     * 画笔对象的引用 
     */  
    private Paint paint;  
      
    /** 
     * 圆环的颜色 
     */  
    private int roundColor;  
      
    /** 
     * 圆环进度的颜色 
     */  
    private int roundProgressColor;  
      
    /** 
     * 中间进度百分比的字符串的颜色 
     */  
    private int textColor;  
      
    /** 
     * 中间进度百分比的字符串的字体 
     */  
    private float textSize;  
      
    /** 
     * 圆环的宽度 
     */  
    private float roundWidth;  
      
    /** 
     * 最大进度 
     */  
    private int maxProgress;  
      
    /** 
     * 当前进度 
     */  
    private int progress = 0; 
    
    /**
     * 圆环中间区域显示类型：0无，1进度文字，2图片
     */
    private int displayStyle;
    
    /**
     * 圆环中间区域显示图片的ID
     */
    private int displayDrawableResId;
    
    /**
     * 上下文
     */
    private Context context;
    
    /**
     * 每次旋转的角度，单位是度；也可以理解为旋转速率
     */
    private int rotateDegree = -5;
    
    /**
     * 状态回调接口监听器
     */
    private onProgressListener mProgressListener;
    
    /**
     * bitmap:原始图片
     * resBitmap：添加了matrix旋转属性后的图片
     * matrix：实现图片旋转
     */
    private Bitmap bitmap,resBitmap;
    private Matrix matrix = new Matrix();
    private boolean startDrawableAnim = false;
    
	public SuperProgressWheel(Context context) {
		this(context, null);
	}

	public SuperProgressWheel(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public SuperProgressWheel(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		paint = new Paint();
		
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,R.styleable.SuperProgressWheel);
		
		roundColor = mTypedArray.getColor(R.styleable.SuperProgressWheel_roundColor, Color.BLUE);
		roundProgressColor = mTypedArray.getColor(R.styleable.SuperProgressWheel_roundProgressColor, Color.GRAY);
		textColor = mTypedArray.getColor(R.styleable.SuperProgressWheel_textColor, Color.BLACK);
		textSize = mTypedArray.getDimension(R.styleable.SuperProgressWheel_textSize, 45);
		roundWidth = mTypedArray.getDimension(R.styleable.SuperProgressWheel_roundWidth, 35);
		maxProgress = mTypedArray.getInteger(R.styleable.SuperProgressWheel_maxProgress, 100);
		displayDrawableResId = mTypedArray.getResourceId(R.styleable.SuperProgressWheel_displayDrawable, R.drawable.ic_launcher);
		displayStyle = mTypedArray.getInt(R.styleable.SuperProgressWheel_displayStyle, 0);
		
		mTypedArray.recycle();
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		/** 
         * 画初始的大圆环 
         */  
        int centre = getWidth()/2; //获取圆心的x坐标  
        int radius = (int) (centre - roundWidth/2); //圆环的半径  
        paint.setColor(roundColor); //设置圆环的颜色  
        paint.setStyle(Paint.Style.STROKE); //设置画笔类型 
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度  
        paint.setAntiAlias(true);  //消除锯齿   
        RectF oval = new RectF(centre - radius, centre - radius, centre  
                + radius, centre + radius);  //用于定义的圆弧的形状和大小的界限  
        
        float startAngle=-90,sweepAngle=(float)360/(float)(2*maxProgress);
        for(int i =0;i<100;i++){
            canvas.drawArc(oval,startAngle,sweepAngle, false, paint);
            startAngle = sweepAngle*2*(i+1)-90;
        }
        
        /**
         * 画进度圆环
         */
        paint.setColor(roundProgressColor);
        float ProgressStartAngle = -90;
        for(int i =0;i<progress;i++){
            canvas.drawArc(oval,ProgressStartAngle,sweepAngle, false, paint);
            ProgressStartAngle = sweepAngle*2*(i+1)-90;
        }
        
        /**
         * 判断中间区域显示类型：0无，1进度文字，2图片
         */
        switch(displayStyle){
            case 0:
                break;
            case 1:
        	    paint.setStrokeWidth(0);   
                paint.setColor(textColor);  
                paint.setTextSize(textSize);  
                paint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
                int percent = (int)(((float)progress / (float)maxProgress) * 100);  //中间的进度百分比，先转换成float在进行除法运算，不然都为0  
                float textWidth = paint.measureText(percent + "%");   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间  
                canvas.drawText(percent + "%", centre - textWidth / 2, centre + textSize/2, paint); //画出进度百分比  
        	    break;
            case 2:
        	    bitmap = BitmapFactory.decodeResource(context.getResources(),displayDrawableResId);
                matrix.postRotate(rotateDegree,centre,centre);//以centre为圆点进行旋转
                resBitmap = Bitmap.createBitmap(bitmap,0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);//整合为新bitmap
                canvas.drawBitmap(resBitmap, centre-(int)(resBitmap.getWidth()/2), centre-(int)(resBitmap.getHeight()/2), null);
                if(startDrawableAnim)
                	invalidate();//为了实现旋转的动画效果，就不停的刷新view
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
			mHandler.sendEmptyMessage(1);
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

	public int getDisplayDrawableResId() {
		return displayDrawableResId;
	}

	public void setDisplayDrawableResId(int displayDrawableResId) {
		this.displayDrawableResId = displayDrawableResId;
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

	public boolean isStartDrawableAnim() {
		return startDrawableAnim;
	}

	public void setStartDrawableAnim(boolean startDrawableAnim) {
		this.startDrawableAnim = startDrawableAnim;
	};
	
	public Handler mHandler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
            switch (msg.what) {  
                case 1:  
                	mProgressListener.onCompleted(SuperProgressWheel.this);
                    break;    
            }  
        }  
    }; 

}
