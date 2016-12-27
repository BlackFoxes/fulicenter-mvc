package cn.ucai.fulicenter.model.net;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.androidannotations.annotations.EBean;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

@EBean
public class ModelNewGoods implements IModelNewGoods{

    @Override
    public void downData(Context context, int catId, int pageId, OnCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(catId))
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }

    @Override
    public void dowanGoodsAvatar(Context context, String goodsThumb, ImageView iv,int state, ViewGroup parent) {
//        ImageLoader.build(I.SERVER_ROOT+ I.REQUEST_DOWNLOAD_IMAGE)
//                .addParam(I.Boutique.IMAGE_URL,goodsThumb)
//                .defaultPicture(R.drawable.nopic)
//                .imageView(iv)
//                .width(160)
//                .height(240)
//                .setDragging(state== RecyclerView.SCROLL_STATE_IDLE)
//                .listener(parent)
//                .showImage(context);
    }
}
