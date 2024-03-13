package com.wky.mmbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wky.mmbook.adapter.AccountAdapter;
import com.wky.mmbook.db.AccountBean;
import com.wky.mmbook.db.DBManager;
import com.wky.mmbook.utils.BudgetDialog;
import com.wky.mmbook.utils.MoreDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ListView todayLv;//展示今日
    List<AccountBean> mDatas;//数据源
    AccountAdapter adapter;
    int year,month,day;
    View headerView;
    TextView topOutTv,topInTv,topbudgetTv,topConTv;
    ImageView topShowIv;
    ImageView searchIv;
    Button editBtn;
    ImageButton moreBtn;
    SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTime();
        initView();
        preferences = getSharedPreferences("budget", Context.MODE_PRIVATE);
        //头布局
        addLVHeaderView();
        mDatas = new ArrayList<>();
        //适配器
        adapter = new AccountAdapter(this, mDatas);
        todayLv.setAdapter(adapter);

    }

    private void initView() {
        todayLv = findViewById(R.id.main_lv);
        editBtn = findViewById(R.id.main_btn_edit);
        moreBtn = findViewById(R.id.main_btn_more);
        searchIv = findViewById(R.id.main_iv_search);
        editBtn.setOnClickListener(this);
        moreBtn.setOnClickListener(this);
        searchIv.setOnClickListener(this);
        setLVLongClickListener();

    }

    //长摁删除
    private void setLVLongClickListener() {
        todayLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0) {//点错了
                    return false;
                }
                int pos = i-1;
                AccountBean clickBean = mDatas.get(pos);

                //删除
                showDeleteItemDialog(clickBean);
                return false;
            }
        });

    }

    //是否删除记录
    private void showDeleteItemDialog(final AccountBean clickBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("您确定要删除这条记录么？").
                setNegativeButton("取消",null).
                setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int click_id = clickBean.getId();
                        DBManager.deleteItemFromAccounttbById(click_id);//删除
                        mDatas.remove(clickBean);//移除
                        adapter.notifyDataSetChanged();//更新
                        setTopTvShow();//刷新
                    }
                });
        builder.create().show();

    }

    private void addLVHeaderView() {
        headerView = getLayoutInflater().inflate(R.layout.item_mainlv_top, null);
        todayLv.addHeaderView(headerView);
        //查找头布局可用控件
        topOutTv = headerView.findViewById(R.id.item_mainlv_top_tv_out);
        topInTv = headerView.findViewById(R.id.item_mainlv_top_tv_in);
        topbudgetTv = headerView.findViewById(R.id.item_mainlv_top_tv_budget);
        topConTv = headerView.findViewById(R.id.item_mainlv_top_tv_day);
        topShowIv = headerView.findViewById(R.id.item_mainlv_top_iv_hide);

        topbudgetTv.setOnClickListener(this);
        headerView.setOnClickListener(this);
        topShowIv.setOnClickListener(this);
    }


    //今日时间
    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    //获取焦点调用
    @Override
    protected void onResume() {
        super.onResume();
        loadDBDate();
        setTopTvShow();
    }

    //头布局文本显示
    private void setTopTvShow() {
        //今日支出
        float outcomeOneDay = DBManager.getSumMoneyOneDay(year, month, day, 0);
        float incomeOneDay = DBManager.getSumMoneyOneDay(year, month, day, 1);
        String infoOneDay = "今日支出 ￥"+outcomeOneDay+" 收入 ￥"+incomeOneDay;
        topConTv.setText(infoOneDay);
        //获取本月收入和支出总金额
        float incomeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 1);
        float outcomeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 0);
        topInTv.setText("￥"+incomeOneMonth);
        topOutTv.setText("￥"+outcomeOneMonth);
        //设置显示预算剩余
        float bmoney = preferences.getFloat("bmoney", 0);//预算
        if (bmoney == 0) {
            topbudgetTv.setText("￥ 0");
        }else{
            float syMoney = bmoney-outcomeOneMonth;
            topbudgetTv.setText("￥"+syMoney);
        }

    }

    private void loadDBDate() {
        List<AccountBean> list = DBManager.getAccountListOneDayFromAccounttb(year, month, day);
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_iv_search:
                Intent it = new Intent(this, SearchActivity.class);  //跳转界面
                startActivity(it);
                break;
            case R.id.main_btn_edit:
                Intent it1 = new Intent(this, RecordActivity.class);//连接多个页面
                startActivity(it1);
                break;
            case R.id.main_btn_more:
                MoreDialog moreDialog = new MoreDialog(this);
                moreDialog.show();
                moreDialog.setDialogSize();
                break;
            case R.id.item_mainlv_top_tv_budget:
                showBudgetDialog();
                break;
            case R.id.item_mainlv_top_iv_hide:
                // 切换TextView明文和密文
                toggleShow();
                break;
        }
        if(view==headerView){
            //头布局被点击了
            Intent intent = new Intent();
            intent.setClass(this, MonthChartActivity.class);
            startActivity(intent);
        }
    }

    private void showBudgetDialog() {
        BudgetDialog dialog = new BudgetDialog(this);
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new BudgetDialog.OnEnsureListener() {
            @Override
            public void onEnsure(float money) {
                //将预算金额写入到共享参数当中，进行存储
                SharedPreferences.Editor editor = preferences.edit();
                editor.putFloat("bmoney",money);
                editor.commit();
                //计算剩余金额
                float outcomeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 0);
                float syMoney = money-outcomeOneMonth;//预算剩余 = 预算-支出
                topbudgetTv.setText("￥"+syMoney);
            }
        });

    }

    boolean isShow = true;

    //更改状态
    private void toggleShow() {
        if (isShow) {
            PasswordTransformationMethod passMethod = PasswordTransformationMethod.getInstance();
            topInTv.setTransformationMethod(passMethod);
            topOutTv.setTransformationMethod(passMethod);   //设置隐藏
            topbudgetTv.setTransformationMethod(passMethod);   //设置隐藏
            topShowIv.setImageResource(R.mipmap.ih_hide);
            isShow = false;   //设置标志位为隐藏状态
        }
        else{
            HideReturnsTransformationMethod hideMethod = HideReturnsTransformationMethod.getInstance();
            topInTv.setTransformationMethod(hideMethod);   //设置隐藏
            topOutTv.setTransformationMethod(hideMethod);   //设置隐藏
            topbudgetTv.setTransformationMethod(hideMethod);   //设置隐藏
            topShowIv.setImageResource(R.mipmap.ih_show);
            isShow = true;   //设置标志位为隐藏状态

        }
    }


}