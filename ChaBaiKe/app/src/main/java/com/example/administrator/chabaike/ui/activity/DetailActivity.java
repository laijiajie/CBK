package com.example.administrator.chabaike.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.beans.Info;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/6/23 0023.
 */
public class DetailActivity extends AppCompatActivity {

    private Info infos;
    private Intent intent;
    private Toolbar toolbar;
    private TextView detail_keywords,detail_content,detail_time,detail_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        int choice = getIntent().getIntExtra("choice",0);
        initView();
        switch (choice) {
            case 1:
                setDetail();
                break;
        }
        setSupportActionBar(toolbar);

    }

    private void setDetail() {
        String title = infos.getTitle();
        String content = infos.getMessage();
        String keywords = infos.getKeywords();
        String id = infos.getId() + "" + intent.getIntExtra("info_id",0);
        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(infos.getTime());
        detail_title.setText(title);
        detail_content.setText(content);
        detail_keywords.setText(keywords);
        detail_time.setText(time);
    }

    private void initView() {
        intent = getIntent();
        infos = intent.getExtras().getParcelable("info");
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        detail_keywords = (TextView) findViewById(R.id.detail_tv_key);
        detail_content = (TextView) findViewById(R.id.detail_tv_content);
        detail_time = (TextView) findViewById(R.id.detail_tv_time);
        detail_title = (TextView) findViewById(R.id.detail_tv_title);
    }
}
