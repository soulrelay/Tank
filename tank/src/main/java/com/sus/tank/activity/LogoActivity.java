package com.sus.tank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.sus.tank.R;
import com.sus.tankcommon.warehouse.launcherview.LauncherView;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        final LauncherView launcherView = (LauncherView) findViewById(R.id.load_view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                launcherView.start();

            }
        }, 500);
        launcherView.setListener(new LauncherView.LauncherViewListener() {
            @Override
            public void onLauncherViewFinish() {
                Intent intent=new Intent(LogoActivity.this,MainActivity.class);
                startActivity(intent);
                LogoActivity.this.finish();
            }
        });
    }
}
