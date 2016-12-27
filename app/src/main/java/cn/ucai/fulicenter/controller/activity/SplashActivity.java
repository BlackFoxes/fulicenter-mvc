package cn.ucai.fulicenter.controller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.utils.MFGT;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        //startActivity(new Intent(SplashActivity.this,MainActivity.class));
                        MFGT.gotoMain(SplashActivity.this);
                        MFGT.finish(SplashActivity.this);
                    }
                }
        ,2000);
    }
}
