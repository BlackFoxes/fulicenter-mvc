package cn.ucai.fulicenter.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

/**
 * Created by clawpo on 2016/12/28.
 */

@EViewGroup(R.layout.item_category_group)
public class GroupViewHolder extends LinearLayout {

    @ViewById(R.id.iv_group_thumb)
    ImageView mIvGroupThumb;
    @ViewById(R.id.tv_group_name)
    TextView mTvGroupName;
    @ViewById(R.id.iv_indicator)
    ImageView mIvIndicator;
    Context mContext;

    public GroupViewHolder(Context context) {
        super(context);
        mContext = context;
    }
    
    public void bind(CategoryGroupBean group, boolean isExpand){
        ImageLoader.downloadImg(mContext, mIvGroupThumb, group.getImageUrl());
        mTvGroupName.setText(group.getName());
        mIvIndicator.setImageResource(isExpand ? R.mipmap.expand_off : R.mipmap.expand_on);
    }
}
