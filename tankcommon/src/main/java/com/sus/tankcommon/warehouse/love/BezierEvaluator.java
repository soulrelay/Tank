package com.sus.tankcommon.warehouse.love;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by MMF on 2016/9/18.
 */
public class BezierEvaluator implements TypeEvaluator<PointF> {
    private PointF pointF1;
    private PointF pointF2;

    public BezierEvaluator(PointF pointF1, PointF pointF2) {
        this.pointF1 = pointF1;
        this.pointF2 = pointF2;

    }

    @Override
    public PointF evaluate(float t, PointF pointF0, PointF pointF3) {
        //t百分比0~1.0
        PointF pointF = new PointF();
        pointF.x = pointF0.x * (1 - t) * (1 - t) * (1 - t)
                + 3 * pointF1.x * t * (1 - t) * (1 - t)
                + 3 * pointF2.x * t * t * (1 - t)
                + pointF3.x * t * t * t;
        pointF.y = pointF0.y * (1 - t) * (1 - t) * (1 - t)
                + 3 * pointF1.y * t * (1 - t) * (1 - t)
                + 3 * pointF2.y * t * t * (1 - t)
                + pointF3.y * t * t * t;
        return pointF;
    }
}
