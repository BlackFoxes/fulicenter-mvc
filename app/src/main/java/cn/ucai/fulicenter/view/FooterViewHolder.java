package cn.ucai.fulicenter.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import cn.ucai.fulicenter.R;

@EViewGroup(R.layout.item_footer)
public class FooterViewHolder extends LinearLayout {

    @ViewById
    public TextView tvFooter;

    public FooterViewHolder(Context context) {
        super(context);
    }

    public void bind(int footerString){
        tvFooter.setText(footerString);
    }
}
