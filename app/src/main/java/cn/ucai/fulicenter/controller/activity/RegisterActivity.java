package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.view.DisplayUtils;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends AppCompatActivity {

    @ViewById(R.id.username)
    EditText mUsername;
    @ViewById(R.id.nick)
    EditText mNick;
    @ViewById(R.id.password)
    EditText mPassword;
    @ViewById(R.id.confirm_password)
    EditText mConfirmPassword;
    @ViewById(R.id.btn_register)
    Button mBtnRegister;

    @Bean(ModelUser.class)
    IModelUser model;

    String username;
    String nickname;
    String password;

    @AfterViews void init(){
        DisplayUtils.initBackWithTitle(this, "账户注册");
    }

    @Click(R.id.btn_register)
    public void checkedInput() {
        username = mUsername.getText().toString().trim();
        nickname = mNick.getText().toString().trim();
        password = mPassword.getText().toString().trim();
        String confirmPwd = mConfirmPassword.getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
            mUsername.requestFocus();
            return;
        }else if(!username.matches("[a-zA-Z]\\w{5,15}")){
            CommonUtils.showShortToast(R.string.illegal_user_name);
            mUsername.requestFocus();
            return;
        }else if(TextUtils.isEmpty(nickname)){
            CommonUtils.showShortToast(R.string.nick_name_connot_be_empty);
            mNick.requestFocus();
            return;
        }else if(TextUtils.isEmpty(password)){
            CommonUtils.showShortToast(R.string.password_connot_be_empty);
            mPassword.requestFocus();
            return;
        }else if(TextUtils.isEmpty(confirmPwd)){
            CommonUtils.showShortToast(R.string.confirm_password_connot_be_empty);
            mConfirmPassword.requestFocus();
            return;
        }else if(!password.equals(confirmPwd)){
            CommonUtils.showShortToast(R.string.two_input_password);
            mConfirmPassword.requestFocus();
            return;
        }
        register();
    }

    private void register() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.registering));
        pd.show();
        model.register(this, username, nickname, password, new OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                pd.dismiss();
                if(result==null){
                    CommonUtils.showShortToast(R.string.register_fail);
                }else{
                    if(result.isRetMsg()){
                        CommonUtils.showLongToast(R.string.register_success);
                        setResult(RESULT_OK,new Intent().putExtra(I.User.USER_NAME,username));
                        MFGT.finish(RegisterActivity.this);
                    }else{
                        CommonUtils.showLongToast(R.string.register_fail_exists);
                        mUsername.requestFocus();
                    }
                }
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showShortToast(error);
            }
        });
    }

    @Click void backClickArea(){
        MFGT.finish(this);
    }
}
