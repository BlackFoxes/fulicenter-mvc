package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;

import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.view.BoutiqueViewHolder;
import cn.ucai.fulicenter.view.BoutiqueViewHolder_;


/**
 * Created by clawpo on 2016/10/17.
 */

@EBean
public class BoutiqueAdapter extends RecyclerViewAdapterBase<BoutiqueBean,BoutiqueViewHolder> {
    @RootContext
    Context mContext;


    @Override
    public void onBindViewHolder(ViewWrapper<BoutiqueViewHolder> holder, int position) {
        holder.getView().bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    protected BoutiqueViewHolder onCreateItemView(ViewGroup parent, int viewType) {
        return BoutiqueViewHolder_.build(mContext);
    }
    public void initData(ArrayList<BoutiqueBean> list) {
        if(items!=null){
            items.clear();
        }
        items.addAll(list);
        notifyDataSetChanged();
    }

}
