package com.sus.tankcommon.utils;

import android.content.Context;

/**
 * Author:    sushuai
 * Version    V1.0
 * Date:      17/7/18 上午11:13
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 17/7/18         sushuai            1.0                    1.0
 * Why & What is modified:
 */
public class CommonUtils {
    /**
     * dp转px
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
