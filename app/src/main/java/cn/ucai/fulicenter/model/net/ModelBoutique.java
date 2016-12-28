package cn.ucai.fulicenter.model.net;

import android.content.Context;

import org.androidannotations.annotations.EBean;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by clawpo on 2016/12/28.
 */

@EBean
public class ModelBoutique implements IModelBoutique {
    @Override
    public void downloadBuotique(Context context, OnCompleteListener<BoutiqueBean[]> listener) {
        OkHttpUtils<BoutiqueBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }
}
