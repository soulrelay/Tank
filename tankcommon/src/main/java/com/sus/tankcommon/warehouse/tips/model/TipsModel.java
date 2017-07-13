package com.sus.tankcommon.warehouse.tips.model;


public class TipsModel implements ITipsModel {

    public String text;

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
