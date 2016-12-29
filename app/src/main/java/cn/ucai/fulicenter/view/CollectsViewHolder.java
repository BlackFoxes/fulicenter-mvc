package cn.ucai.fulicenter.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;

import static cn.ucai.fulicenter.R.id.iv_collect_del;

@EViewGroup(R.layout.item_collects)
public class CollectsViewHolder extends LinearLayout {

    @ViewById(R.id.ivGoodsThumb)
    ImageView mIvGoodsThumb;
    @ViewById(R.id.tvGoodsName)
    TextView mTvGoodsName;
    @ViewById(iv_collect_del)
    ImageView mIvCollectDel;
    @ViewById(R.id.layout_goods)
    RelativeLayout mLayoutGoods;


    Context mContext;

    public CollectsViewHolder(Context context) {
        super(context);
        mContext = context;
    }

    public void bind(CollectBean goods){
        ImageLoader.downloadImg(mContext,mIvGoodsThumb,goods.getGoodsThumb());
        mTvGoodsName.setText(goods.getGoodsName());
        mLayoutGoods.setTag(goods);
    }

    @Click(R.id.layout_goods)
    public void onGoodsItemClick(){
        CollectBean goods = (CollectBean) mLayoutGoods.getTag();
        MFGT.gotoGoodsDetailsActivity(mContext,goods.getGoodsId());
    }

    public void setDeleteListener(OnClickListener listener){
        mIvCollectDel.setOnClickListener(listener);
    }

}
