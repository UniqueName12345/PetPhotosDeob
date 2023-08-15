package com.google.appinventor.components.runtime;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class PieChartDataModel extends Chart2DDataModel<PieEntry, IPieDataSet, PieData, PieChart, PieChartView> {
    private final List<LegendEntry> legendEntries;

    public PieChartDataModel(PieData data, PieChartView view, PieChart chart) {
        super(data, view);
        this.legendEntries = new ArrayList();
        this.dataset = new PieDataSet(new ArrayList(), "");
        this.data.addDataSet(this.dataset);
        chart.setData(data);
        setDefaultStylingProperties();
        this.view = view;
    }

    @Override // com.google.appinventor.components.runtime.ChartDataModel
    public void addEntryFromTuple(YailList tuple) {
        PieEntry entry = getEntryFromTuple(tuple);
        if (entry != null) {
            this.entries.add(entry);
            LegendEntry legendEntry = new LegendEntry();
            legendEntry.label = tuple.getString(0);
            int entriesCount = this.entries.size();
            List<Integer> colors = ((IPieDataSet) getDataset()).getColors();
            int index = (entriesCount - 1) % colors.size();
            legendEntry.formColor = colors.get(index).intValue();
            this.legendEntries.add(legendEntry);
            ((PieChartView) this.view).addLegendEntry(legendEntry);
        }
    }

    @Override // com.google.appinventor.components.runtime.ChartDataModel
    public void removeEntry(int index) {
        if (index >= 0) {
            this.entries.remove(index);
            LegendEntry removedEntry = this.legendEntries.remove(index);
            ((PieChartView) this.view).removeLegendEntry(removedEntry);
            updateLegendColors();
        }
    }

    @Override // com.google.appinventor.components.runtime.ChartDataModel
    public void clearEntries() {
        super.clearEntries();
        ((PieChartView) this.view).removeLegendEntries(this.legendEntries);
        this.legendEntries.clear();
    }

    @Override // com.google.appinventor.components.runtime.ChartDataModel
    public Entry getEntryFromTuple(YailList tuple) {
        String xValue;
        String yValue;
        try {
            xValue = tuple.getString(0);
            yValue = tuple.getString(1);
        } catch (IndexOutOfBoundsException e) {
            ((PieChartView) this.view).getForm().dispatchErrorOccurredEvent(((PieChartView) this.view).chartComponent, "GetEntryFromTuple", ErrorMessages.ERROR_INSUFFICIENT_CHART_ENTRY_VALUES, Integer.valueOf(getTupleSize()), Integer.valueOf(tuple.size()));
        } catch (NullPointerException e2) {
            ((PieChartView) this.view).getForm().dispatchErrorOccurredEvent(((PieChartView) this.view).chartComponent, "GetEntryFromTuple", ErrorMessages.ERROR_NULL_CHART_ENTRY_VALUES, new Object[0]);
        }
        try {
            float y = Float.parseFloat(yValue);
            return new PieEntry(y, xValue);
        } catch (NumberFormatException e3) {
            ((PieChartView) this.view).getForm().dispatchErrorOccurredEvent(((PieChartView) this.view).chartComponent, "GetEntryFromTuple", ErrorMessages.ERROR_INVALID_CHART_ENTRY_VALUES, xValue, yValue);
            return null;
        }
    }

    @Override // com.google.appinventor.components.runtime.Chart2DDataModel, com.google.appinventor.components.runtime.ChartDataModel
    public YailList getTupleFromEntry(Entry entry) {
        PieEntry pieEntry = (PieEntry) entry;
        List<?> tupleEntries = Arrays.asList(pieEntry.getLabel(), Float.valueOf(pieEntry.getY()));
        return YailList.makeList((List) tupleEntries);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.appinventor.components.runtime.ChartDataModel
    public void setDefaultStylingProperties() {
        if (this.dataset instanceof PieDataSet) {
            this.dataset.setSliceSpace(3.0f);
        }
    }

    @Override // com.google.appinventor.components.runtime.ChartDataModel
    public void setColors(List<Integer> colors) {
        super.setColors(colors);
        updateLegendColors();
    }

    @Override // com.google.appinventor.components.runtime.ChartDataModel
    public void setColor(int argb) {
        setColors(Collections.singletonList(Integer.valueOf(argb)));
    }

    @Override // com.google.appinventor.components.runtime.ChartDataModel
    protected boolean areEntriesEqual(Entry e1, Entry e2) {
        if ((e1 instanceof PieEntry) && (e2 instanceof PieEntry)) {
            PieEntry p1 = (PieEntry) e1;
            PieEntry p2 = (PieEntry) e2;
            return p1.getLabel().equals(p2.getLabel()) && p1.getY() == p2.getY();
        }
        return false;
    }

    private void updateLegendColors() {
        for (int i = 0; i < this.legendEntries.size(); i++) {
            int index = i % ((IPieDataSet) getDataset()).getColors().size();
            this.legendEntries.get(i).formColor = ((Integer) ((IPieDataSet) getDataset()).getColors().get(index)).intValue();
        }
    }
}
