package com.google.appinventor.components.runtime;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

/* loaded from: classes.dex */
public class AreaChartView extends LineChartViewBase<AreaChartView> {
    public AreaChartView(Chart chartComponent) {
        super(chartComponent);
        this.chart.setHardwareAccelerationEnabled(false);
    }

    @Override // com.google.appinventor.components.runtime.ChartView
    public ChartDataModel<Entry, ILineDataSet, LineData, LineChart, AreaChartView> createChartModel() {
        return new AreaChartDataModel(this.data, this);
    }
}
