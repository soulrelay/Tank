package com.sus.tankcommon.warehouse.formtips.model;


public class SimpleHypeTextModel implements IHypeTextModel {

    public String text;

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
