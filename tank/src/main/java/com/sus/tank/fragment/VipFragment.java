package com.sus.tank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sus.tank.R;
import com.sus.tankcommon.base.AbsNormalFragment;
import com.sus.tankcommon.base.IView;
import com.sus.tankcommon.base.PresenterGroup;
import com.sus.tankcommon.warehouse.formtips.AbsFormHypeTextComponent;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.sus.tankcommon.component.Components.PageIds.PAGE_NONE;
import static com.sus.tankcommon.component.Components.Types.TYPE1;


/**
 * Created by xingjingmin on 2017/4/13.
 */

public class VipFragment extends AbsNormalFragment {

    private VipPresenter mTopPresenter;

    private RelativeLayout mRootView;
    private LinearLayout mContentContainer;

    private AbsFormHypeTextComponent absFormHypeTextComponent;

    public static VipFragment getInstance() {
        VipFragment fra = new VipFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString(ARG_TITLE, title);
//        fra.setArguments(bundle);
        return fra;
    }

    @Override
    protected PresenterGroup onCreateTopPresenter() {
        mTopPresenter = new VipPresenter(getContext(), getArguments());
        return mTopPresenter;
    }

    @Override
    protected void onSIDPopulated(String sid) {

    }

    @Nullable
    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = (RelativeLayout) inflater.inflate(R.layout.oc_vip_fragment, container, false);


        mContentContainer = (LinearLayout) mRootView.findViewById(R.id.oc_vip_content_root);

        addVipCardComponent();

        return mRootView;
    }

    @Override
    public void onDestroyViewImpl() {
        super.onDestroyViewImpl();
        mTopPresenter = null;
    }

    @Override
    public void setTitle(String title) {
//        if (!isDestroyed()) {
//            mTitleBar.setTitle(title);
//        }
    }

    /**
     * 添加会员卡片组件
     */
    private void addVipCardComponent() {
        AbsFormHypeTextComponent vipCardComponent = newComponent(TYPE1, PAGE_NONE);
        if (vipCardComponent == null) {
            return;
        }
        initComponent(vipCardComponent, TYPE1, mContentContainer, PAGE_NONE);
        IView iView = vipCardComponent.getView();
        View operationView = iView != null ? iView.getView() : null;
        if (operationView == null) {
            return;
        }
        absFormHypeTextComponent = vipCardComponent;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        mContentContainer.addView(operationView, layoutParams);
        mTopPresenter.addChild(absFormHypeTextComponent.getPresenter());
    }
}
