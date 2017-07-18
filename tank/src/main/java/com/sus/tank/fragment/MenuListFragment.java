
package com.sus.tank.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sus.tank.R;
import com.sus.tankcommon.warehouse.love.LoveLayout;

/**
 * Created by mxn on 2016/12/13.
 * MenuListFragment
 */

public class MenuListFragment extends Fragment {

    private LoveLayout loveLayout;

    private CountDownTimer countDownTimer;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container,
                false);
        loveLayout = (LoveLayout) view.findViewById(R.id.loveLayout);
        initTimer();
        return view;
    }

    private void initTimer() {
        if (countDownTimer == null) {
            countDownTimer = createTimer();
            countDownTimer.start();
        }
    }

    private CountDownTimer createTimer() {
        return new CountDownTimer(Integer.MAX_VALUE, 500) {

            @Override
            public void onTick(long l) {
            loveLayout.addLove();
            }

            @Override
            public void onFinish() {

            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelTimer();
    }

    private void cancelTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = null;
    }
}
