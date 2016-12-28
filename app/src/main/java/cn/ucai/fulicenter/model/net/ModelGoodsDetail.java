package cn.ucai.fulicenter.model.net;

import android.content.Context;

import org.androidannotations.annotations.EBean;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by clawpo on 2016/12/28.
 */

@EBean
public class ModelGoodsDetail implements IModelGoodsDetail {
    @Override
    public void downloadGoodsDetail(Context context, int goodsId, OnCompleteListener<GoodsDetailsBean> listener) {
        OkHttpUtils<GoodsDetailsBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.GoodsDetails.KEY_GOODS_ID,String.valueOf(goodsId))
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }

    @Override
    public void deleteCollect(Context context, String username, int goodsId, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_COLLECT)
                .addParam(I.Collect.USER_NAME,username)
                .addParam(I.Collect.GOODS_ID,String.valueOf(goodsId))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void addCollect(Context context, String username, int goodsId, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_COLLECT)
                .addParam(I.Collect.USER_NAME,username)
                .addParam(I.Collect.GOODS_ID,String.valueOf(goodsId))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void isColected(Context context, String username, int goodsId, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_IS_COLLECT)
                .addParam(I.Collect.USER_NAME,username)
                .addParam(I.Collect.GOODS_ID,String.valueOf(goodsId))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void addCart(Context context, String username, int goodsId, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_CART)
                .addParam(I.Cart.USER_NAME,username)
                .addParam(I.Cart.GOODS_ID,String.valueOf(goodsId))
                .addParam(I.Cart.COUNT,String.valueOf(1))
                .addParam(I.Cart.IS_CHECKED,String.valueOf(I.CART_CHECKED_DEFAULT))
                .targetClass(MessageBean.class)
                .execute(listener);
    }
}
