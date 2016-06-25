package com.example.administrator.chabaike.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.app.ConstantKey;
import com.example.administrator.chabaike.utils.Pref_Utils;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class WelcomeActivity extends Activity {

    private ViewPager viewPager;
    private WelcomeAdapter adapter;
    private LinearLayout ll_container;
    private int[] imagesourse = new int[]{R.mipmap.slide1, R.mipmap.slide2, R.mipmap.slide3};
    private int pre_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        viewPager = (ViewPager) findViewById(R.id.welcome_vp);
        initView();
        viewPager.setAdapter(adapter = new WelcomeAdapter());
        viewPager.addOnPageChangeListener(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Pref_Utils.getBoolean(this, ConstantKey.PRE_KEY_FIRST_OPEN, false);
    }

    private ImageView[] imageView = new ImageView[imagesourse.length];

    private void initView() {
        ll_container = (LinearLayout) findViewById(R.id.welcome_ll);
        View view = null;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(10,10);

        ViewGroup.LayoutParams lvp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                 ViewPager.LayoutParams.MATCH_PARENT);

        lp.leftMargin = 10;
        ImageView iv = null;
        for (int i = 0; i < imagesourse.length; i++) {

            iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(lvp);
            imageView[i] = iv;
            imageView[i].setImageResource(imagesourse[i]);


            if (i == imagesourse.length-1) {
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(WelcomeActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            view = new View(this);
            if (i == 0) {
                view.setBackgroundResource(R.mipmap.page_now);
            }else {
                view.setBackgroundResource(R.mipmap.page);
            }
            view.setLayoutParams(lp);
            ll_container.addView(view);


        }
    }



    class WelcomeAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

        @Override
        public int getCount() {
            return imagesourse.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageView[position]);
            return imageView[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            ll_container.getChildAt(position).setBackgroundResource(R.mipmap.page_now);
            ll_container.getChildAt(pre_index).setBackgroundResource(R.mipmap.page);
            pre_index = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}


