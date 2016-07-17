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
    public static final int PERCENTAGE = 0;
    public static final int FRACTION = 1;
    public static final int OTHER = 2;

    protected static final int DEFAULT_REACH_COLOR = Color.RED;
    protected static final int DEFAULT_REACH_HEIGHT = 2;//dp
    protected static final int DEFAULT_UNREACH_COLOR = Color.BLUE;
    protected static final int DEFAULT_UNREACH_HEIGHT = 2;//dp
    protected static final int DEFAULT_TEXT_SIZE = 14;//sp
    protected static final int DEFULT_TEXT_COLOR = Color.GREEN;
    protected static final boolean DEFAULT_IS_SHOW_TEXT = true;
    protected static final int DEFAULT_TEXT_PADDING = 3;//dp
    protected static final int DEFAULT_TEXT_MODEL = PERCENTAGE;

    protected int reachColor = DEFAULT_REACH_COLOR;
    protected int reachHeight = DEFAULT_REACH_HEIGHT;
    protected int unReachColor = DEFAULT_UNREACH_COLOR;
    protected int unReachHeight = DEFAULT_UNREACH_HEIGHT;
    protected int textSize = DEFAULT_TEXT_SIZE;
    protected int textColor = DEFULT_TEXT_COLOR;
    protected boolean textVisible = DEFAULT_IS_SHOW_TEXT;
    protected int textPadding = DEFAULT_TEXT_PADDING;
    protected int textPaddingLeft = DEFAULT_TEXT_PADDING;
    protected int textPaddingRight = DEFAULT_TEXT_PADDING;
    protected int textPaddingTop = DEFAULT_TEXT_PADDING;
    protected int textPaddingBottom = DEFAULT_TEXT_PADDING;
    protected int textModel = DEFAULT_TEXT_MODEL;

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

            reachColor = ta.getColor(R.styleable.ProgressBarBase_tr_reach_color, reachColor);
            reachHeight = (int) ta.getDimension(
                    R.styleable.ProgressBarBase_tr_reach_height,
                    DensityHelper.dip2px(context, reachHeight));


            unReachColor = ta.getColor(R.styleable.ProgressBarBase_tr_unreach_color, unReachColor);
            unReachHeight = (int) ta.getDimension(
                    R.styleable.ProgressBarBase_tr_unreach_height,
                    DensityHelper.dip2px(context, unReachHeight));

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

            textModel = ta.getInt(R.styleable.ProgressBarBase_tr_text_show_model, textModel);

            ta.recycle();
        }
    }

    public void setProcessTextAdapter(ProcessTextAdapter processTextAdapter) {
        this.processTextAdapter = processTextAdapter;
    }


    protected String getProcessText() {
        String text = null;
        if (textModel == PERCENTAGE) {
            text = (int) (1.0f * getProgress() / getMax() * 100) + "%";
        } else if (textModel == FRACTION) {
            text = getProgress() + "/" + getMax();
        } else {
            if (null != processTextAdapter) {
                text = processTextAdapter.getCustomProcessText(getProgress(), getMax());
            } else {
                throw new RuntimeException("You must set ProcessTextAdapter!");
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

    public int getReachColor() {
        return reachColor;
    }

    public void setReachColor(int reachColor) {
        this.reachColor = reachColor;
        postInvalidate();
    }

    public int getReachHeight() {
        return reachHeight;
    }

    public void setReachHeight(int reachHeight) {
        this.reachHeight = reachHeight;
        postInvalidate();
    }

    public int getUnReachColor() {
        return unReachColor;
    }

    public void setUnReachColor(int unReachColor) {
        this.unReachColor = unReachColor;
        postInvalidate();
    }

    public int getUnReachHeight() {
        return unReachHeight;
    }

    public void setUnReachHeight(int unReachHeight) {
        this.unReachHeight = unReachHeight;
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


    public int getTextModel() {
        return textModel;
    }

    public void setTextModel(int textModel) {
        if (textModel < PERCENTAGE && textModel > OTHER) {
            throw new RuntimeException("No such model!");
        }
        this.textModel = textModel;
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
