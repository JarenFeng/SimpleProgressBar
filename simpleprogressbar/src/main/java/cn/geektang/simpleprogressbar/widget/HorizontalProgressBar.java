package cn.geektang.simpleprogressbar.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import cn.geektang.simpleprogressbar.R;
import cn.geektang.simpleprogressbar.common.DensityHelper;


/**
 * Author :Rui
 * Email  :tr@geektang.cn
 * Date   :2016/7/16
 */
public class HorizontalProgressBar extends ProgressBarBase {

    public static final int CENTER = 0;
    public static final int RIGHTTOP = 1;
    public static final int LEFTTOP = 2;
    public static final int RIGHTBOTTOM = 3;
    public static final int LEFTBOTTOM = 4;

    private static final int DEFAULT_TEXT_POSITION = CENTER;

    private int textPositon = DEFAULT_TEXT_POSITION;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public HorizontalProgressBar(Context context) {
        this(context, null);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        obtainAttrs(context, attrs);
        mPaint.setTextSize(textSize);
    }

    private void obtainAttrs(Context context, AttributeSet attrs) {
        if (null != attrs) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgressBar);
            textPositon = ta.getInt(R.styleable.HorizontalProgressBar_tr_text_location, textPositon);
            ta.recycle();
        }
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int height = 0, width = 0;

        if (textPositon == CENTER) {
            height = measureCenterHeight(heightMeasureSpecSize, heightMeasureSpecMode);
            width = measureCenterWidth(widthMeasureSpecSize, widthMeasureSpecMode);
        } else {
            height = measureRoundHeight(heightMeasureSpecSize, heightMeasureSpecMode);
            width = measureRoundWidth(widthMeasureSpecSize, widthMeasureSpecMode);
        }

        mRealHeight = height - getPaddingBottom() - getPaddingTop();
        mRealWidth = width - getPaddingLeft() - getPaddingRight();
        setMeasuredDimension(width, height);
    }

    private int measureCenterHeight(int heightMeasureSpecSize, int heightMeasureSpecMode) {
        int height = 0;
        if (heightMeasureSpecMode == MeasureSpec.AT_MOST) {
            if(textVisible){
                int textHeight = (int) Math.abs(mPaint.descent() - mPaint.ascent()) + textPaddingBottom + textPaddingTop;
                height = Math.max(textHeight, Math.max(reachHeight, unReachHeight)) + getPaddingBottom() + getPaddingTop();
            }else {
                height = Math.max(reachHeight, unReachHeight) + getPaddingBottom() + getPaddingTop();
            }
        } else {
            height = heightMeasureSpecSize;
        }
        return height;
    }

    private int measureCenterWidth(int widthMeasureSpecSize, int widthMeasureSpecMode) {
        int width = 0;

        if (widthMeasureSpecMode == MeasureSpec.AT_MOST) {
            width = (int) DensityHelper.dip2px(getContext(), 100);
        } else {
            width = widthMeasureSpecSize;
        }

        return width;
    }

    private int measureRoundHeight(int heightMeasureSpecSize, int heightMeasureSpecMode) {
        int height = 0;

        if (heightMeasureSpecMode == MeasureSpec.AT_MOST) {
            if(textVisible){
                int textHeight = (int) Math.abs(mPaint.descent() + mPaint.ascent()) + textPaddingBottom + textPaddingTop;
                height = textHeight + Math.max(reachHeight, unReachHeight) + getPaddingBottom() + getPaddingTop();
            }else{
                height = Math.max(reachHeight, unReachHeight) + getPaddingBottom() + getPaddingTop();
            }
        } else {
            height = heightMeasureSpecSize;
        }

        return height;
    }

    private int measureRoundWidth(int widthMeasureSpecSize, int widthMeasureSpecMode) {
        return measureCenterWidth(widthMeasureSpecSize, widthMeasureSpecMode);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());

        switch (textPositon) {
            case CENTER:
                drawCenterBar(canvas);
                break;
            case RIGHTBOTTOM:
                drawRightBottomBar(canvas);
                break;
            case RIGHTTOP:
                drawRightTopBar(canvas);
                break;
            case LEFTBOTTOM:
                drawLeftBottomBar(canvas);
                break;
            case LEFTTOP:
                drawLetTopBar(canvas);
                break;
            default:
                throw new RuntimeException("No such position!");
        }
        canvas.restore();
    }

    private void drawTopBar(Canvas canvas){
        mPaint.setColor(reachColor);
        mPaint.setStrokeWidth(reachHeight);
        int reachBarLength = (int) (1.0f * getProgress() / getMax() * mRealWidth);
        int drawY = (int) (mRealHeight - Math.max(reachHeight * 0.5f,unReachHeight * 0.5f));
        canvas.drawLine(0,drawY,reachBarLength,drawY,mPaint);

        mPaint.setColor(unReachColor);
        mPaint.setStrokeWidth(unReachHeight);
        canvas.drawLine(reachBarLength,drawY,mRealWidth,drawY,mPaint);
    }

    private void drawLetTopBar(Canvas canvas) {
        drawTopBar(canvas);

        if(textVisible){
            mPaint.setTextSize(textSize);
            mPaint.setColor(textColor);

            String drawText = getProcessText();
            int drawX = textPaddingLeft;
            int drawY = (int) (textPaddingTop - mPaint.descent() - mPaint.ascent());
            canvas.drawText(drawText,drawX,drawY,mPaint);
        }
    }

    private void drawBottomBar(Canvas canvas){
        mPaint.setColor(reachColor);
        mPaint.setStrokeWidth(reachHeight);
        int reachBarLength = (int) (1.0f * getProgress() / getMax() * mRealWidth);
        int drawY = (int) Math.max(reachHeight * 0.5f,unReachHeight * 0.5f);
        canvas.drawLine(0,drawY,reachBarLength,drawY,mPaint);

        mPaint.setColor(unReachColor);
        mPaint.setStrokeWidth(unReachHeight);
        canvas.drawLine(reachBarLength,drawY,mRealWidth,drawY,mPaint);
    }

    private void drawLeftBottomBar(Canvas canvas) {
        drawBottomBar(canvas);

        if(textVisible){
            mPaint.setTextSize(textSize);
            mPaint.setColor(textColor);

            String drawText = getProcessText();
            int drawX = textPaddingLeft;
            int drawY = (int) (textPaddingTop - mPaint.descent() - mPaint.ascent());
            canvas.drawText(drawText,drawX,drawY,mPaint);
        }
    }

    private void drawRightTopBar(Canvas canvas) {
        drawTopBar(canvas);

        if(textVisible){
            mPaint.setTextSize(textSize);
            mPaint.setColor(textColor);

            String drawText = getProcessText();
            int drawLength = (int) mPaint.measureText(drawText);
            int drawX = mRealWidth - textPaddingRight - drawLength;
            int drawY = (int) (textPaddingTop - mPaint.descent() - mPaint.ascent());
            canvas.drawText(drawText,drawX,drawY,mPaint);
        }
    }

    private void drawRightBottomBar(Canvas canvas) {
        drawBottomBar(canvas);

        if(textVisible){
            mPaint.setTextSize(textSize);
            mPaint.setColor(textColor);

            String drawText = getProcessText();
            int drawLength = (int) mPaint.measureText(drawText);
            int drawX = mRealWidth - textPaddingRight - drawLength;
            int drawY = (int) (Math.max(reachHeight,unReachHeight) + textPaddingTop - mPaint.descent() - mPaint.ascent());
            canvas.drawText(drawText,drawX,drawY,mPaint);
        }
    }

    private void drawCenterBar(Canvas canvas) {
        boolean drawUnRech = true;
        mPaint.setTextSize(textSize);
        String drawText = getProcessText();

        if(!textVisible){
            drawText = "";
        }
        int textLength = (int) mPaint.measureText(drawText);

        mPaint.setColor(reachColor);
        mPaint.setStrokeWidth(reachHeight);
        int reachBarLength = (int) (1.0f * getProgress() / getMax() * mRealWidth);
        int drawY = (int) (1.0f * mRealHeight / 2);

        if(reachBarLength + textLength + textPaddingLeft > mRealWidth){
            reachBarLength = mRealWidth - textLength - textPaddingLeft;
            drawUnRech = false;
        }
        canvas.drawLine(0, drawY, reachBarLength, drawY, mPaint);

        mPaint.setColor(textColor);
        int drawX = reachBarLength + textPaddingLeft;
        drawY = (int) (drawY - (mPaint.descent() + mPaint.ascent()) / 2);
        canvas.drawText(drawText,drawX,drawY,mPaint);

        if(drawUnRech){
            mPaint.setColor(unReachColor);
            mPaint.setStrokeWidth(unReachHeight);
            int startX = reachBarLength + textPaddingLeft + textPaddingRight + textLength;
            drawY = (int) (1.0f * mRealHeight / 2);
            canvas.drawLine(startX,drawY,mRealWidth,drawY,mPaint);
        }
    }

    public int getTextPositon() {
        return textPositon;
    }

    public void setTextPositon(int textPositon) {
        if(textPositon < CENTER && textPositon > LEFTBOTTOM){
            throw  new RuntimeException("No such position!");
        }
        this.textPositon = textPositon;
        postInvalidate();
    }
}
