package com.wky.mmbook.frag_chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class MonthXAxisValueFormatter extends ValueFormatter implements IAxisValueFormatter {
    private int month;

    public MonthXAxisValueFormatter(int month) {
        this.month = month;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int val = (int) value;
        if (val == 0) {
            return month + "-1";
        }
        if (val == 14) {
            return month + "-15";
        }
        // 根据不同的月份，显示最后一天的位置
        if (month == 2) {
            if (val == 27) {
                return month + "-28";
            }
        } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            if (val == 30) {
                return month + "-31";
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (val == 29) {
                return month + "-30";
            }
        }
        return "";
    }
}
