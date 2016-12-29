package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.model.dao.SharePrefrenceUtils;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.model.utils.OnSetAvatarListener;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.view.DisplayUtils;

@EActivity(R.layout.activity_user_profile)
public class UserProfileActivity extends AppCompatActivity {

    @ViewById(R.id.iv_user_profile_avatar)
    ImageView mIvUserProfileAvatar;
    @ViewById(R.id.tv_user_profile_name)
    TextView mTvUserProfileName;
    @ViewById(R.id.tv_user_profile_nick)
    TextView mTvUserProfileNick;
    @Bean(ModelUser.class)
    IModelUser model;

    User user = null;
    OnSetAvatarListener mOnSetAvatarListener;

    @AfterViews void initView() {
        DisplayUtils.initBackWithTitle(this,getResources().getString(R.string.user_profile));
        initData();
    }

    void initData() {
        user = FuLiCenterApplication.getUser();
        if(user==null){
            finish();
            return;
        }
        showInfo();
    }

    @Click({R.id.layout_user_profile_avatar, R.id.layout_user_profile_username, R.id.layout_user_profile_nickname, R.id.btn_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_user_profile_avatar:
                mOnSetAvatarListener = new OnSetAvatarListener(this,R.id.layout_upload_avatar,
                        user.getMuserName(), I.AVATAR_TYPE_USER_PATH);
                break;
            case R.id.layout_user_profile_username:
                CommonUtils.showLongToast(R.string.username_connot_be_modify);
                break;
            case R.id.layout_user_profile_nickname:
                MFGT.gotoUpdateNick(this);
                break;
            case R.id.btn_logout:
                logout();
                break;
        }
    }

    private void logout() {
        if(user!=null){
            SharePrefrenceUtils.getInstence(this).removeUser();
            FuLiCenterApplication.setUser(null);
            MFGT.gotoLogin(this);
        }
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e("onActivityResult,requestCode="+requestCode+",resultCode="+resultCode);
        if(resultCode!=RESULT_OK){
            return;
        }
//        mOnSetAvatarListener.setAvatar(requestCode,data,mIvUserProfileAvatar);
//        if(requestCode== I.REQUEST_CODE_NICK){
//            CommonUtils.showLongToast(R.string.update_user_nick_success);
//        }
//        if(requestCode==OnSetAvatarListener.REQUEST_CROP_PHOTO){
//            updateAvatar();
//        }

        if(requestCode== I.REQUEST_CODE_NICK){
            CommonUtils.showLongToast(R.string.update_user_nick_success);
        }else if(requestCode==OnSetAvatarListener.REQUEST_CROP_PHOTO){
            updateAvatar();
        }else {
            mOnSetAvatarListener.setAvatar(requestCode, data, mIvUserProfileAvatar);
        }
    }

    private void updateAvatar() {
        //file=/storage/emulated/0/Android/data/cn.ucai.fulicenter/files/Pictures/a952700
        //file=/storage/emulated/0/Android/data/cn.ucai.fulicenter/files/Pictures/user_avatar/a952700.jpg
        File file = new File(OnSetAvatarListener.getAvatarPath(this,
                user.getMavatarPath()+"/"+user.getMuserName()
                        +I.AVATAR_SUFFIX_JPG));
        L.e("file="+file.exists());
        L.e("file="+file.getAbsolutePath());
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.update_user_avatar));
        pd.show();
        model.updateAvatar(this, user.getMuserName(), file, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                L.e("s="+s);
                Result result = ResultUtils.getResultFromJson(s,User.class);
                L.e("result="+result);
                if(result==null){
                    CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                }else{
                    User u = (User) result.getRetData();
                    if(result.isRetMsg()){
                        FuLiCenterApplication.setUser(u);
                        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(u),UserProfileActivity.this,mIvUserProfileAvatar);
                        CommonUtils.showLongToast(R.string.update_user_avatar_success);
                    }else{
                        CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                    }
                }
                pd.dismiss();
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                L.e("error="+error);
            }
        });
    }

    private void showInfo(){
        user = FuLiCenterApplication.getUser();
        if(user!=null){
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),this,mIvUserProfileAvatar);
            mTvUserProfileName.setText(user.getMuserName());
            mTvUserProfileNick.setText(user.getMuserNick());
        }
    }
}
