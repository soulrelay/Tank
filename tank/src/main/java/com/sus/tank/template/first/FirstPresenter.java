package com.sus.tank.template.first;

import android.content.Context;
import android.os.Bundle;

import com.sus.tankcommon.base.PresenterGroup;


/**
 * Created by xingjingmin on 2017/4/13.
 */

public class FirstPresenter extends PresenterGroup<IFirstView> {

    public static final String KEY_FROM_PAGE = "KEY_FROM_PAGE";
    public static final String VALUE_FROM_PAGE = "FirstPresenter";

//    private VipCardModel cardData;
//    private int channelId;

    public FirstPresenter(Context context, Bundle arguments) {
        super(context, arguments);
    }

    @Override
    protected void onAdd(Bundle arguments) {
        super.onAdd(arguments);
//
//        cardData = (VipCardModel) arguments.getSerializable(KEY_DATA_VIP_CARD);
//        channelId = arguments.getInt(KEY_CHANNEL_ID);
    }

    @Override
    protected void onPageResume() {
        super.onPageResume();
//
//        HashMap<String, Object> map = new HashMap<>(2);
//        map.put("channel_id", channelId);
//        if (cardData != null) {
//            map.put("member_name", cardData.middle_title);
//        }
//        OmegaUtils.trackEvent("gulf_member_sw", map);
    }

    @Override
    public boolean onBackPressed(BackType backType) {
//
//        if (Utils.isFastDoubleClick()) {
//            return true;
//        }
//
//        HashMap<String, Object> map = new HashMap<>(1);
//        if (cardData != null) {
//            map.put("member_name", cardData.middle_title);
//        }
//        OmegaUtils.trackEvent("gulf_member_return_ck", map);
//
//        Bundle bundle = new Bundle();
//        bundle.putString(KEY_FROM_PAGE, VALUE_FROM_PAGE);
//        goBack(bundle);
        return true;
    }
}
