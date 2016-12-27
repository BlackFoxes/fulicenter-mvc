package cn.ucai.fulicenter.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.L;

@EViewGroup(R.layout.item_goods)
public class GoodsViewHolder extends LinearLayout {

    @ViewById
    ImageView ivGoodsThumb;
    @ViewById
    TextView tvGoodsName;
    @ViewById
    TextView tvGoodsPrice;
    @ViewById
    LinearLayout layoutGoods;
    Context mContext;

    public GoodsViewHolder(Context context) {
        super(context);
        mContext = context;
    }

    public void bind(NewGoodsBean goods){
        ImageLoader.downloadImg(mContext,ivGoodsThumb,goods.getGoodsThumb());
        tvGoodsName.setText(goods.getGoodsName());
        tvGoodsPrice.setText(goods.getCurrencyPrice());
        layoutGoods.setTag(goods.getGoodsId());
    }

    @Click
    void layoutGoods(){
        int goodsId = (int) layoutGoods.getTag();
        L.e("goodsId="+goodsId);
//        MFGT.gotoGoodsDetailsActivity(mContext,goodsId);
    }
}
