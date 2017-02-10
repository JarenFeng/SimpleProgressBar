package cn.geektang.simpleprogressbar.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import cn.geektang.simpleprogressbar.R;
import cn.geektang.simpleprogressbar.common.DensityHelper;

/**
 * Author :Rui
 * Email  :tr@geektang.cn
 * Date   :2016/7/16
 */
public abstract class ProgressBarBase extends ProgressBar {
    // text mode
    public static final int PERCENTAGE = 0;
    public static final int FRACTION = 1;
    public static final int OTHER = 2;

    // default attrs
    protected static final int DEFAULT_REACHED_COLOR = Color.RED;
    protected static final int DEFAULT_REACHED_HEIGHT = 2;    // dp
    protected static final int DEFAULT_UNREACHED_COLOR = Color.BLUE;
    protected static final int DEFAULT_UNREACHED_HEIGHT = 2;    // dp
    protected static final int DEFAULT_TEXT_SIZE = 14;  // sp
    protected static final int DEFAULT_TEXT_COLOR = Color.GREEN;
    protected static final boolean DEFAULT_SHOW_TEXT = true;
    protected static final int DEFAULT_TEXT_PADDING = 3;    // dp
    protected static final int DEFAULT_TEXT_MODE = PERCENTAGE;

    // attrs
    protected int reachedColor = DEFAULT_REACHED_COLOR;
    protected int reachedHeight = DEFAULT_REACHED_HEIGHT;
    protected int unreachedColor = DEFAULT_UNREACHED_COLOR;
    protected int unreachedHeight = DEFAULT_UNREACHED_HEIGHT;
    protected int textSize = DEFAULT_TEXT_SIZE;
    protected int textColor = DEFAULT_TEXT_COLOR;
    protected boolean textVisible = DEFAULT_SHOW_TEXT;
    protected int textPadding = DEFAULT_TEXT_PADDING;
    protected int textPaddingLeft = DEFAULT_TEXT_PADDING;
    protected int textPaddingRight = DEFAULT_TEXT_PADDING;
    protected int textPaddingTop = DEFAULT_TEXT_PADDING;
    protected int textPaddingBottom = DEFAULT_TEXT_PADDING;
    protected int textMode = DEFAULT_TEXT_MODE;

    protected int mRealHeight;
    protected int mRealWidth;

    private OnProcessChangeListener onProcessChangeListener;
    private ProcessTextAdapter processTextAdapter;

    public ProgressBarBase(Context context) {
        this(context, null);
    }

    public ProgressBarBase(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBarBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        obtainBaseAttrs(context, attrs);
    }

    private void obtainBaseAttrs(Context context, AttributeSet attrs) {
        if (null != attrs) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ProgressBarBase);

            reachedColor = ta.getColor(R.styleable.ProgressBarBase_tr_reached_color, reachedColor);
            reachedHeight = (int) ta.getDimension(
                    R.styleable.ProgressBarBase_tr_reached_height,
                    DensityHelper.dip2px(context, reachedHeight));


            unreachedColor = ta.getColor(R.styleable.ProgressBarBase_tr_unreached_color, unreachedColor);
            unreachedHeight = (int) ta.getDimension(
                    R.styleable.ProgressBarBase_tr_unreached_height,
                    DensityHelper.dip2px(context, unreachedHeight));

            textSize = (int) ta.getDimension(
                    R.styleable.ProgressBarBase_tr_text_size,
                    DensityHelper.sp2px(context, textSize));
            textColor = ta.getColor(R.styleable.ProgressBarBase_tr_text_color, textColor);
            textVisible = ta.getBoolean(R.styleable.ProgressBarBase_tr_text_visible, textVisible);

            textPadding = (int) ta.getDimension(
                    R.styleable.ProgressBarBase_tr_text_padding,
                    DensityHelper.dip2px(context, textPadding));

            textPaddingLeft = (int) ta.getDimension(
                    R.styleable.ProgressBarBase_tr_text_padding_left,
                    textPaddingLeft);

            textPaddingRight = (int) ta.getDimension(
                    R.styleable.ProgressBarBase_tr_text_padding_right,
                    textPaddingRight);

            textPaddingTop = (int) ta.getDimension(
                    R.styleable.ProgressBarBase_tr_text_padding_top,
                    textPaddingTop);

