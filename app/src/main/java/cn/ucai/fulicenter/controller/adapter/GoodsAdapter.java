package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.view.FooterViewHolder;
import cn.ucai.fulicenter.view.FooterViewHolder_;
import cn.ucai.fulicenter.view.GoodsViewHolder;
import cn.ucai.fulicenter.view.GoodsViewHolder_;


/**
 * Created by clawpo on 2016/10/17.
 */

@EBean
public class GoodsAdapter extends RecyclerViewAdapterBase<NewGoodsBean,View> {
    @RootContext
    Context mContext;
    boolean isMore;
    int soryBy = I.SORT_BY_ADDTIME_DESC;

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        View holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = FooterViewHolder_.build(mContext);
        } else {
            holder = GoodsViewHolder_.build(mContext);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewWrapper<View> holder, int position) {
        if(getItemViewType(position)==I.TYPE_FOOTER){
            FooterViewHolder viewHolder = (FooterViewHolder) holder.getView();
            viewHolder.bind(getFootString());
        }else{
            GoodsViewHolder viewHolder = (GoodsViewHolder) holder.getView();
            viewHolder.bind(items.get(position));
        }
    }

    public void setSoryBy(int soryBy) {
        this.soryBy = soryBy;
        sortBy();
        notifyDataSetChanged();
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() + 1 : 1;
    }


    private int getFootString() {
        return isMore? R.string.load_more:R.string.no_more;
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if(items!=null){
            items.clear();
        }
        items.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    private void sortBy(){
        Collections.sort(items, new Comparator<NewGoodsBean>() {
            @Override
            public int compare(NewGoodsBean left, NewGoodsBean right) {
                int result=0;
                switch (soryBy){
                    case I.SORT_BY_ADDTIME_ASC:
                        result= (int) (Long.valueOf(left.getAddTime())-Long.valueOf(right.getAddTime()));
                        break;
                    case I.SORT_BY_ADDTIME_DESC:
                        result= (int) (Long.valueOf(right.getAddTime())-Long.valueOf(left.getAddTime()));
                        break;
                    case I.SORT_BY_PRICE_ASC:
                        result = getPrice(left.getCurrencyPrice())-getPrice(right.getCurrencyPrice());
                        break;
                    case I.SORT_BY_PRICE_DESC:
                        result = getPrice(right.getCurrencyPrice())-getPrice(left.getCurrencyPrice());
                        break;
                }
                return result;
            }
            private int getPrice(String price){
                price = price.substring(price.indexOf("￥")+1);
                return Integer.valueOf(price);
            }
        });
    }
}
