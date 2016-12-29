package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.model.dao.UserDao;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.view.DisplayUtils;

@EActivity(R.layout.activity_update_nick)
public class UpdateNickActivity extends AppCompatActivity {


    @ViewById(R.id.et_update_user_name)
    EditText mEtUpdateUserName;
    User user = null;
    @Bean(ModelUser.class)
    IModelUser model;


    @AfterViews void initView() {
        DisplayUtils.initBackWithTitle(this,getResources().getString(R.string.update_user_nick));
        initData();
    }

    void initData() {
        user = FuLiCenterApplication.getUser();
        if(user!=null){
            mEtUpdateUserName.setText(user.getMuserNick());
            mEtUpdateUserName.setSelectAllOnFocus(true);
        }else{
            finish();
        }
    }

    @Click(R.id.btn_save)
    public void checkNick() {
        if(user!=null){
            String nick = mEtUpdateUserName.getText().toString().trim();
            if(nick.equals(user.getMuserNick())){
                CommonUtils.showLongToast(R.string.update_nick_fail_unmodify);
            }else if(TextUtils.isEmpty(nick)){
                CommonUtils.showLongToast(R.string.nick_name_connot_be_empty);
            }else{
                updateNick(nick);
            }
        }
    }

    private void updateNick(String nick) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.update_user_nick));
        pd.show();
        model.updateNick(this, user.getMuserName(), nick, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s,User.class);
                if(result==null){
                    CommonUtils.showLongToast(R.string.update_fail);
                }else{
                    if(result.isRetMsg()){
                        User u = (User) result.getRetData();
                        UserDao dao = new UserDao(UpdateNickActivity.this);
                        boolean isSuccess = dao.updateUser(u);
                        if(isSuccess){
                            FuLiCenterApplication.setUser(u);
                            setResult(RESULT_OK);
                            MFGT.finish(UpdateNickActivity.this);
                        }else{
                            CommonUtils.showLongToast(R.string.user_database_error);
                        }
                    }else{
                        if(result.getRetCode()== I.MSG_USER_SAME_NICK){
                            CommonUtils.showLongToast(R.string.update_nick_fail_unmodify);
                        }else if(result.getRetCode()==I.MSG_USER_UPDATE_NICK_FAIL){
                            CommonUtils.showLongToast(R.string.update_fail);
                        }else{
                            CommonUtils.showLongToast(R.string.update_fail);
                        }
                    }
                }
                pd.dismiss();
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showLongToast(error);
            }
        });
    }
}
