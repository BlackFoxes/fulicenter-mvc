package cn.ucai.fulicenter.controller.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.fragment.BoutiquesFragment_;
import cn.ucai.fulicenter.controller.fragment.CartFragment_;
import cn.ucai.fulicenter.controller.fragment.CategoryFragment_;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment_;
import cn.ucai.fulicenter.controller.fragment.PersonalFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.layout_new_good)
    RadioButton mLayoutNewGood;
    @ViewById(R.id.layout_boutique)
    RadioButton mLayoutBoutique;
    @ViewById(R.id.layout_category)
    RadioButton mLayoutCategory;
    @ViewById(R.id.layout_cart)
    RadioButton mLayoutCart;
    @ViewById(R.id.layout_personal_center)
    RadioButton mLayoutPersonalCenter;

    Fragment mNewGoodsFragment,mBoutiqueFragment,mCategoryFragment,mCartFragment,mPersonalFragment;

    int index,currentIndex;
    Fragment[] mFragments;
    RadioButton[] rbs;

    @AfterViews void initView(){
        rbs = new RadioButton[5];
        rbs[0] = mLayoutNewGood;
        rbs[1] = mLayoutBoutique;
        rbs[2] = mLayoutCategory;
        rbs[3] = mLayoutCart;
        rbs[4] = mLayoutPersonalCenter;
        mFragments = new Fragment[5];
        mFragments[0] = mNewGoodsFragment = new NewGoodsFragment_();
        mFragments[1] = mBoutiqueFragment = new BoutiquesFragment_();
        mFragments[2] = mCategoryFragment = new CategoryFragment_();
        mFragments[3] = mCartFragment = new CartFragment_();
        mFragments[4] = mPersonalFragment = new PersonalFragment_();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container,mNewGoodsFragment)
                .show(mNewGoodsFragment)
                .commit();
    }

    public void onCheckedChange(View view){
        switch (view.getId()){
            case R.id.layout_new_good:
                index = 0;
                break;
            case R.id.layout_boutique:
                index = 1;
                break;
            case R.id.layout_category:
                index = 2;
                break;
            case R.id.layout_cart:
                index = 3;
                break;
            case R.id.layout_personal_center:
                index = 4;
                break;
        }
        setFragment();
    }

    private void setFragment() {
        if(index!=currentIndex) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(mFragments[currentIndex]);
            if(!mFragments[index].isAdded()){
                ft.add(R.id.fragment_container,mFragments[index]);
            }
            ft.show(mFragments[index]).commit();
        }
        setRadioButtonStatus();
        currentIndex = index;
    }

    private void setRadioButtonStatus() {
        for (int i=0;i<rbs.length;i++){
            if(i==index){
                rbs[i].setChecked(true);
            }else{
                rbs[i].setChecked(false);
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(index == 4 && FuLiCenterApplication.getUser()==null){
            index = 0;
        }
        setFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(FuLiCenterApplication.getUser()!=null){
            if(requestCode == I.REQUEST_CODE_LOGIN) {
                index = 4;
            }
            if(requestCode == I.REQUEST_CODE_LOGIN_FROM_CART){
                index = 3;
            }
        }
    }
}
