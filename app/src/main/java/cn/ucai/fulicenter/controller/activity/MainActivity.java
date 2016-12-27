package cn.ucai.fulicenter.controller.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ucai.fulicenter.R;

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
//        if(index!=currentIndex) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.hide(mFragments[currentIndex]);
//            if(!mFragments[index].isAdded()){
//                ft.add(R.id.fragment_container,mFragments[index]);
//            }
//            ft.show(mFragments[index]).commit();
//        }
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
}
