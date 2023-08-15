package com.google.appinventor.components.runtime;

import android.view.View;
import android.widget.RelativeLayout;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class PieChartView extends ChartView<PieEntry, IPieDataSet, PieData, PieChart, PieChartView> {
    private float bottomOffset;
    private final List<LegendEntry> legendEntries;
    private final List<PieChart> pieCharts;
    private int pieHoleRadius;
    private final RelativeLayout rootView;

    public PieChartView(Chart chartComponent) {
        super(chartComponent);
        this.pieCharts = new ArrayList();
        this.pieHoleRadius = 0;
        this.legendEntries = new ArrayList();
        this.bottomOffset = 0.0f;
        this.rootView = new RelativeLayout(this.form);
        this.chart = new PieChart(this.form);
        initializeDefaultSettings();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.appinventor.components.runtime.ChartView
    public void initializeDefaultSettings() {
        super.initializeDefaultSettings();
        this.chart.getLegend().setDrawInside(true);
        this.chart.getLegend().setCustom(this.legendEntries);
    }

    @Override // com.google.appinventor.components.runtime.ChartView
    public View getView() {
        return this.rootView;
    }

    @Override // com.google.appinventor.components.runtime.ChartView
    public ChartDataModel<PieEntry, IPieDataSet, PieData, PieChart, PieChartView> createChartModel() {
        PieChart pieChart = createPieChartRing();
        return new PieChartDataModel(new PieData(), this, pieChart);
    }

    private PieChart createPieChartRing() {
        PieChart pieChart;
        if (this.pieCharts.isEmpty()) {
            pieChart = this.chart;
        } else {
            pieChart = new PieChart(this.form);
            pieChart.getDescription().setEnabled(false);
            pieChart.getLegend().setEnabled(false);
        }
        setPieChartProperties(pieChart);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, -1);
        params.addRule(13, -1);
        pieChart.setLayoutParams(params);
        this.pieCharts.add(pieChart);
        this.rootView.addView(pieChart);
        return pieChart;
    }

    private void setPieChartProperties(PieChart chart) {
        chart.setDrawEntryLabels(false);
    }

    @Override // com.google.appinventor.components.runtime.ChartView
    protected void refresh(ChartDataModel<PieEntry, IPieDataSet, PieData, PieChart, PieChartView> model, List<PieEntry> entries) {
        PieDataSet pieDataSet = (IPieDataSet) model.getDataset();
        if (pieDataSet instanceof PieDataSet) {
            pieDataSet.setValues(entries);
        }
        this.chart.getLegend().setCustom(this.legendEntries);
        for (PieChart pieChart : this.pieCharts) {
            if (pieChart == this.chart || pieChart.getData().getDataSet().equals(model.getDataset())) {
                pieChart.getData().notifyDataChanged();
                pieChart.notifyDataSetChanged();
            }
            updatePieChartRingOffset(pieChart);
            pieChart.invalidate();
        }
    }

    public void resizePieRings() {
        int lastWidth = 0;
        int lastHeight = 0;
        float reductionFactor = (0.75f + (this.pieHoleRadius / 100.0f)) / this.pieCharts.size();
        float radius = 100.0f - this.pieHoleRadius;
        float newHoleRadius = 100.0f - (radius * reductionFactor);
        int i = 0;
        while (i < this.pieCharts.size()) {
            PieChart pieChart = this.pieCharts.get(i);
            boolean lastChart = i == this.pieCharts.size() + (-1);
            changePieChartRadius(pieChart, newHoleRadius, lastChart);
            if (i != 0) {
                float scalingFactor = newHoleRadius / 100.0f;
                lastWidth = (int) (lastWidth * scalingFactor);
                lastHeight = (int) (lastHeight * scalingFactor);
                changePieChartSize(pieChart, lastWidth, lastHeight);
            } else {
                lastHeight = pieChart.getHeight();
                lastWidth = pieChart.getWidth();
            }
            pieChart.invalidate();
            i++;
        }
    }

    private void changePieChartRadius(PieChart pieChart, float newHoleRadius, boolean lastChart) {
        if (!lastChart) {
            pieChart.setTransparentCircleRadius(newHoleRadius);
            pieChart.setHoleRadius(newHoleRadius);
            pieChart.setDrawHoleEnabled(true);
        } else if (this.pieHoleRadius == 0) {
            pieChart.setDrawHoleEnabled(false);
        } else {
            float delta = newHoleRadius - this.pieHoleRadius;
            float setRadius = this.pieHoleRadius * (1.0f + (delta / 100.0f));
            pieChart.setTransparentCircleRadius(setRadius);
            pieChart.setHoleRadius(setRadius);
        }
    }

    private void changePieChartSize(PieChart pieChart, int width, int height) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) pieChart.getLayoutParams();
        params.width = width;
        params.height = height;
        pieChart.setLayoutParams(params);
    }

    private void updatePieChartRingOffset(PieChart pieChart) {
        if (this.chart == pieChart) {
            float dpNeededHeight = Utils.convertPixelsToDp(this.chart.getLegend().mNeededHeight);
            this.bottomOffset = dpNeededHeight / 2.5f;
            this.bottomOffset = Math.min(25.0f, this.bottomOffset);
        }
        pieChart.setExtraBottomOffset(this.bottomOffset);
        pieChart.calculateOffsets();
    }

    public void addLegendEntry(final LegendEntry entry) {
        this.uiHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.PieChartView.1
            @Override // java.lang.Runnable
            public void run() {
                PieChartView.this.legendEntries.add(entry);
            }
        });
    }

    public void removeLegendEntry(final LegendEntry entry) {
        this.uiHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.PieChartView.2
            @Override // java.lang.Runnable
            public void run() {
                PieChartView.this.legendEntries.remove(entry);
            }
        });
    }

    public void removeLegendEntries(List<LegendEntry> entries) {
        final List<LegendEntry> entriesCopy = new ArrayList<>(entries);
        this.uiHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.PieChartView.3
            @Override // java.lang.Runnable
            public void run() {
                PieChartView.this.legendEntries.removeAll(entriesCopy);
            }
        });
    }

    public void setPieRadius(int percent) {
        if (percent > 100) {
            percent = 100;
        } else if (percent < 0) {
            percent = 0;
        }
        this.pieHoleRadius = 100 - percent;
        resizePieRings();
    }

    public List<LegendEntry> getLegendEntries() {
        return this.legendEntries;
    }
}
