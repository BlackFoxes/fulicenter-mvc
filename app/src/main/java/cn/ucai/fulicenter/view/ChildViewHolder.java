package cn.ucai.fulicenter.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

/**
 * Created by clawpo on 2016/12/28.
 */

@EViewGroup(R.layout.item_category_child)
public class ChildViewHolder extends LinearLayout {

    @ViewById(R.id.iv_category_child_thumb)
    ImageView mIvCategoryChildThumb;
    @ViewById(R.id.tv_category_child_name)
    TextView mTvCategoryChildName;
    @ViewById(R.id.layout_category_child)
    RelativeLayout mLayoutCategoryChild;
    Context mContext;

    public ChildViewHolder(Context context) {
        super(context);
        mContext = context;
    }
    
    public void bind(CategoryChildBean child){
        ImageLoader.downloadImg(mContext,mIvCategoryChildThumb,child.getImageUrl());
        mTvCategoryChildName.setText(child.getName());

    }
}
