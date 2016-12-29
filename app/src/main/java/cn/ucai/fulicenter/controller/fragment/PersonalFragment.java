package cn.ucai.fulicenter.controller.fragment;

import android.support.v4.app.Fragment;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.model.dao.UserDao;
import cn.ucai.fulicenter.model.net.IModelGoodsDetail;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelGoodsDetail;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.model.utils.ResultUtils;

/**
 * Created by clawpo on 2016/12/27.
 */

@EFragment(R.layout.fragment_personal_center)
public class PersonalFragment extends Fragment {

    @ViewById(R.id.iv_user_avatar)
    ImageView mIvUserAvatar;
    @ViewById(R.id.tv_user_name)
    TextView mTvUserName;

    @ViewById(R.id.center_user_order_lis)
    GridView mCenterUserOrderLis;
    @ViewById(R.id.tv_collect_count)
    TextView mTvCollectCount;
    User user = null;

    @Bean(ModelUser.class)
    IModelUser model;
    @Bean(ModelGoodsDetail.class)
    IModelGoodsDetail detailModel;

    @AfterViews void init(){
        initOrderList();
        initUser();
    }

    private void initUser() {
        user = FuLiCenterApplication.getUser();
        if (user == null) {
            MFGT.gotoLogin(getContext());
        } else {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), getContext(), mIvUserAvatar);
            mTvUserName.setText(user.getMuserNick());
        }
    }

    private void initOrderList() {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> order1 = new HashMap<String, Object>();
        order1.put("order", R.drawable.order_list1);
        data.add(order1);
        HashMap<String, Object> order2 = new HashMap<String, Object>();
        order2.put("order", R.drawable.order_list2);
        data.add(order2);
        HashMap<String, Object> order3 = new HashMap<String, Object>();
        order3.put("order", R.drawable.order_list3);
        data.add(order3);
        HashMap<String, Object> order4 = new HashMap<String, Object>();
        order4.put("order", R.drawable.order_list4);
        data.add(order4);
        HashMap<String, Object> order5 = new HashMap<String, Object>();
        order5.put("order", R.drawable.order_list5);
        data.add(order5);
        SimpleAdapter adapter = new SimpleAdapter(getContext(), data, R.layout.simple_adapter,
                new String[]{"order"}, new int[]{R.id.iv_order});
        mCenterUserOrderLis.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        user = FuLiCenterApplication.getUser();
        if (user != null) {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), getContext(), mIvUserAvatar);
            mTvUserName.setText(user.getMuserNick());
            syncUserInfo();
            syncCollectsCount();
        }
    }

    private void syncUserInfo() {
        model.syncUserInfo(getActivity(), user.getMuserName(), new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, User.class);
                if (result != null) {
                    User u = (User) result.getRetData();
                    if (!user.equals(u)) {
                        UserDao dao = new UserDao(getContext());
                        boolean b = dao.saveUser(u);
                        if (b) {
                            FuLiCenterApplication.setUser(u);
                            user = u;
                            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), getContext(), mIvUserAvatar);
                            mTvUserName.setText(user.getMuserNick());
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void syncCollectsCount() {
        detailModel.getCollectsCount(getActivity(), user.getMuserName(), new OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null && result.isSuccess()) {
                    mTvCollectCount.setText(result.getMsg());
                } else {
                    mTvCollectCount.setText(String.valueOf(0));
                }
            }

            @Override
            public void onError(String error) {
                mTvCollectCount.setText(String.valueOf(0));
            }
        });
    }
}
