package cn.ucai.fulicenter.application;

import android.app.Application;

/**
 * Created by clawpo on 2016/12/26.
 */

public class FuLiCenterApplication extends Application {

    private static FuLiCenterApplication instance;
    public static FuLiCenterApplication getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
