package com.google.appinventor.components.runtime;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.google.appinventor.components.runtime.PointChartView;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.YailList;

/* loaded from: classes.dex */
public abstract class PointChartDataModel<E extends Entry, T extends IBarLineScatterCandleBubbleDataSet<E>, D extends BarLineScatterCandleBubbleData<T>, C extends BarLineChartBase<D>, V extends PointChartView<E, T, D, C, V>> extends Chart2DDataModel<E, T, D, C, V> {
    /* JADX INFO: Access modifiers changed from: protected */
    public PointChartDataModel(D data, V view) {
        super(data, view);
    }

    @Override // com.google.appinventor.components.runtime.ChartDataModel
    public Entry getEntryFromTuple(YailList tuple) {
        try {
            String xValue = tuple.getString(0);
            String yValue = tuple.getString(1);
            try {
                float x = Float.parseFloat(xValue);
                float y = Float.parseFloat(yValue);
                return new Entry(x, y);
            } catch (NumberFormatException e) {
                ((PointChartView) this.view).getForm().dispatchErrorOccurredEvent(((PointChartView) this.view).chartComponent, "GetEntryFromTuple", ErrorMessages.ERROR_INVALID_CHART_ENTRY_VALUES, xValue, yValue);
                return null;
            }
        } catch (IndexOutOfBoundsException e2) {
            ((PointChartView) this.view).getForm().dispatchErrorOccurredEvent(((PointChartView) this.view).chartComponent, "GetEntryFromTuple", ErrorMessages.ERROR_INSUFFICIENT_CHART_ENTRY_VALUES, Integer.valueOf(getTupleSize()), Integer.valueOf(tuple.size()));
            return null;
        } catch (NullPointerException e3) {
            ((PointChartView) this.view).getForm().dispatchErrorOccurredEvent(((PointChartView) this.view).chartComponent, "GetEntryFromTuple", ErrorMessages.ERROR_NULL_CHART_ENTRY_VALUES, new Object[0]);
            return null;
        }
    }
}
