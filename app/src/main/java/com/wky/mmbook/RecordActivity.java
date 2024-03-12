package com.wky.mmbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.wky.mmbook.adapter.RecordPagerAdapter;
import com.wky.mmbook.frag_record.IncomeFragment;
import com.wky.mmbook.frag_record.BaseRecordFragment;
import com.wky.mmbook.frag_record.OutcomeFragment;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        tabLayout = findViewById(R.id.record_tabs);
        viewPager = findViewById(R.id.record_vp);

        initPager();
    }

    private void initPager() {
        //初始化
        List<Fragment> fragmentList = new ArrayList<>();
        //创建页面
        OutcomeFragment outFrag = new OutcomeFragment();
        IncomeFragment inFrag = new IncomeFragment();
        fragmentList.add(outFrag);
        fragmentList.add(inFrag);
        //创建适配器
        RecordPagerAdapter PagerAdapter = new RecordPagerAdapter(getSupportFragmentManager(), fragmentList);
        //设置适配器
        viewPager.setAdapter(PagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }


    public void onClick(View view) {
        switch (view.getId()){
            case R.id.record_iv_back:
                finish();
                break;
        }
    }
}