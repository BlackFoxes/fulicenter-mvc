package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;

/**
 * Created by clawpo on 2016/12/28.
 */

public interface IModelCategory {
    void downloadCategoryGroup(Context context, OnCompleteListener<CategoryGroupBean[]> listener);
    void downloadCategoryChild(Context context,int parentId, OnCompleteListener<CategoryChildBean[]> listener);
}
