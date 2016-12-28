package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;

import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.view.ChildViewHolder;
import cn.ucai.fulicenter.view.ChildViewHolder_;
import cn.ucai.fulicenter.view.GroupViewHolder;
import cn.ucai.fulicenter.view.GroupViewHolder_;

/**
 * Created by clawpo on 2016/12/28.
 */

@EBean
public class CategoryAdapter extends BaseExpandableListAdapter {
    @RootContext
    Context context;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;

    @AfterInject
    void initAdapter() {
        mGroupList = new ArrayList<>();
        mChildList = new ArrayList<>();
    }

    @Override
    public int getGroupCount() {
        return mGroupList != null ? mGroupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList != null && mChildList.get(groupPosition) != null ?
                mChildList.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return mGroupList != null ? mGroupList.get(groupPosition) : null;
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return mChildList != null && mChildList.get(groupPosition) != null ?
                mChildList.get(groupPosition).get(childPosition) : null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder;
        if (view == null) {
            groupViewHolder = GroupViewHolder_.build(context);
        } else {
            groupViewHolder = (GroupViewHolder) view;
        }
        groupViewHolder.bind(getGroup(i),b);
        return groupViewHolder;
    }

    @Override
    public View getChildView(final int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (view == null) {
            childViewHolder = ChildViewHolder_.build(context);
        } else {
            childViewHolder = (ChildViewHolder) view;
        }
        final CategoryChildBean child = getChild(i, i1);
        childViewHolder.bind(child);
        childViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<CategoryChildBean> list = mChildList.get(i);
                String groupName = mGroupList.get(i).getName();
                MFGT.gotoCategoryChildActivity(context,child.getId(),groupName,list);
            }
        });
        return childViewHolder;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public void initData(ArrayList<CategoryGroupBean> groupList,
                         ArrayList<ArrayList<CategoryChildBean>> childList) {
        if(mGroupList!=null){
            mGroupList.clear();
        }
        mGroupList.addAll(groupList);
        if(mChildList!=null){
            mChildList.clear();
        }
        mChildList.addAll(childList);
        notifyDataSetChanged();
    }

}
