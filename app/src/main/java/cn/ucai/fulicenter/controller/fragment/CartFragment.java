package cn.ucai.fulicenter.controller.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.controller.adapter.CartAdapter;
import cn.ucai.fulicenter.model.net.IModelGoodsDetail;
import cn.ucai.fulicenter.model.net.ModelGoodsDetail;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.view.SpaceItemDecoration;

/**
 * Created by clawpo on 2016/12/27.
 */

@EFragment(R.layout.fragment_cart)
public class CartFragment extends Fragment {
    private static final String TAG = CartFragment.class.getSimpleName();
    @ViewById(R.id.tv_refresh)
    TextView mTvRefresh;
    @ViewById(R.id.rv)
    RecyclerView mRv;
    @ViewById(R.id.srl)
    SwipeRefreshLayout mSrl;
    LinearLayoutManager llm;
    @Bean
    CartAdapter mAdapter;
    ArrayList<CartBean> mList = new ArrayList<>();
    @ViewById(R.id.tv_cart_sum_price)
    TextView mTvCartSumPrice;
    @ViewById(R.id.tv_cart_save_price)
    TextView mTvCartSavePrice;
    @ViewById(R.id.layout_cart)
    RelativeLayout mLayoutCart;
    @ViewById(R.id.tv_nothing)
    TextView mTvNothing;

    @Bean(ModelGoodsDetail.class)
    IModelGoodsDetail model;

    updateCartReceiver mReceiver;
    String cartIds="";
    @AfterViews void init(){
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        llm = new LinearLayoutManager(getContext());
        mRv.setLayoutManager(llm);
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(new SpaceItemDecoration(12));
        setCartLayout(false);

        setListener();
        downloadCart();
    }
    protected void setListener() {
        setPullDownListener();
        IntentFilter filter = new IntentFilter(I.BROADCAST_UPDATA_CART);
        mReceiver = new updateCartReceiver();
        getContext().registerReceiver(mReceiver,filter);
    }

    private void setPullDownListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                mTvRefresh.setVisibility(View.VISIBLE);
                downloadCart();
            }
        });
    }


    private void downloadCart() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            model.downloadCart(getContext(), user.getMuserName(), new OnCompleteListener<String>() {
                @Override
                public void onSuccess(String s) {
                    ArrayList<CartBean> list = ResultUtils.getCartFromJson(s);
                    L.e(TAG, "result=" + list);
                    mSrl.setRefreshing(false);
                    mTvRefresh.setVisibility(View.GONE);
                    if (list != null && list.size() > 0) {
                        mList.clear();
                        mList.addAll(list);
                        mAdapter.initData(mList);
                        setCartLayout(true);
                    }else{
                        setCartLayout(false);
                    }
                }

                @Override
                public void onError(String error) {
                    setCartLayout(false);
                    mSrl.setRefreshing(false);
                    mTvRefresh.setVisibility(View.GONE);
                    CommonUtils.showShortToast(error);
                    L.e("error:" + error);
                }
            });
        }
    }

    private void setCartLayout(boolean hasCart) {
        mLayoutCart.setVisibility(hasCart?View.VISIBLE:View.GONE);
        mTvNothing.setVisibility(hasCart?View.GONE:View.VISIBLE);
        mRv.setVisibility(hasCart?View.VISIBLE:View.GONE);
        sumPrice();
    }

    @Click(R.id.tv_cart_buy)
    public void buy() {
        if(cartIds!=null && !cartIds.equals("") && cartIds.length()>0){
            MFGT.gotoBuy(getContext(),cartIds);
        }else{
            CommonUtils.showLongToast(R.string.order_nothing);
        }
    }

    private void sumPrice(){
        cartIds = "";
        int sumPrice = 0;
        int rankPrice = 0;
        if(mList!=null && mList.size()>0){
            for (CartBean c:mList){
                if(c.isChecked()){
                    cartIds += c.getId()+",";
                    sumPrice += getPrice(c.getGoods().getCurrencyPrice())*c.getCount();
                    rankPrice += getPrice(c.getGoods().getRankPrice())*c.getCount();
                }
            }
            mTvCartSumPrice.setText("合计:￥"+Double.valueOf(rankPrice));
            mTvCartSavePrice.setText("节省:￥"+Double.valueOf(sumPrice-rankPrice));

        }else{
            cartIds = "";
            mTvCartSumPrice.setText("合计:￥0");
            mTvCartSavePrice.setText("节省:￥0");
        }
    }
    private int getPrice(String price){
        price = price.substring(price.indexOf("￥")+1);
        return Integer.valueOf(price);
    }
    class updateCartReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            L.e(TAG,"updateCartReceiver...");
            sumPrice();
            setCartLayout(mList!=null&&mList.size()>0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mReceiver!=null){
            getContext().unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        L.e(TAG,"onResume.......");
        downloadCart();
    }
}
