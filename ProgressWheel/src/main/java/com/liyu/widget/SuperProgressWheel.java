package com.liyu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by liyu on 2016/4/27.
 */
public class SuperProgressWheel extends RelativeLayout {

    private ProgressWheelView pwv;

    private ImageView imageView;

    private RotateAnimation animation;

    private int displayDrawableResId;

    private int displayStyle;

    private float drawablePadding;

    private int rotateDuration;

    public SuperProgressWheel(Context context) {
        this(context, null);
    }

    public SuperProgressWheel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperProgressWheel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,R.styleable.SuperProgressWheel);
        displayDrawableResId = mTypedArray.getResourceId(R.styleable.SuperProgressWheel_displayDrawable, R.drawable.ic_fan);
        displayStyle = mTypedArray.getInt(R.styleable.SuperProgressWheel_displayStyle, 0);
        drawablePadding = mTypedArray.getDimension(R.styleable.SuperProgressWheel_drawablePadding, 0);
        rotateDuration = mTypedArray.getInt(R.styleable.SuperProgressWheel_rotateDuration, 1600);
        mTypedArray.recycle();

        pwv = new ProgressWheelView(context,attrs,defStyleAttr);
        addView(pwv,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        if(displayStyle != 2)
            return;

        imageView = new ImageView(context);
        imageView.setImageResource(displayDrawableResId);

        animation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);

        animation.setDuration(rotateDuration);
        animation.setRepeatCount(-1);
        animation.setInterpolator(new LinearInterpolator());
        imageView.setAnimation(animation);
        imageView.setPadding((int)drawablePadding,(int)drawablePadding,(int)drawablePadding,(int)drawablePadding);

        addView(imageView,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        animation.startNow();
    }

    public void startAnimation(){
        if(animation != null)
            animation.startNow();
    }

    public void stopAnimation(){
        if(animation != null)
            animation.cancel();
    }

    public int getProgress() {
        return pwv.getProgress();
    }

    public void setProgress(int progress){
        pwv.setProgress(progress);
    }

    public int getRoundColor() {
        return pwv.getRoundColor();
    }

    public void setRoundColor(int roundColor) {
        pwv.setRoundColor(roundColor);
    }

    public int getRoundProgressColor() {
        return pwv.getRoundProgressColor();
    }

    public void setRoundProgressColor(int roundProgressColor) {
        pwv.setRoundProgressColor(roundProgressColor);
    }

    public int getTextColor() {
        return pwv.getTextColor();
    }

    public void setTextColor(int textColor) {
        pwv.setTextColor(textColor);
    }

    public float getTextSize() {
        return pwv.getTextSize();
    }

    public void setTextSize(float textSize) {
        pwv.setTextSize(textSize);
    }

    public float getRoundWidth() {
        return pwv.getRoundWidth();
    }

    public void setRoundWidth(float roundWidth) {
        pwv.setRoundWidth(roundWidth);
    }

    public int getMaxProgress() {
        return pwv.getMaxProgress();
    }

    public void setMaxProgress(int maxProgress) {
        this.pwv.setMaxProgress(maxProgress);
    }

    public int getDisplayStyle() {
        return displayStyle;
    }

    public void setDisplayStyle(int displayStyle) {
        this.displayStyle = displayStyle;
    }

    public int getRotateDuration() {
        return rotateDuration;
    }

    public void setRotateDuration(int rotateDuration) {
        this.rotateDuration = rotateDuration;
    }

    public float getDrawablePadding() {
        return drawablePadding;
    }

    public void setDrawablePadding(float drawablePadding) {
        this.drawablePadding = drawablePadding;
    }

    public void setOnProgressListener(ProgressWheelView.onProgressListener mProgressListener){
        pwv.setOnProgressListener(mProgressListener);
    }

}
