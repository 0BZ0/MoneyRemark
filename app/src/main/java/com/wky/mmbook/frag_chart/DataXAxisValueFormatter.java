package com.wky.mmbook.frag_chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class DataXAxisValueFormatter extends ValueFormatter implements IAxisValueFormatter {
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // 此处的value默认保存一位小数
        if (value == 0) {
            return "";
        }
        return String.valueOf(value);
    }
}
