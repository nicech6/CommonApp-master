package com.project.wisdomfirecontrol.firecontrol.ui.utils;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by Administrator on 2018/7/25.
 */

public class DayAxisValueFormatter implements IAxisValueFormatter {
    private int tmep = 0;

    protected String[] mMonths = new String[]{
            "A相电流（A）", "B相电流（A）", "C相电流（A）", "漏电流（ma）"
    };

    private BarLineChartBase<?> chart;
    private String monthName;

    public DayAxisValueFormatter(BarLineChartBase<?> chart) {
        this.chart = chart;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return "";
    }

}
