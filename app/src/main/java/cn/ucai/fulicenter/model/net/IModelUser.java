package cn.ucai.fulicenter.model.net;

import android.content.Context;

import java.io.File;

import cn.ucai.fulicenter.bean.Result;

/**
 * Created by clawpo on 2016/12/29.
 */

public interface IModelUser {
    void register(Context context, String username, String nickname, String password, OnCompleteListener<Result> listener);
    void login(Context context, String username, String password, OnCompleteListener<String> listener);
    void updateNick(Context context, String username, String nick, OnCompleteListener<String> listener);
    void updateAvatar(Context context, String username, File file, OnCompleteListener<String> listener);
    void syncUserInfo(Context context, String username, OnCompleteListener<String> listener);
    
}
