package com.sus.tankcommon.warehouse.tips.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sus.tankcommon.R;


/**
 * Created by sushuai
 * Date: 17/7/13
 */

public class TipsView extends LinearLayout implements ITipsView {

    private LinearLayout group_formtips;

    public TipsView(Context context) {
        super(context);
        init();
    }

    public TipsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(this.getContext()).inflate(R.layout.oc_form_hypetext, this);
        group_formtips = (LinearLayout) findViewById(R.id.group_formtips);
    }

    @Override
    public void setData(String data) {
        group_formtips.removeAllViews();
        TextView textView = new TextView(getContext());
        textView.setText(data);
        group_formtips.addView(textView);

    }


    @Override
    public View getView() {
        return this;
    }
}
