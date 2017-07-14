package com.sus.tank.template.first;

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
import com.sus.tankcommon.warehouse.tips.AbsTipsComponent;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.sus.tankcommon.component.Components.PageIds.PAGE_NONE;
import static com.sus.tankcommon.component.Components.Types.TIPS;


/**
 * Created by sushuai
 * Date: 17/7/13
 */
public class FirstFragment extends AbsNormalFragment {

    private FirstPresenter mTopPresenter;

    private RelativeLayout mRootView;
    private LinearLayout mContentContainer;

    private AbsTipsComponent absTipsComponent;

    public static FirstFragment getInstance() {
        FirstFragment fra = new FirstFragment();
        return fra;
    }

    @Override
    protected PresenterGroup onCreateTopPresenter() {
        mTopPresenter = new FirstPresenter(getContext(), getArguments());
        return mTopPresenter;
    }

    @Nullable
    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = (RelativeLayout) inflater.inflate(R.layout.oc_vip_fragment, container, false);


        mContentContainer = (LinearLayout) mRootView.findViewById(R.id.oc_vip_content_root);

        addTipsComponent();

        return mRootView;
    }

    @Override
    public void onDestroyViewImpl() {
        super.onDestroyViewImpl();
        mTopPresenter = null;
    }

    /**
     * 添加只有一行文字的提示组件
     */
    private void addTipsComponent() {
        AbsTipsComponent tipsComponent = newComponent(TIPS, PAGE_NONE);
        if (tipsComponent == null) {
            return;
        }
        initComponent(tipsComponent, TIPS, mContentContainer, PAGE_NONE);
        IView iView = tipsComponent.getView();
        View operationView = iView != null ? iView.getView() : null;
        if (operationView == null) {
            return;
        }
        absTipsComponent = tipsComponent;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        layoutParams.topMargin = 20;
        mContentContainer.addView(operationView, layoutParams);
        mTopPresenter.addChild(absTipsComponent.getPresenter());
    }
}
