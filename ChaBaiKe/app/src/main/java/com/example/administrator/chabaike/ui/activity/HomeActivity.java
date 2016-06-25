package com.example.administrator.chabaike.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.beans.TabInfo;
import com.example.administrator.chabaike.ui.fragment.ContentFragment;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class HomeActivity extends AppCompatActivity {

    private TabLayout mTabs;
    private ViewPager viewPager;
    private Toolbar toolbar;

    private TabInfo[] tabs = new TabInfo[] {
            new TabInfo("社会热点",6),
            new TabInfo("企业要闻",1),
            new TabInfo("医疗新闻",2),
            new TabInfo("生活贴士",3),
            new TabInfo("药品新闻",4),
            new TabInfo("疾病快讯",7),
            new TabInfo("食品新闻",5)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initView() {
        mTabs = (TabLayout) findViewById(R.id.home_class);
        viewPager = (ViewPager) findViewById(R.id.home_vp);
        viewPager.setAdapter(new contentAdapter(getSupportFragmentManager()));
        mTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabs.setupWithViewPager(viewPager);
        toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        toolbar.setTitle("茶百科");
    }

    public class contentAdapter extends FragmentStatePagerAdapter {

        public contentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ContentFragment cf = new ContentFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", tabs[position].class_id);
            cf.setArguments(bundle);
            return cf;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position].name;
        }
    }
}
