package com.wky.mmbook.frag_record;

import com.wky.mmbook.R;
import com.wky.mmbook.db.DBManager;
import com.wky.mmbook.db.TypeBean;

import java.util.List;

public class ReOutFragment extends BaseRecordFragment{

    //重写
    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        //获取数据源
        List<TypeBean> outList = DBManager.getTypeList(0);
        typeList.addAll(outList);
        adapter.notifyDataSetChanged();
        setAccountBean(AccId);
    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(0);
        DBManager.updateItemInAccounttb(accountBean);

    }
}
