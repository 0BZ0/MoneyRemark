package com.wky.mmbook.frag_record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.wky.mmbook.R;
import com.wky.mmbook.db.DBManager;
import com.wky.mmbook.db.TypeBean;

import java.util.List;

public class IncomeFragment extends BaseRecordFragment {

    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        //获取数据源
        List<TypeBean> inList = DBManager.getTypeList(1);
        typeList.addAll(inList);
        adapter.notifyDataSetChanged();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.in_qt_fs);
    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(1);
        DBManager.insertItemToAccounttb(accountBean);

    }
}