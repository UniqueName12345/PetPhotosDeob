package com.google.appinventor.components.runtime;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.google.appinventor.components.runtime.ChartView;
import com.google.appinventor.components.runtime.util.ChartDataSourceUtil;
import com.google.appinventor.components.runtime.util.YailList;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.mapping.Symbol;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public abstract class ChartDataModel<E extends Entry, T extends IDataSet<E>, D extends ChartData<T>, C extends com.github.mikephil.charting.charts.Chart<D>, V extends ChartView<E, T, D, C, V>> {
    protected D data;
    protected T dataset;
    protected V view;
    protected int maximumTimeEntries = HttpRequestContext.HTTP_OK;
    protected List<E> entries = new ArrayList();

    /* loaded from: classes.dex */
    public enum EntryCriterion {
        All,
        XValue,
        YValue
    }

    public abstract void addEntryFromTuple(YailList yailList);

    public abstract Entry getEntryFromTuple(YailList yailList);

    public abstract YailList getTupleFromEntry(Entry entry);

    protected abstract int getTupleSize();

    /* JADX INFO: Access modifiers changed from: protected */
    public ChartDataModel(D data, V view) {
        this.data = data;
        this.view = view;
    }

    public T getDataset() {
        return this.dataset;
    }

    public D getData() {
        return this.data;
    }

    public void setColor(int argb) {
        if (this.dataset instanceof DataSet) {
            this.dataset.setColor(argb);
        }
    }

    public void setColors(List<Integer> colors) {
        if (this.dataset instanceof DataSet) {
            this.dataset.setColors(colors);
        }
    }

    public void setLabel(String text) {
        getDataset().setLabel(text);
    }

    public void setElements(String elements) {
        int tupleSize = getTupleSize();
        String[] entries = elements.split(",");
        for (int i = tupleSize - 1; i < entries.length; i += tupleSize) {
            List<String> tupleEntries = new ArrayList<>();
            for (int j = tupleSize - 1; j >= 0; j--) {
                int index = i - j;
                tupleEntries.add(entries[index]);
            }
            addEntryFromTuple(YailList.makeList((List) tupleEntries));
        }
    }

    public void importFromList(List<?> list) {
        for (Object entry : list) {
            YailList tuple = null;
            if (entry instanceof YailList) {
                tuple = (YailList) entry;
            } else if (entry instanceof List) {
                tuple = YailList.makeList((List) entry);
            }
            if (tuple != null) {
                addEntryFromTuple(tuple);
            }
        }
    }

    public void removeValues(List<?> values) {
        for (Object entry : values) {
            YailList tuple = null;
            if (entry instanceof YailList) {
                tuple = (YailList) entry;
            } else if (entry instanceof List) {
                tuple = YailList.makeList((List) entry);
            } else if (entry instanceof Symbol) {
            }
            if (tuple != null) {
                removeEntryFromTuple(tuple);
            }
        }
    }

    public void importFromColumns(YailList columns, boolean hasHeaders) {
        YailList tuples = getTuplesFromColumns(columns, hasHeaders);
        importFromList(tuples);
    }

    public YailList getTuplesFromColumns(YailList columns, boolean hasHeaders) {
        int rows = ChartDataSourceUtil.determineMaximumListSize(columns);
        List<YailList> tuples = new ArrayList<>();
        for (int i = hasHeaders ? 1 : 0; i < rows; i++) {
            ArrayList<String> tupleElements = new ArrayList<>();
            for (int j = 0; j < columns.size(); j++) {
                Object value = columns.getObject(j);
                if (!(value instanceof YailList)) {
                    tupleElements.add(getDefaultValue(i - 1));
                } else {
                    YailList column = (YailList) value;
                    if (column.size() > i) {
                        tupleElements.add(column.getString(i));
                    } else if (column.size() == 0) {
                        tupleElements.add(getDefaultValue(i - 1));
                    } else {
                        tupleElements.add("");
                    }
                }
            }
            YailList tuple = YailList.makeList((List) tupleElements);
            tuples.add(tuple);
        }
        return YailList.makeList((List) tuples);
    }

    public void removeEntryFromTuple(YailList tuple) {
        Entry entry = getEntryFromTuple(tuple);
        if (entry != null) {
            int index = findEntryIndex(entry);
            removeEntry(index);
        }
    }

    public void removeEntry(int index) {
        if (index >= 0) {
            this.entries.remove(index);
        }
    }

    public boolean doesEntryExist(YailList tuple) {
        Entry entry = getEntryFromTuple(tuple);
        int index = findEntryIndex(entry);
        return index >= 0;
    }

    public YailList findEntriesByCriterion(String value, EntryCriterion criterion) {
        List<YailList> entries = new ArrayList<>();
        for (Entry entry : this.entries) {
            if (isEntryCriterionSatisfied(entry, criterion, value)) {
                entries.add(getTupleFromEntry(entry));
            }
        }
        return YailList.makeList((List) entries);
    }

    public YailList getEntriesAsTuples() {
        return findEntriesByCriterion(Component.TYPEFACE_DEFAULT, EntryCriterion.All);
    }

    protected boolean isEntryCriterionSatisfied(Entry entry, EntryCriterion criterion, String value) {
        switch (criterion) {
            case All:
                return true;
            case XValue:
                if (entry instanceof PieEntry) {
                    PieEntry pieEntry = (PieEntry) entry;
                    boolean criterionSatisfied = pieEntry.getLabel().equals(value);
                    return criterionSatisfied;
                }
                try {
                    float xValue = Float.parseFloat(value);
                    float compareValue = entry.getX();
                    if (entry instanceof BarEntry) {
                        compareValue = (float) Math.floor(compareValue);
                    }
                    return compareValue == xValue;
                } catch (NumberFormatException e) {
                    return false;
                }
            case YValue:
                try {
                    float yValue = Float.parseFloat(value);
                    return entry.getY() == yValue;
                } catch (NumberFormatException e2) {
                    return false;
                }
            default:
                throw new IllegalArgumentException("Unknown criterion: " + criterion);
        }
    }

    protected int findEntryIndex(Entry entry) {
        for (int i = 0; i < this.entries.size(); i++) {
            Entry currentEntry = this.entries.get(i);
            if (areEntriesEqual(currentEntry, entry)) {
                return i;
            }
        }
        return -1;
    }

    public void clearEntries() {
        this.entries.clear();
    }

    public void addTimeEntry(YailList tuple) {
        if (this.entries.size() >= this.maximumTimeEntries) {
            this.entries.remove(0);
        }
        addEntryFromTuple(tuple);
    }

    public void setMaximumTimeEntries(int entries) {
        this.maximumTimeEntries = entries;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setDefaultStylingProperties() {
    }

    protected String getDefaultValue(int index) {
        return index + "";
    }

    protected boolean areEntriesEqual(Entry e1, Entry e2) {
        return e1.equalTo(e2);
    }

    public List<E> getEntries() {
        return Collections.unmodifiableList(this.entries);
    }
}
