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
import com.wky.mmbook.frag_record.ReInFragment;
import com.wky.mmbook.frag_record.ReOutFragment;

import java.util.ArrayList;
import java.util.List;

public class reRecordActivity extends AppCompatActivity {
    TabLayout tabLayout1;
    ViewPager viewPager1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_record);

        tabLayout1 = findViewById(R.id.rrecord_tabs);
        viewPager1 = findViewById(R.id.rrecord_vp);

        initPager();
    }

    private void initPager() {
        //初始化
        List<Fragment> fragmentList = new ArrayList<>();
        //创建页面
        ReOutFragment outFrag = new ReOutFragment();
        ReInFragment inFrag = new ReInFragment();
        fragmentList.add(outFrag);
        fragmentList.add(inFrag);
        //创建适配器
        RecordPagerAdapter PagerAdapter = new RecordPagerAdapter(getSupportFragmentManager(), fragmentList);
        //设置适配器
        viewPager1.setAdapter(PagerAdapter);
        tabLayout1.setupWithViewPager(viewPager1);

    }

    public void onClick(View view) {

    }
}