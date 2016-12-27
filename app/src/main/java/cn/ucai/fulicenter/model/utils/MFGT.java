package cn.ucai.fulicenter.model.utils;

import android.app.Activity;
import android.content.Intent;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.controller.activity.MainActivity_;

/**
 * Created by clawpo on 2016/12/27.
 */

public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void startActivity(Activity activity, Intent intent){
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
    public static void startActivity(Activity activity,Class<?> cls){
        Intent intent = new Intent(activity,cls);
        startActivity(activity,intent);
    }
    public static void gotoMain(Activity activity){
        startActivity(activity, MainActivity_.class);
    }
}
