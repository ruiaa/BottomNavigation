package com.ruiaa.bottomnavigation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

/**
 * Created by ruiaa on 2017/10/24.
 */

public class BottomNavUtils {

    public static int getDimenInPx(Context context,int dimenFromR){
        return context.getResources().getDimensionPixelSize(dimenFromR);
    }

    public static Drawable getDrawable(Context context,@DrawableRes int drawableFromR) {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getResources().getDrawable(drawableFromR, null);
        } else {
            return context.getResources().getDrawable(drawableFromR);
        }
    }

    public static int getColor(Context context,@ColorRes int colorFromR) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getResources().getColor(colorFromR, null);
        } else {
            return context.getResources().getColor(colorFromR);
        }
    }

    public static int dp2px(Context context,float dpValue) {
        if (dpValue == 0) return 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context,float spValue) {
        if (spValue == 0) return 0;
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
