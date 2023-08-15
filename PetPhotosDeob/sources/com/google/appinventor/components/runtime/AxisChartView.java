package com.google.appinventor.components.runtime;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.google.appinventor.components.runtime.AxisChartView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public abstract class AxisChartView<E extends Entry, T extends IBarLineScatterCandleBubbleDataSet<E>, D extends BarLineScatterCandleBubbleData<T>, C extends BarLineChartBase<D>, V extends AxisChartView<E, T, D, C, V>> extends ChartView<E, T, D, C, V> {
    private List<String> axisLabels;

    /* JADX INFO: Access modifiers changed from: protected */
    public AxisChartView(Chart chartComponent) {
        super(chartComponent);
        this.axisLabels = new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.appinventor.components.runtime.ChartView
    public void initializeDefaultSettings() {
        super.initializeDefaultSettings();
        this.chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        this.chart.getAxisRight().setEnabled(false);
        this.chart.getAxisLeft().setGranularity(1.0f);
        this.chart.getXAxis().setGranularity(1.0f);
        this.chart.getXAxis().setValueFormatter(new ValueFormatter() { // from class: com.google.appinventor.components.runtime.AxisChartView.1
            public String getFormattedValue(float value) {
                int integerValue = Math.round(value) - ((int) AxisChartView.this.chart.getXAxis().getAxisMinimum());
                return (integerValue < 0 || integerValue >= AxisChartView.this.axisLabels.size()) ? super.getFormattedValue(value) : (String) AxisChartView.this.axisLabels.get(integerValue);
            }
        });
        if (this.chartComponent.XFromZero()) {
            this.chart.getXAxis().setAxisMaximum(0.0f);
        }
        if (this.chartComponent.YFromZero()) {
            this.chart.getAxisLeft().setAxisMinimum(0.0f);
        }
    }

    public void setXMinimum(boolean zero) {
        if (zero) {
            this.chart.getXAxis().setAxisMinimum(0.0f);
        } else {
            this.chart.getXAxis().resetAxisMinimum();
        }
    }

    public void setYMinimum(boolean zero) {
        if (zero) {
            this.chart.getAxisLeft().setAxisMinimum(0.0f);
        } else {
            this.chart.getAxisLeft().resetAxisMinimum();
        }
    }

    public void setXBounds(double minimum, double maximum) {
        this.chart.getXAxis().setAxisMinimum((float) minimum);
        this.chart.getXAxis().setAxisMaximum((float) maximum);
    }

    public void setYBounds(double minimum, double maximum) {
        this.chart.getAxisLeft().setAxisMinimum((float) minimum);
        this.chart.getAxisLeft().setAxisMaximum((float) maximum);
    }

    public void setGridEnabled(boolean enabled) {
        this.chart.getXAxis().setDrawGridLines(enabled);
        this.chart.getAxisLeft().setDrawGridLines(enabled);
    }

    public void setLabels(List<String> labels) {
        this.axisLabels = labels;
    }
}
