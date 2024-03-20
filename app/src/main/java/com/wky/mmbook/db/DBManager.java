package com.wky.mmbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wky.mmbook.utils.FloatUtils;
import com.wky.mmbook.utils.IDSession;

import java.util.ArrayList;
import java.util.List;

//管理数据库
public class DBManager {
    private static SQLiteDatabase sqLiteDatabase;

    public static void initDB(Context context){
        DBOpenHelper helper = new DBOpenHelper(context);
        sqLiteDatabase = helper.getWritableDatabase();

    }

    //读取数据库
    public static List<TypeBean> getTypeList(int kind){
        List<TypeBean>list = new ArrayList<>();
        //读取数据
        String sql = "select * from typetb where kind = "+kind;
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        //循环
        while(cursor.moveToNext()){
            String typename = cursor.getString(cursor.getColumnIndexOrThrow("typename"));
            int imageId = cursor.getInt(cursor.getColumnIndexOrThrow("imageId"));
            int sImageId = cursor.getInt(cursor.getColumnIndexOrThrow("sImageId"));
            int kind1 = cursor.getInt(cursor.getColumnIndexOrThrow("kind"));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            TypeBean typeBean = new TypeBean(id, typename, imageId, sImageId, kind1);
            list.add(typeBean);
        }
        return list;
    }

    //记账表插入数据
    public static void insertItemToAccounttb(AccountBean bean,UserBean userBean){
        ContentValues values = new ContentValues();
        values.put("typename",bean.getTypename());
        values.put("sImageId",bean.getsImageId());
        values.put("beizhu",bean.getBeizhu());
        values.put("money",bean.getMoney());
        values.put("time",bean.getTime());
        values.put("year",bean.getYear());
        values.put("month",bean.getMonth());
        values.put("day",bean.getDay());
        values.put("kind",bean.getKind());
        values.put("UserId", IDSession.getInstance().getUserId());
        sqLiteDatabase.insert("accounttb",null,values);

    }
    //修改数据库单条数据
    public static void updateItemInAccounttb(AccountBean bean) {
        // 创建 ContentValues 对象，用于存储需要更新的数据
        ContentValues values = new ContentValues();
        values.put("typename", bean.getTypename());
        values.put("sImageId", bean.getsImageId());
        values.put("beizhu", bean.getBeizhu());
        values.put("money", bean.getMoney());
        values.put("time", bean.getTime());
        values.put("year", bean.getYear());
        values.put("month", bean.getMonth());
        values.put("day", bean.getDay());
        values.put("kind", bean.getKind());
        values.put("UserId", bean.getUserId());

        // 根据 ID 执行更新操作
        sqLiteDatabase.update("accounttb", values, "id=?", new String[]{String.valueOf(bean.getId())});
    }

