package com.wky.mmbook.frag_record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.wky.mmbook.R;
import com.wky.mmbook.db.DBManager;
import com.wky.mmbook.db.TypeBean;
import com.wky.mmbook.utils.IDSession;

import java.util.List;

public class ReInFragment extends BaseRecordFragment {

    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        //获取数据源
        List<TypeBean> inList = DBManager.getTypeList(1);
        typeList.addAll(inList);
        adapter.notifyDataSetChanged();
        setAccountBean(AccId);
    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(1);
        DBManager.updateItemInAccounttb(accountBean);

    }
}
