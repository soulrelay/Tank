package com.sus.tankcommon.warehouse.launcherview;

import android.animation.TypeEvaluator;

/**
 * Author:    Diamond_Lin
 * Version    V1.0
 * Date:      16/10/18 下午6:48
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/10/18      Diamond_Lin            1.0                    1.0
 * Why & What is modified:
 */
public class ViewPathEvaluator implements TypeEvaluator<ViewPoint> {


    public ViewPathEvaluator() {
    }

    @Override
    public ViewPoint evaluate(float t, ViewPoint startValue, ViewPoint endValue) {

        float x = 0  ,y = 0;

        float startX,startY;

        if(endValue.operation == ViewPath.LINE){

            startX = startValue.x;

            startY = startValue.y;

            x = startX + t * (endValue.x - startX);
            y = startY+ t * (endValue.y - startY);



        }else if(endValue.operation == ViewPath.CURVE){


            startX = startValue.x;
            startY = startValue.y;

            float oneMinusT = 1 - t;


            x = oneMinusT * oneMinusT * oneMinusT * startX +
                    3 * oneMinusT * oneMinusT * t * endValue.x +
                    3 * oneMinusT * t * t * endValue.x1+
                    t * t * t * endValue.x2;

            y = oneMinusT * oneMinusT * oneMinusT * startY +
                    3 * oneMinusT * oneMinusT * t * endValue.y +
                    3 * oneMinusT * t * t * endValue.y1+
                    t * t * t * endValue.y2;


        }



        return new ViewPoint(x,y);
    }
}

