package com.wky.mmbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wky.mmbook.R;
import com.wky.mmbook.db.AccountBean;

import java.util.Calendar;
import java.util.List;

public class AccountAdapter extends BaseAdapter {
    Context context;
    List<AccountBean> mDatas;
    LayoutInflater inflater;
    int year,month,day;
    public AccountAdapter(Context context, List<AccountBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.item_mainlv,viewGroup,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        AccountBean bean = mDatas.get(i);
        holder.typeIv.setImageResource(bean.getsImageId());
        holder.typeTv.setText(bean.getTypename());
        holder.beizhuTv.setText(bean.getBeizhu());
        holder.moneyTv.setText("￥ "+bean.getMoney());
        if (bean.getYear()==year && bean.getMonth()==month && bean.getDay()==day) {
            String time = bean.getTime().split(" ")[1];
            holder.timeTv.setText("今天 "+time);
        }
        else {
            holder.timeTv.setText(bean.getTime());
        }
        return view;
    }
    class ViewHolder {
        ImageView typeIv;
        TextView typeTv, beizhuTv, timeTv, moneyTv;

        public ViewHolder(View view) {
            typeIv = view.findViewById(R.id.item_mainlv_iv);
            typeTv = view.findViewById(R.id.item_mainlv_tv_title);
            timeTv = view.findViewById(R.id.item_mainlv_tv_time);
            beizhuTv = view.findViewById(R.id.item_mainlv_tv_beizhu);
            moneyTv = view.findViewById(R.id.item_mainlv_tv_money);
        }
    }
}