    //获取记账数据库单日内容
    public static List<AccountBean>getAccountListOneDayFromAccounttb(int year,int month,int day,int UserId){
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=? and day=? and UserId=? order by id desc";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{year + "", month + "", day + "",UserId + ""});
        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String typename = cursor.getString(cursor.getColumnIndexOrThrow("typename"));
            String beizhu = cursor.getString(cursor.getColumnIndexOrThrow("beizhu"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndexOrThrow("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndexOrThrow("kind"));
            float money = cursor.getFloat(cursor.getColumnIndexOrThrow("money"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, beizhu, money, time, year, month, day, kind,UserId);
            list.add(accountBean);
        }
        cursor.close();
        return list;
    }
    //月
    public static List<AccountBean>getAccountListOneMonthFromAccounttb(int year,int month,int UserId){
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=? and UserId=? order by id desc";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{year + "", month + "",UserId + ""});
        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String typename = cursor.getString(cursor.getColumnIndexOrThrow("typename"));
            String beizhu = cursor.getString(cursor.getColumnIndexOrThrow("beizhu"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndexOrThrow("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndexOrThrow("kind"));
            float money = cursor.getFloat(cursor.getColumnIndexOrThrow("money"));
            int day = cursor.getInt(cursor.getColumnIndexOrThrow("day"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, beizhu, money, time, year, month, day, kind,UserId);
            list.add(accountBean);
        }
        cursor.close();
        return list;
    }

    //获取某一天的支出或者收入的总金额   kind：支出==0    收入===1
    public static float getSumMoneyOneDay(int year,int month,int day,int kind,int UserId){
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and day=? and kind=? and UserId=? ";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{year + "", month + "", day + "", kind + "",UserId + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndexOrThrow("sum(money)"));
            total = money;
        }
        cursor.close();
        return total;
    }
    //月
    public static float getSumMoneyOneMonth(int year,int month,int kind,int UserId){
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and kind=? and UserId=? ";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{year + "", month + "", kind + "",UserId + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndexOrThrow("sum(money)"));
            total = money;
        }
        cursor.close();
        return total;
    }
    //年
    public static float getSumMoneyOneYear(int year,int kind,int UserId){
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and kind=? and UserId=? ";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{year + "", kind + "",UserId + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndexOrThrow("sum(money)"));
            total = money;
        }
        cursor.close();
        return total;
    }
    //月支出和收入
    public static int getCountItemOneMonth(int year,int month,int kind,int UserId){
        int total = 0;
        String sql = "select count(money) from accounttb where year=? and month=? and kind=? and UserId=? ";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{year + "", month + "", kind + "",UserId + ""});
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(cursor.getColumnIndexOrThrow("count(money)"));
            total = count;
        }
        cursor.close();
        return total;
    }
    //根据id查找
    public static AccountBean getItemFromAccounttbById(int id) {
        Cursor cursor = null;
        try {
            String sql = "SELECT * FROM accounttb WHERE id = ?";
            String[] selectionArgs = {String.valueOf(id)};
            cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
            if (cursor != null && cursor.moveToFirst()) {
                // 从Cursor中读取数据并创建AccountBean对象
                int itemId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String typename = cursor.getString(cursor.getColumnIndexOrThrow("typename"));
                int sImageId = cursor.getInt(cursor.getColumnIndexOrThrow("sImageId"));
                String beizhu = cursor.getString(cursor.getColumnIndexOrThrow("beizhu"));
                float money = cursor.getFloat(cursor.getColumnIndexOrThrow("money"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                int year = cursor.getInt(cursor.getColumnIndexOrThrow("year"));
                int month = cursor.getInt(cursor.getColumnIndexOrThrow("month"));
                int day = cursor.getInt(cursor.getColumnIndexOrThrow("day"));
                int kind = cursor.getInt(cursor.getColumnIndexOrThrow("kind"));
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("UserId"));
                // 根据需要创建AccountBean对象
                AccountBean accountBean = new AccountBean(itemId, typename, sImageId, beizhu, money, time, year, month, day, kind, userId);
                return accountBean;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null; // 没有找到对应id的数据
    }



    //根据id删除
    public static int deleteItemFromAccounttbById(int id){
        int i = sqLiteDatabase.delete("accounttb", "id=?", new String[]{id + ""});
        return i;
    }
    //根据备注搜索收入或者支出的情况列表
    public static List<AccountBean>getAccountListByRemarkFromAccounttb(String beizhu,int UserId){
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from accounttb where beizhu like '%"+beizhu+"%'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String typename = cursor.getString(cursor.getColumnIndexOrThrow("typename"));
            String bz = cursor.getString(cursor.getColumnIndexOrThrow("beizhu"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndexOrThrow("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndexOrThrow("kind"));
            float money = cursor.getFloat(cursor.getColumnIndexOrThrow("money"));
            int year = cursor.getInt(cursor.getColumnIndexOrThrow("year"));
            int month = cursor.getInt(cursor.getColumnIndexOrThrow("month"));
            int day = cursor.getInt(cursor.getColumnIndexOrThrow("day"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, bz, money, time, year, month, day, kind,UserId);
            list.add(accountBean);
        }
        cursor.close();
        return list;
    }
    //根据种类搜索收入或者支出的情况列表
    public static List<AccountBean>getAccountListByTypenameFromAccounttb(String typename,int UserId){
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from accounttb where typename like '%"+typename+"%'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String tyn = cursor.getString(cursor.getColumnIndexOrThrow("typename"));
            String beizhu = cursor.getString(cursor.getColumnIndexOrThrow("beizhu"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndexOrThrow("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndexOrThrow("kind"));
            float money = cursor.getFloat(cursor.getColumnIndexOrThrow("money"));
            int year = cursor.getInt(cursor.getColumnIndexOrThrow("year"));
            int month = cursor.getInt(cursor.getColumnIndexOrThrow("month"));
            int day = cursor.getInt(cursor.getColumnIndexOrThrow("day"));
            AccountBean accountBean = new AccountBean(id, tyn, sImageId, beizhu, money, time, year, month, day, kind,UserId);
            list.add(accountBean);
        }
        cursor.close();
        return list;
    }

    //查询记账的表当中有几个年份信息
    public static List<Integer>getYearListFromAccounttb(int UserId){
        List<Integer> list = new ArrayList<>();
        String sql = "select distinct(year) from accounttb where UserId=? order by year asc";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(UserId)});
        while (cursor.moveToNext()) {
            int year = cursor.getInt(cursor.getColumnIndexOrThrow("year"));
            list.add(year);
        }
        cursor.close();
        return list;
    }
    //删库跑路
    public static void deleteAllAccount(int UserId){
        String sql = "delete from accounttb where UserId=?";
        sqLiteDatabase.execSQL(sql, new Object[]{UserId});
    }
    //获取建表数据
    public static List<ChartItemBean>getChartListFromAccounttb(int year,int month,int kind,int UserId){
        List<ChartItemBean>list = new ArrayList<>();
        float sumMoneyOneMonth = getSumMoneyOneMonth(year, month, kind,UserId);  //求出支出或者收入总钱数
        String sql = "select typename,sImageId,sum(money)as total from accounttb where year=? and month=? and kind=? and UserId=? group by typename " +
                "order by total desc";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{year + "", month + "", kind + "",UserId + ""});
        while (cursor.moveToNext()) {
            int sImageId = cursor.getInt(cursor.getColumnIndexOrThrow("sImageId"));
            String typename = cursor.getString(cursor.getColumnIndexOrThrow("typename"));
            float total = cursor.getFloat(cursor.getColumnIndexOrThrow("total"));
            //计算所占百分比  total /sumMonth
            float ratio = FloatUtils.div(total,sumMoneyOneMonth);
            ChartItemBean bean = new ChartItemBean(sImageId, typename, ratio, total);
            list.add(bean);
        }
        cursor.close();
        return list;
    }

    //最大金额
    public static float getMaxMoneyOneDayInMonth(int year,int month,int kind,int UserId){
        String sql = "select sum(money) from accounttb where year=? and month=? and kind=? and UserId=? group by day order by sum(money) desc";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{year + "", month + "", kind + "",UserId + ""});
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndexOrThrow("sum(money)"));
            return money;
        }
        cursor.close();
        return 0;
    }

    //指定月份钱数总和
    public static List<BarChartItemBean>getSumMoneyOneDayInMonth(int year,int month,int kind,int UserId){
        String sql = "select day,sum(money) from accounttb where year=? and month=? and kind=? and UserId=? group by day";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{year + "", month + "", kind + "",UserId + ""});
        List<BarChartItemBean>list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int day = cursor.getInt(cursor.getColumnIndexOrThrow("day"));
            float smoney = cursor.getFloat(cursor.getColumnIndexOrThrow("sum(money)"));
            BarChartItemBean itemBean = new BarChartItemBean(year, month, day, smoney);
            list.add(itemBean);
        }
        cursor.close();
        return list;
    }

    //用户注册
    public static void RegisterUser(UserBean user) {
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        sqLiteDatabase.insert("users", null, values);
    }
    //用户验证
    public static boolean isValidUser(String username, String password) {
        String[] columns = {"id"};
        String selection = "username=? AND password=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = sqLiteDatabase.query("users", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
    //获取id
    public static int getUserId(String username, String password) {
        String[] columns = {"id"};
        String selection = "username=? AND password=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = sqLiteDatabase.query("users", columns, selection, selectionArgs, null, null, null);
        int userId = -1; // 默认为 -1，表示未找到匹配的用户
        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow("id")); // 获取找到的用户的 ID
            cursor.close();
        }
        return userId;
    }
}
