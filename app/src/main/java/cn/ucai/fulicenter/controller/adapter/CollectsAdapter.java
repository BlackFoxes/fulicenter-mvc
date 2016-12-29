package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.model.net.IModelGoodsDetail;
import cn.ucai.fulicenter.model.net.ModelGoodsDetail;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.view.CollectsViewHolder;
import cn.ucai.fulicenter.view.CollectsViewHolder_;
import cn.ucai.fulicenter.view.FooterViewHolder;
import cn.ucai.fulicenter.view.FooterViewHolder_;


/**
 * Created by clawpo on 2016/10/17.
 */

@EBean
public class CollectsAdapter extends RecyclerViewAdapterBase<CollectBean,View> {
    @RootContext
    Context mContext;
    boolean isMore;
    @Bean(ModelGoodsDetail.class)
    IModelGoodsDetail model;

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        View holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = FooterViewHolder_.build(mContext);
        } else {
            holder = CollectsViewHolder_.build(mContext);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewWrapper<View> holder, final int position) {
        if(getItemViewType(position)==I.TYPE_FOOTER){
            FooterViewHolder viewHolder = (FooterViewHolder) holder.getView();
            viewHolder.bind(getFootString());
        }else{
            CollectsViewHolder viewHolder = (CollectsViewHolder) holder.getView();
            viewHolder.bind(items.get(position));
            viewHolder.setDeleteListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CollectBean goods = items.get(position);
                    String username = FuLiCenterApplication.getUser().getMuserName();
                    model.deleteCollect(mContext, username, goods.getGoodsId(), new OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if(result!=null && result.isSuccess()){
                                items.remove(position);
                                notifyDataSetChanged();
                            }else{
                                CommonUtils.showLongToast(result!=null?result.getMsg():
                                        mContext.getResources().getString(R.string.delete_collect_fail));
                            }
                        }

                        @Override
                        public void onError(String error) {
                            L.e("error="+error);
                            CommonUtils.showLongToast(mContext.getResources().getString(R.string.delete_collect_fail));
                        }
                    });
                }
            });
        }
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

    public void initData(ArrayList<CollectBean> list) {
        if(items!=null){
            items.clear();
        }
        items.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<CollectBean> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

}
