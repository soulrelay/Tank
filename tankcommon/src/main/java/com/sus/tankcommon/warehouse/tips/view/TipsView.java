package com.sus.tankcommon.warehouse.tips.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sus.tankcommon.R;


/**
 * Created by sushuai
 * Date: 17/7/13
 */

public class TipsView extends LinearLayout implements ITipsView {

    private RelativeLayout rlTips;

    public TipsView(Context context) {
        super(context);
        init();
    }

    public TipsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(this.getContext()).inflate(R.layout.layout_component_tips, this);
        rlTips = (RelativeLayout) findViewById(R.id.rl_tips);
    }

    @Override
    public void setData(String data) {
        rlTips.removeAllViews();
        TextView tvTips = new TextView(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        tvTips.setLayoutParams(layoutParams);
        tvTips.setText(data);
        tvTips.setGravity(Gravity.CENTER);
        rlTips.addView(tvTips);
    }

    @Override
    public View getView() {
        return this;
    }
}
