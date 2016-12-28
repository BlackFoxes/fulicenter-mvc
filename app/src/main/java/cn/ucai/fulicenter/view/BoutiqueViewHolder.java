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
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

@EViewGroup(R.layout.item_boutique)
public class BoutiqueViewHolder extends LinearLayout {

    @ViewById(R.id.ivBoutiqueImg)
    ImageView mIvBoutiqueImg;
    @ViewById(R.id.tvBoutiqueTitle)
    TextView mTvBoutiqueTitle;
    @ViewById(R.id.tvBoutiqueName)
    TextView mTvBoutiqueName;
    @ViewById(R.id.tvBoutiqueDescription)
    TextView mTvBoutiqueDescription;
    @ViewById(R.id.layout_boutique_item)
    RelativeLayout mLayoutBoutiqueItem;
    Context mContext;

    public BoutiqueViewHolder(Context context) {
        super(context);
        mContext = context;
    }

    public void bind(BoutiqueBean boutiqueBean){
        ImageLoader.downloadImg(mContext,mIvBoutiqueImg,boutiqueBean.getImageurl());
        mTvBoutiqueTitle.setText(boutiqueBean.getTitle());
        mTvBoutiqueName.setText(boutiqueBean.getName());
        mTvBoutiqueDescription.setText(boutiqueBean.getDescription());
        mLayoutBoutiqueItem.setTag(boutiqueBean);
    }

    @Click void layout_boutique_item(){
        BoutiqueBean bean = (BoutiqueBean) mLayoutBoutiqueItem.getTag();
//        MFGT.gotoBoutiqueChildActivity(mContext,bean);
    }
}
