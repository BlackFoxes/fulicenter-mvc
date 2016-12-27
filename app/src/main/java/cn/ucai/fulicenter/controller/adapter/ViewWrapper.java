package cn.ucai.fulicenter.controller.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by clawpo on 2016/12/27.
 */

public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder {

    private V view;

    public ViewWrapper(V itemView) {
        super(itemView);
        view = itemView;
    }

    public V getView() {
        return view;
    }
}
