package com.sus.tankcommon.warehouse.formtips.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sus.tankcommon.R;


/**
 * 接送机表单最底下的提示文案
 *
 * @author kechanghe
 * @since 2017/3/27
 */

public class FormHypeTextImpl extends LinearLayout implements IFormHypeText {

    private LinearLayout group_formtips;

    public FormHypeTextImpl(Context context) {
        super(context);
        init();
    }

    public FormHypeTextImpl(Context context, @Nullable AttributeSet attrs) {
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
