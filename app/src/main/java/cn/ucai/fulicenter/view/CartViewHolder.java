package cn.ucai.fulicenter.view;

import android.content.Context;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

@EViewGroup(R.layout.item_cart)
public class CartViewHolder extends LinearLayout {

    @ViewById(R.id.cb_cart_selected)
    CheckBox mCbCartSelected;
    @ViewById(R.id.iv_cart_thumb)
    ImageView mIvCartThumb;
    @ViewById(R.id.tv_cart_good_name)
    TextView mTvCartGoodName;
    @ViewById(R.id.iv_cart_add)
    ImageView mIvCartAdd;
    @ViewById(R.id.tv_cart_count)
    TextView mTvCartCount;
    @ViewById(R.id.iv_cart_del)
    ImageView mIvCartDel;
    @ViewById(R.id.tv_cart_price)
    TextView mTvCartPrice;
    Context mContext;

    public CartViewHolder(Context context) {
        super(context);
        mContext = context;
    }

    public void bind(final CartBean cartBean){
        GoodsDetailsBean goods = cartBean.getGoods();
        if(goods!=null) {
            ImageLoader.downloadImg(mContext, mIvCartThumb, goods.getGoodsThumb());
            mTvCartGoodName.setText(goods.getGoodsName());
            mTvCartPrice.setText(goods.getCurrencyPrice());
        }
        mTvCartCount.setText("("+cartBean.getCount()+")");
        mCbCartSelected.setChecked(cartBean.isChecked());
        mCbCartSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cartBean.setChecked(b);
                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
            }
        });
//        mIvCartAdd.setTag(position);
    }

    public void setAddCartListener(OnClickListener listener){
        mIvCartAdd.setOnClickListener(listener);
    }

    public void setDelCartListener(OnClickListener listener){
        mIvCartDel.setOnClickListener(listener);
    }

    public void setCountText(String countText){
        mTvCartCount.setText(countText);
    }
}
