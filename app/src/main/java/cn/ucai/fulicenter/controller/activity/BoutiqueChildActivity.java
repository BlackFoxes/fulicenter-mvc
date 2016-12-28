package cn.ucai.fulicenter.controller.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.controller.adapter.GoodsAdapter;
import cn.ucai.fulicenter.model.net.IModelNewGoods;
import cn.ucai.fulicenter.model.net.ModelNewGoods;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.view.SpaceItemDecoration;

@EActivity(R.layout.activity_boutique_child)
public class BoutiqueChildActivity extends AppCompatActivity {

    @ViewById(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @ViewById
    RecyclerView rv;
    @ViewById
    SwipeRefreshLayout srl;
    @ViewById
    TextView tv_refresh;
    GridLayoutManager glm;

    @Bean
    GoodsAdapter adapter;
    @Bean(ModelNewGoods.class)
    IModelNewGoods model;
    int pageId = 1;

    @Extra(I.Boutique.CAT_ID)
    BoutiqueBean boutique;

    @AfterViews void init(){
        L.e("BoutiqueChildActivity","boutique="+boutique);
        if(boutique == null){
            finish();
        }else{
            srl.setColorSchemeColors(
                    getResources().getColor(R.color.google_blue),
                    getResources().getColor(R.color.google_green),
                    getResources().getColor(R.color.google_red),
                    getResources().getColor(R.color.google_yellow)
            );
            glm = new GridLayoutManager(this, I.COLUM_NUM);
            rv.setLayoutManager(glm);
            rv.setHasFixedSize(true);
            rv.setAdapter(adapter);
            rv.addItemDecoration(new SpaceItemDecoration(12));
            mTvCommonTitle.setText(boutique.getTitle());
            initData(I.ACTION_DOWNLOAD);
            setPullUpListener();
            setPullDownListener();
        }

    }
    void initData(final int action){
        //获取cat_id
        int catId=boutique.getId();
        model.downData(this, catId, pageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                srl.setRefreshing(false);
                tv_refresh.setVisibility(View.GONE);
                adapter.setMore(true);
                if(result!=null && result.length>0){
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    if(action==I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        adapter.initData(list);
                    }else{
                        adapter.addData(list);
                    }
                    if(list.size()<I.PAGE_SIZE_DEFAULT){
                        adapter.setMore(false);
                    }
                }else{
                    adapter.setMore(false);
                }
            }

            @Override
            public void onError(String error) {
                srl.setRefreshing(false);
                tv_refresh.setVisibility(View.GONE);
                adapter.setMore(false);
                CommonUtils.showShortToast(error);
                L.e("error:"+error);
            }
        });
    }
    private void setPullDownListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                tv_refresh.setVisibility(View.VISIBLE);
                pageId = 1;
                initData(I.ACTION_PULL_DOWN);
            }
        });
    }
    private void setPullUpListener() {
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = glm.findLastVisibleItemPosition();
                if(newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastPosition == adapter.getItemCount()-1
                        && adapter.isMore()){
                    pageId++;
                    initData(I.ACTION_PULL_UP);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = glm.findFirstVisibleItemPosition();
                srl.setEnabled(firstPosition==0);
            }
        });
    }
    @Click void backClickArea(){
        MFGT.finish(this);
    }
}
