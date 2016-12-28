package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;

/**
 * Created by clawpo on 2016/12/28.
 */

public interface IModelGoodsDetail {
    void downloadGoodsDetail(Context context, int goodsId, OnCompleteListener<GoodsDetailsBean> listener);
    void deleteCollect(Context context, String username, int goodsId, OnCompleteListener<MessageBean> listener);
    void addCollect(Context context, String username, int goodsId, OnCompleteListener<MessageBean> listener);
    void isColected(Context context,String username,int goodsId,OnCompleteListener<MessageBean> listener);
    void addCart(Context context,String username, int goodsId, OnCompleteListener<MessageBean> listener);
}
