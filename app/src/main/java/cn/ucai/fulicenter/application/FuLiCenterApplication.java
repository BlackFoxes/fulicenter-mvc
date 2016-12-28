package cn.ucai.fulicenter.application;

import android.app.Application;

import cn.ucai.fulicenter.bean.User;

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

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FuLiCenterApplication.user = user;
    }

    private static User user;
}
