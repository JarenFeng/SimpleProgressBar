package cn.geektang.simpleprogressbar.common;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by renya on 2016/7/15.
 */
public class DensityHelper {
    /**
     * dp converse to px
     */
    public static float dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale;
    }

    /**
     * px converse to dp
     */
    public static float px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return  pxValue / scale;
    }

    /**
     * sp converse to px
     */
    public static float sp2px(Context context, float spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spValue,context.getResources().getDisplayMetrics());
    }
}
