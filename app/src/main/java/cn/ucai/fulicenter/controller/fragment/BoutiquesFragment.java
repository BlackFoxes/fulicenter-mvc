package cn.ucai.fulicenter.controller.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.controller.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.model.net.IModelBoutique;
import cn.ucai.fulicenter.model.net.ModelBoutique;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.view.SpaceItemDecoration;

/**
 * Created by clawpo on 2016/12/27.
 */

@EFragment(R.layout.fragment_newgoods)
public class BoutiquesFragment extends Fragment {
    @ViewById(R.id.tv_refresh)
    TextView mTvRefresh;
    @ViewById(R.id.rv)
    RecyclerView mRv;
    @ViewById(R.id.srl)
    SwipeRefreshLayout mSrl;
    LinearLayoutManager llm;
    @Bean
    BoutiqueAdapter mAdapter;
    @Bean(ModelBoutique.class)
    IModelBoutique model;

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
        initData();
    }

    private void initData() {
        model.downloadBuotique(getContext(), new OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                mSrl.setRefreshing(false);
                mTvRefresh.setVisibility(View.GONE);
                if(result!=null && result.length>0){
                    ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                    mAdapter.initData(list);
                }
            }

            @Override
            public void onError(String error) {
                mSrl.setRefreshing(false);
                mTvRefresh.setVisibility(View.GONE);
                CommonUtils.showShortToast(error);
                L.e("error:"+error);
            }
        });
        setPullDownListener();
    }

    private void setPullDownListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                mTvRefresh.setVisibility(View.VISIBLE);
                initData();
            }
        });
    }

}
