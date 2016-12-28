package cn.ucai.fulicenter.controller.fragment;

import android.support.v4.app.Fragment;
import android.widget.ExpandableListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.controller.adapter.CategoryAdapter;
import cn.ucai.fulicenter.model.net.IModelCategory;
import cn.ucai.fulicenter.model.net.ModelCategory;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.L;

/**
 * Created by clawpo on 2016/12/27.
 */

@EFragment(R.layout.fragment_category)
public class CategoryFragment extends Fragment {

    @ViewById(R.id.elv_category)
    ExpandableListView mElvCategory;
    @Bean
    CategoryAdapter mAdapter;
    @Bean(ModelCategory.class)
    IModelCategory model;

    int groupCount;
    ArrayList<CategoryGroupBean> mGroupList = new ArrayList<>();;
    ArrayList<ArrayList<CategoryChildBean>> mChildList = new ArrayList<>();

    @AfterViews void init(){
        mElvCategory.setGroupIndicator(null);
        mElvCategory.setAdapter(mAdapter);
        downloadGroup();
    }

    private void downloadGroup() {
        model.downloadCategoryGroup(getContext(), new OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if(result!=null && result.length>0){
                    ArrayList<CategoryGroupBean> groupList = ConvertUtils.array2List(result);
                    mGroupList.addAll(groupList);
                    for (int i=0;i<groupList.size();i++){
                        mChildList.add(new ArrayList<CategoryChildBean>());
                        CategoryGroupBean g = groupList.get(i);
                        downloadChild(g.getId(),i);
                    }
                }
            }

            @Override
            public void onError(String error) {
                L.e("error="+error);
            }
        });
    }

    private void downloadChild(int id,final int index) {
        model.downloadCategoryChild(getContext(), id, new OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCount++;
                if(result!=null && result.length>0) {
                    ArrayList<CategoryChildBean> childList = ConvertUtils.array2List(result);
                    mChildList.set(index,childList);
                }
                if(groupCount==mGroupList.size()){
                    mAdapter.initData(mGroupList,mChildList);
                }

            }

            @Override
            public void onError(String error) {
                L.e("error="+error);
            }
        });
    }
}
