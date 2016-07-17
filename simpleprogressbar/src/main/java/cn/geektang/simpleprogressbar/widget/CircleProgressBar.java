package cn.geektang.simpleprogressbar.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import cn.geektang.simpleprogressbar.R;
import cn.geektang.simpleprogressbar.common.DensityHelper;


/**
 * Author :Rui
 * Email  :tr@geektang.cn
 * Date   :2016/7/16
 */
public class CircleProgressBar extends ProgressBarBase {
    private static final float DEFAULT_START_ANGLE = 0;//°
    private static final int DEFAULT_RADIUS = 30;//dp

    private float startAngle;
    private int radius;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mHeight, mWidth;

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttrs(context, attrs);
    }

    private void obtainAttrs(Context context, AttributeSet attrs) {
        if (null != attrs) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
            startAngle = ta.getFloat(R.styleable.CircleProgressBar_tr_start_angle, DEFAULT_START_ANGLE);
            radius = (int) ta.getDimension(
                    R.styleable.CircleProgressBar_tr_radius,
                    DensityHelper.dip2px(context, DEFAULT_RADIUS));
            ta.recycle();
        }
        initPaint();
        rf = new RectF(0, 0, radius * 2, radius * 2);
    }

    private void initPaint() {
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        //mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = radius * 2 + getPaddingLeft() + getPaddingRight() + Math.max(reachHeight, unReachHeight) * 2;
        mHeight = radius * 2 + getPaddingTop() + getPaddingBottom() + Math.max(reachHeight, unReachHeight) * 2;
        mWidth = resolveSize(mWidth, widthMeasureSpec);
        mHeight = resolveSize(mHeight, heightMeasureSpec);

        mRealHeight = mRealWidth
                = Math.min(mWidth - getPaddingLeft() - getPaddingRight(), mHeight - getPaddingTop() - getPaddingBottom());
        setMeasuredDimension(mWidth, mHeight);
    }

    private RectF rf;
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        int translateX = getPaddingLeft() + Math.max(reachHeight, unReachHeight);
        int translateY = getPaddingTop() + Math.max(reachHeight, unReachHeight);

        if (mRealHeight + getPaddingBottom() + getPaddingTop() < mHeight) {
            translateY = (int) (mHeight * 0.5f - radius - Math.max(reachHeight, unReachHeight));
        }

        if (mRealWidth + getPaddingLeft() + getPaddingRight() < mWidth) {
            translateX = (int) (mWidth * 0.5f - radius - Math.max(reachHeight, unReachHeight));
        }
        canvas.translate(translateX, translateY);

        mPaint.setColor(unReachColor);
        mPaint.setStrokeWidth(unReachHeight);
        mPaint.setStyle(Paint.Style.STROKE);

        float sAngle =  1.0f * getProgress() / getMax() * 360 + startAngle;
        float sweepAngle = 360 - 1.0f * getProgress() / getMax() * 360;
        canvas.drawArc(rf, sAngle, sweepAngle, false, mPaint);

        mPaint.setColor(reachColor);
        mPaint.setStrokeWidth(reachHeight);
        mPaint.setStyle(Paint.Style.STROKE);
        sweepAngle = (int) (1.0f * getProgress() / getMax() * 360);
        if(getProgress() > 0)
            canvas.drawArc(rf, startAngle, sweepAngle + 1, false, mPaint);

        if(textVisible){
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(textColor);
            mPaint.setTextSize(textSize);

            String drawText = getProcessText();
            int textLength = (int) mPaint.measureText(drawText);
            int drawX = (int) (radius - textLength * 0.5f);
            int drawY = (int) (radius - (mPaint.descent() + mPaint.ascent()) / 2);
            canvas.drawText(drawText,drawX,drawY,mPaint);
        }

        canvas.restore();
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
        postInvalidate();
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        postInvalidate();
    }
}
