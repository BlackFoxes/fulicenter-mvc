package cn.ucai.fulicenter.controller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.AlbumsBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.model.net.IModelGoodsDetail;
import cn.ucai.fulicenter.model.net.ModelGoodsDetail;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.view.FlowIndicator;
import cn.ucai.fulicenter.view.SlideAutoLoopView;

@EActivity(R.layout.activity_goods_detail)
public class GoodsDetailActivity extends AppCompatActivity {


    @ViewById(R.id.backClickArea)
    LinearLayout mBackClickArea;
    @ViewById(R.id.tv_good_name_english)
    TextView mTvGoodNameEnglish;
    @ViewById(R.id.tv_good_name)
    TextView mTvGoodName;
    @ViewById(R.id.tv_good_price_shop)
    TextView mTvGoodPriceShop;
    @ViewById(R.id.tv_good_price_current)
    TextView mTvGoodPriceCurrent;
    @ViewById(R.id.salv)
    SlideAutoLoopView mSalv;
    @ViewById(R.id.indicator)
    FlowIndicator mIndicator;
    @ViewById(R.id.wv_good_brief)
    WebView mWvGoodBrief;
    @ViewById(R.id.iv_good_collect)
    ImageView mIvGoodCollect;

    int goodsId;
    boolean isCollected = false;

    @Bean(ModelGoodsDetail.class)
    IModelGoodsDetail model;

    @AfterViews void initData(){
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.e("details", "goodsid=" + goodsId);
        if (goodsId == 0) {
            finish();
        }else{
            downData();
        }
    }

    private void downData() {
        model.downloadGoodsDetail(this, goodsId, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result != null) {
                    showGoodDetails(result);
                } else {
                    finish();
                }
            }

            @Override
            public void onError(String error) {
                finish();
                L.e("details,error=" + error);
                CommonUtils.showShortToast(error);
            }
        });
    }

    private void showGoodDetails(GoodsDetailsBean details) {
        mTvGoodNameEnglish.setText(details.getGoodsEnglishName());
        mTvGoodName.setText(details.getGoodsName());
        mTvGoodPriceCurrent.setText(details.getCurrencyPrice());
        mTvGoodPriceShop.setText(details.getShopPrice());
        mSalv.startPlayLoop(mIndicator, getAlbumImgUrl(details), getAlbumImgCount(details));
        mWvGoodBrief.loadDataWithBaseURL(null, details.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
    }

    private int getAlbumImgCount(GoodsDetailsBean details) {
        if (details.getProperties() != null && details.getProperties().length > 0) {
            return details.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumImgUrl(GoodsDetailsBean details) {
        String[] urls = new String[]{};
        if (details.getProperties() != null && details.getProperties().length > 0) {
            AlbumsBean[] albums = details.getProperties()[0].getAlbums();
            urls = new String[albums.length];
            for (int i = 0; i < albums.length; i++) {
                urls[i] = albums[i].getImgUrl();
            }
        }
        return urls;
    }

    @Click void backClickArea(){
        MFGT.finish(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isCollected();
    }
    private void isCollected() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            model.isColected(this, user.getMuserName(), goodsId, new OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        isCollected = true;
                    }else{
                        isCollected = false;
                    }
                    updateGoodsCollectStatus();
                }

                @Override
                public void onError(String error) {
                    isCollected = false;
                    updateGoodsCollectStatus();
                }
            });
        }
        updateGoodsCollectStatus();
    }

    private void updateGoodsCollectStatus() {
        if (isCollected) {
            mIvGoodCollect.setImageResource(R.mipmap.bg_collect_out);
        } else {
            mIvGoodCollect.setImageResource(R.mipmap.bg_collect_in);
        }
    }

    @Click void iv_good_cart(){
        User user = FuLiCenterApplication.getUser();
        if(user!=null){
            model.addCart(this, user.getMuserName(), goodsId, new OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if(result!=null && result.isSuccess()){
                        CommonUtils.showLongToast(R.string.add_goods_success);
                    }else {
                        CommonUtils.showLongToast(R.string.add_goods_fail);
                    }
                }

                @Override
                public void onError(String error) {
                    CommonUtils.showLongToast(R.string.add_goods_fail);
                    L.e("error="+error);
                }
            });
        }else{
            MFGT.gotoLogin(this);
        }
    }

    @Click void iv_good_collect(){
        User user = FuLiCenterApplication.getUser();
        if(user==null){
            MFGT.gotoLogin(this);
        }else{
            if(isCollected){
                model.deleteCollect(this, user.getMuserName(), goodsId, new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if(result!=null && result.isSuccess()){
                            isCollected = !isCollected;
                            updateGoodsCollectStatus();
                            CommonUtils.showLongToast(result.getMsg());
                            sendStickyBroadcast(new Intent("update_collect").putExtra(I.Collect.GOODS_ID,goodsId));
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }else{
                model.addCollect(this, user.getMuserName(), goodsId, new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if(result!=null && result.isSuccess()){
                            isCollected = !isCollected;
                            updateGoodsCollectStatus();
                            CommonUtils.showLongToast(result.getMsg());
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        }
    }
}