            textPaddingBottom = (int) ta.getDimension(
                    R.styleable.ProgressBarBase_tr_text_padding_bottom,
                    textPaddingBottom);

            textMode = ta.getInt(R.styleable.ProgressBarBase_tr_text_show_mode, textMode);

            ta.recycle();
        }
    }

    public void setProcessTextAdapter(ProcessTextAdapter processTextAdapter) {
        this.processTextAdapter = processTextAdapter;
    }


    protected String getProcessText() {
        String text = null;
        if (textMode == PERCENTAGE) {
            text = (int) (1.0f * getProgress() / getMax() * 100) + "%";
        } else if (textMode == FRACTION) {
            text = getProgress() + "/" + getMax();
        } else {
            if (null != processTextAdapter) {
                text = processTextAdapter.getCustomProcessText(getProgress(), getMax());
            } else {
                throw new RuntimeException("A ProgressBarBase#ProcessTextAdapter is required!");
            }
        }
        return text;
    }

    public void setOnProcessChangeListener(OnProcessChangeListener onProcessChangeListener) {
        this.onProcessChangeListener = onProcessChangeListener;
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        if (null != onProcessChangeListener)
            onProcessChangeListener.onProcessChange(progress, getMax());
    }

    public int getReachedColor() {
        return reachedColor;
    }

    public void setReachColor(int reachedColor) {
        this.reachedColor = reachedColor;
        postInvalidate();
    }

    public int getReachedHeight() {
        return reachedHeight;
    }

    public void setReachedHeight(int reachedHeight) {
        this.reachedHeight = reachedHeight;
        postInvalidate();
    }

    public int getUnReachedColor() {
        return unreachedColor;
    }

    public void setUnreachedColor(int unreachedColor) {
        this.unreachedColor = unreachedColor;
        postInvalidate();
    }

    public int getUnreachedHeight() {
        return unreachedHeight;
    }

    public void setUnreachedHeight(int unreachedHeight) {
        this.unreachedHeight = unreachedHeight;
        postInvalidate();
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        postInvalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        postInvalidate();
    }

    public boolean isTextVisible() {
        return textVisible;
    }

    public void setTextVisible(boolean textVisible) {
        this.textVisible = textVisible;
        postInvalidate();
    }

    public int getTextPadding() {
        return textPadding;
    }

    public void setTextPadding(int textPadding) {
        this.textPadding = textPadding;
        this.textPaddingLeft = textPadding;
        this.textPaddingRight = textPadding;
        this.textPaddingTop = textPadding;
        this.textPaddingBottom = textPadding;
        postInvalidate();
    }

    public int getTextPaddingLeft() {
        return textPaddingLeft;
    }

    public void setTextPaddingLeft(int textPaddingLeft) {
        this.textPaddingLeft = textPaddingLeft;
        postInvalidate();
    }

    public int getTextPaddingRight() {
        return textPaddingRight;
    }

    public void setTextPaddingRight(int textPaddingRight) {
        this.textPaddingRight = textPaddingRight;
        postInvalidate();
    }

    public int getTextPaddingTop() {
        return textPaddingTop;
    }

    public void setTextPaddingTop(int textPaddingTop) {
        this.textPaddingTop = textPaddingTop;
        postInvalidate();
    }

    public int getTextPaddingBottom() {
        return textPaddingBottom;
    }

    public void setTextPaddingBottom(int textPaddingBottom) {
        this.textPaddingBottom = textPaddingBottom;
        postInvalidate();
    }


    public int getTextMode() {
        return textMode;
    }

    public void setTextMode(int textMode) {
        if (textMode < PERCENTAGE && textMode > OTHER) {
            throw new RuntimeException("Unsupported mode!");
        }
        this.textMode = textMode;
        postInvalidate();
    }

    public int getRealHeight() {
        return mRealHeight;
    }

    public void setRealHeight(int mRealHeight) {
        this.mRealHeight = mRealHeight;
        postInvalidate();
    }

    public int getRealWidth() {
        return mRealWidth;
    }

    public void setRealWidth(int mRealWidth) {
        this.mRealWidth = mRealWidth;
        postInvalidate();
    }

    public interface OnProcessChangeListener {
        void onProcessChange(int process, int max);
    }

    public interface ProcessTextAdapter {
        String getCustomProcessText(int process, int max);
    }
}
