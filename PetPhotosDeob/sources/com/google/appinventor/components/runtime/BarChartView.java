package com.google.appinventor.components.runtime;

import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import java.util.List;

/* loaded from: classes.dex */
public class BarChartView extends AxisChartView<BarEntry, IBarDataSet, BarData, BarChart, BarChartView> {
    private static final float GROUP_SPACE = 0.08f;
    private static final float START_X_VALUE = 0.0f;
    private float barSpace;
    private float barWidth;

    public BarChartView(Chart chartComponent) {
        super(chartComponent);
        this.barSpace = 0.0f;
        this.barWidth = 0.3f;
        this.chart = new BarChart(this.form);
        this.data = new BarData();
        this.chart.setData(this.data);
        initializeDefaultSettings();
    }

    @Override // com.google.appinventor.components.runtime.ChartView
    public View getView() {
        return this.chart;
    }

    @Override // com.google.appinventor.components.runtime.ChartView
    public ChartDataModel<BarEntry, IBarDataSet, BarData, BarChart, BarChartView> createChartModel() {
        BarChartDataModel model = new BarChartDataModel(this.data, this);
        recalculateBarSpaceAndWidth();
        return model;
    }

    private void recalculateBarSpaceAndWidth() {
        int dataSetCount = this.chart.getData().getDataSetCount();
        if (dataSetCount > 1) {
            float x = 0.92f / dataSetCount;
            this.barSpace = 0.1f * x;
            this.barWidth = 0.9f * x;
            this.chart.getData().setBarWidth(this.barWidth);
        }
        if (dataSetCount == 2) {
            this.chart.getXAxis().setCenterAxisLabels(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.appinventor.components.runtime.AxisChartView, com.google.appinventor.components.runtime.ChartView
    public void initializeDefaultSettings() {
        super.initializeDefaultSettings();
        this.chart.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.chart.getXAxis().setGranularity(1.0f);
    }

    @Override // com.google.appinventor.components.runtime.ChartView
    protected void refresh(ChartDataModel<BarEntry, IBarDataSet, BarData, BarChart, BarChartView> model, List<BarEntry> entries) {
        BarDataSet barDataSet = (IBarDataSet) model.getDataset();
        if (barDataSet instanceof BarDataSet) {
            barDataSet.setValues(entries);
        }
        regroupBars();
        this.chart.getData().notifyDataChanged();
        this.chart.notifyDataSetChanged();
        this.chart.invalidate();
    }

    private void regroupBars() {
        int dataSetCount = this.chart.getData().getDataSetCount();
        if (dataSetCount > 1) {
            this.chart.groupBars(0.0f, (float) GROUP_SPACE, this.barSpace);
            int maxEntries = 0;
            for (IBarDataSet dataSet : this.chart.getData().getDataSets()) {
                maxEntries = Math.max(maxEntries, dataSet.getEntryCount());
            }
            this.chart.getXAxis().setAxisMinimum(0.0f);
            this.chart.getXAxis().setAxisMaximum((this.chart.getData().getGroupWidth((float) GROUP_SPACE, this.barSpace) * maxEntries) + 0.0f);
        }
    }
}
