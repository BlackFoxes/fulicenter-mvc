package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.model.net.IModelGoodsDetail;
import cn.ucai.fulicenter.model.net.ModelGoodsDetail;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.view.CartViewHolder;
import cn.ucai.fulicenter.view.CartViewHolder_;


/**
 * Created by clawpo on 2016/10/17.
 */

@EBean
public class CartAdapter extends RecyclerViewAdapterBase<CartBean,CartViewHolder> {
    @RootContext
    Context mContext;

    @Bean(ModelGoodsDetail.class)
    IModelGoodsDetail model;

    @Override
    public void onBindViewHolder(ViewWrapper<CartViewHolder> holder, final int position) {
        final CartViewHolder viewHolder = holder.getView();
        viewHolder.bind(items.get(position));
        viewHolder.setAddCartListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartBean cart = items.get(position);
                model.updateCart(mContext, cart.getId(), cart.getCount() + 1, new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if(result!=null && result.isSuccess()){
                            items.get(position).setCount(items.get(position).getCount()+1);
                            mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                            viewHolder.setCountText("("+(items.get(position).getCount())+")");
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        });
        viewHolder.setDelCartListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartBean cart = items.get(position);
                if(cart.getCount()>1) {
                    model.updateCart(mContext, cart.getId(), cart.getCount() - 1, new OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result != null && result.isSuccess()) {
                                items.get(position).setCount(items.get(position).getCount() - 1);
                                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                                viewHolder.setCountText("(" + (items.get(position).getCount()) + ")");
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                }else{
                    model.deleteCart(mContext, cart.getId(), new OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if(result!=null && result.isSuccess()){
                                items.remove(position);
                                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    protected CartViewHolder onCreateItemView(ViewGroup parent, int viewType) {
        return CartViewHolder_.build(mContext);
    }
    public void initData(ArrayList<CartBean> list) {
//        if(items!=null){
//            items.clear();
//        }
//        items.addAll(list);
        items = list;
        notifyDataSetChanged();
    }

}
