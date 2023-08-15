package com.google.appinventor.components.runtime;

import android.util.Log;
import android.view.MotionEvent;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.LineType;
import com.google.appinventor.components.common.PointStyle;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.ChartDataModel;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.CsvUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SimpleObject
/* loaded from: classes.dex */
public abstract class ChartDataBase implements Component, DataSourceChangeListener, OnChartGestureListener, OnChartValueSelectedListener {
    protected ChartDataModel<?, ?, ?, ?, ?> chartDataModel;
    private int color;
    private YailList colors;
    protected Chart container;
    protected List<String> dataFileColumns;
    private DataSource<?, ?> dataSource;
    protected String dataSourceKey;
    private String elements;
    private String label;
    private Object lastDataSourceValue;
    protected List<String> sheetsColumns;
    protected ExecutorService threadRunner;
    protected boolean useSheetHeaders;
    protected List<String> webColumns;
    private boolean initialized = false;
    private int tick = 0;

    static /* synthetic */ int access$308(ChartDataBase x0) {
        int i = x0.tick;
        x0.tick = i + 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ChartDataBase(Chart chartContainer) {
        this.container = chartContainer;
        chartContainer.addDataComponent(this);
        initChartData();
        DataSourceKey("");
        this.threadRunner = Executors.newSingleThreadExecutor();
    }

    public void setExecutorService(ExecutorService service) {
        this.threadRunner = service;
    }

    public void initChartData() {
        this.chartDataModel = this.container.createChartModel();
        Color(-16777216);
        Label("");
        this.chartDataModel.view.chart.setOnChartGestureListener(this);
        this.chartDataModel.view.chart.setOnChartValueSelectedListener(this);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int Color() {
        return this.color;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = Component.DEFAULT_VALUE_COLOR_BLACK, editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR)
    public void Color(int argb) {
        this.color = argb;
        this.chartDataModel.setColor(this.color);
        refreshChart();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public YailList Colors() {
        return this.colors;
    }

    @SimpleProperty
    public void Colors(YailList colors) {
        List<Integer> resultColors = new ArrayList<>();
        for (int i = 0; i < colors.size(); i++) {
            String color = colors.getString(i);
            try {
                long colorValue = Long.parseLong(color);
                if (colorValue > 2147483647L) {
                    colorValue -= 4294967296L;
                }
                resultColors.add(Integer.valueOf((int) colorValue));
            } catch (NumberFormatException e) {
                this.container.$form().dispatchErrorOccurredEvent(this.container, "Colors", ErrorMessages.ERROR_INVALID_CHART_DATA_COLOR, color);
            }
        }
        this.colors = YailList.makeList((List) resultColors);
        this.chartDataModel.setColors(resultColors);
        refreshChart();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public String Label() {
        return this.label;
    }

    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void Label(String text) {
        this.label = text;
        this.chartDataModel.setLabel(text);
        refreshChart();
    }

    @SimpleProperty(userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = Component.TYPEFACE_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_CHART_POINT_SHAPE)
    public void PointShape(PointStyle shape) {
        if (this.chartDataModel instanceof ScatterChartDataModel) {
            ((ScatterChartDataModel) this.chartDataModel).setPointShape(shape);
        }
    }

    @SimpleProperty(userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = Component.TYPEFACE_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_CHART_LINE_TYPE)
    public void LineType(LineType type) {
        if (this.chartDataModel instanceof LineChartBaseDataModel) {
            ((LineChartBaseDataModel) this.chartDataModel).setLineType(type);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = JSONzip.probe)
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void ElementsFromPairs(final String elements) {
        this.elements = elements;
        if (elements != null && !elements.equals("") && this.initialized) {
            this.threadRunner.execute(new Runnable() { // from class: com.google.appinventor.components.runtime.ChartDataBase.1
                @Override // java.lang.Runnable
                public void run() {
                    ChartDataBase.this.chartDataModel.setElements(elements);
                    ChartDataBase.this.refreshChart();
                }
            });
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = JSONzip.probe)
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void SpreadsheetUseHeaders(boolean useHeaders) {
        this.useSheetHeaders = useHeaders;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = JSONzip.probe)
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_DATA_FILE_COLUMN)
    public void DataFileXColumn(String column) {
        this.dataFileColumns.set(0, column);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Sets the column to parse from the attached Web component for the x values. If a column is not specified, default values for the x values will be generated instead.", userVisible = JSONzip.probe)
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void WebXColumn(String column) {
        this.webColumns.set(0, column);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = JSONzip.probe)
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void SpreadsheetXColumn(String column) {
        this.sheetsColumns.set(0, column);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = JSONzip.probe)
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_DATA_FILE_COLUMN)
    public void DataFileYColumn(String column) {
        this.dataFileColumns.set(1, column);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Sets the column to parse from the attached Web component for the y values. If a column is not specified, default values for the y values will be generated instead.", userVisible = JSONzip.probe)
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void WebYColumn(String column) {
        this.webColumns.set(1, column);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = JSONzip.probe)
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void SpreadsheetYColumn(String column) {
        this.sheetsColumns.set(1, column);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = JSONzip.probe)
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void DataSourceKey(String key) {
        this.dataSourceKey = key;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = JSONzip.probe)
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_CHART_DATA_SOURCE)
    public <K, V> void Source(DataSource<K, V> dataSource) {
        if (this.dataSource != dataSource && (this.dataSource instanceof ObservableDataSource)) {
            ((ObservableDataSource) this.dataSource).removeDataObserver(this);
        }
        this.dataSource = dataSource;
        if (this.initialized) {
            if (dataSource instanceof ObservableDataSource) {
                ((ObservableDataSource) dataSource).addDataObserver(this);
                if (this.dataSourceKey == null) {
                    return;
                }
            }
            if (dataSource instanceof DataFile) {
                importFromDataFileAsync((DataFile) dataSource, YailList.makeList((List) this.dataFileColumns));
            } else if (dataSource instanceof TinyDB) {
                ImportFromTinyDB((TinyDB) dataSource, this.dataSourceKey);
            } else if (dataSource instanceof CloudDB) {
                ImportFromCloudDB((CloudDB) dataSource, this.dataSourceKey);
            } else if (dataSource instanceof Spreadsheet) {
                importFromSpreadsheetAsync((Spreadsheet) dataSource, YailList.makeList((List) this.sheetsColumns), this.useSheetHeaders);
            } else if (dataSource instanceof Web) {
                importFromWebAsync((Web) dataSource, YailList.makeList((List) this.webColumns));
            }
        }
    }

    @SimpleFunction
    public void ImportFromList(final YailList list) {
        this.threadRunner.execute(new Runnable() { // from class: com.google.appinventor.components.runtime.ChartDataBase.2
            @Override // java.lang.Runnable
            public void run() {
                ChartDataBase.this.chartDataModel.importFromList(list);
                ChartDataBase.this.refreshChart();
            }
        });
    }

    @SimpleFunction(description = "Clears all of the data.")
    public void Clear() {
        this.threadRunner.execute(new Runnable() { // from class: com.google.appinventor.components.runtime.ChartDataBase.3
            @Override // java.lang.Runnable
            public void run() {
                ChartDataBase.this.chartDataModel.clearEntries();
                ChartDataBase.this.refreshChart();
            }
        });
    }

    @SimpleFunction
    public <K, V> void ChangeDataSource(final DataSource<K, V> source, final String keyValue) {
        this.threadRunner.execute(new Runnable() { // from class: com.google.appinventor.components.runtime.ChartDataBase.4
            @Override // java.lang.Runnable
            public void run() {
                List<String> columnsList;
                if ((source instanceof DataFile) || (source instanceof Web)) {
                    YailList keyValues = new YailList();
                    try {
                        keyValues = CsvUtil.fromCsvRow(keyValue);
                    } catch (Exception e) {
                        Log.e(getClass().getName(), e.getMessage());
                    }
                    if (source instanceof DataFile) {
                        columnsList = ChartDataBase.this.dataFileColumns;
                    } else if (source instanceof Spreadsheet) {
                        columnsList = ChartDataBase.this.sheetsColumns;
                    } else if (source instanceof Web) {
                        columnsList = ChartDataBase.this.webColumns;
                    } else {
                        throw new IllegalArgumentException(source + " is not an expected DataSource");
                    }
                    for (int i = 0; i < columnsList.size(); i++) {
                        String columnValue = "";
                        if (keyValues.size() > i) {
                            columnValue = keyValues.getString(i);
                        }
                        columnsList.set(i, columnValue);
                    }
                } else {
                    ChartDataBase.this.dataSourceKey = keyValue;
                }
                ChartDataBase.this.lastDataSourceValue = null;
                ChartDataBase.this.Source(source);
            }
        });
    }

    @SimpleFunction(description = "Un-links the currently associated Data Source component from the Chart.")
    public void RemoveDataSource() {
        this.threadRunner.execute(new Runnable() { // from class: com.google.appinventor.components.runtime.ChartDataBase.5
            @Override // java.lang.Runnable
            public void run() {
                ChartDataBase.this.Source(null);
                ChartDataBase.this.dataSourceKey = "";
                ChartDataBase.this.lastDataSourceValue = null;
                for (int i = 0; i < ChartDataBase.this.dataFileColumns.size(); i++) {
                    ChartDataBase.this.dataFileColumns.set(i, "");
                    ChartDataBase.this.sheetsColumns.set(i, "");
                    ChartDataBase.this.webColumns.set(i, "");
                }
            }
        });
    }

    @SimpleFunction(description = "Returns a List of entries with x values matching the specified x value. A single entry is represented as a List of values of the entry.")
    public YailList GetEntriesWithXValue(final String x) {
        try {
            return (YailList) this.threadRunner.submit(new Callable<YailList>() { // from class: com.google.appinventor.components.runtime.ChartDataBase.6
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public YailList call() {
                    return ChartDataBase.this.chartDataModel.findEntriesByCriterion(x, ChartDataModel.EntryCriterion.XValue);
                }
            }).get();
        } catch (InterruptedException e) {
            Log.e(getClass().getName(), e.getMessage());
            return new YailList();
        } catch (ExecutionException e2) {
            Log.e(getClass().getName(), e2.getMessage());
            return new YailList();
        }
    }

    @SimpleFunction(description = "Returns a List of entries with y values matching the specified y value. A single entry is represented as a List of values of the entry.")
    public YailList GetEntriesWithYValue(final String y) {
        try {
            return (YailList) this.threadRunner.submit(new Callable<YailList>() { // from class: com.google.appinventor.components.runtime.ChartDataBase.7
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public YailList call() {
                    return ChartDataBase.this.chartDataModel.findEntriesByCriterion(y, ChartDataModel.EntryCriterion.YValue);
                }
            }).get();
        } catch (InterruptedException e) {
            Log.e(getClass().getName(), e.getMessage());
            return new YailList();
        } catch (ExecutionException e2) {
            Log.e(getClass().getName(), e2.getMessage());
            return new YailList();
        }
    }

    @SimpleFunction(description = "Returns all the entries of the Data Series. A single entry is represented as a List of values of the entry.")
    public YailList GetAllEntries() {
        try {
            return (YailList) this.threadRunner.submit(new Callable<YailList>() { // from class: com.google.appinventor.components.runtime.ChartDataBase.8
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public YailList call() {
                    return ChartDataBase.this.chartDataModel.getEntriesAsTuples();
                }
            }).get();
        } catch (InterruptedException e) {
            Log.e(getClass().getName(), e.getMessage());
            return new YailList();
        } catch (ExecutionException e2) {
            Log.e(getClass().getName(), e2.getMessage());
            return new YailList();
        }
    }

    @SimpleFunction
    public void ImportFromTinyDB(TinyDB tinyDB, String tag) {
        final List<?> list = tinyDB.getDataValue(tag);
        updateCurrentDataSourceValue(tinyDB, tag, list);
        this.threadRunner.execute(new Runnable() { // from class: com.google.appinventor.components.runtime.ChartDataBase.9
            @Override // java.lang.Runnable
            public void run() {
                ChartDataBase.this.chartDataModel.importFromList(list);
                ChartDataBase.this.refreshChart();
            }
        });
    }

    @SimpleFunction
    public void ImportFromCloudDB(final CloudDB cloudDB, final String tag) {
        final Future<YailList> list = cloudDB.getDataValue(tag);
        this.threadRunner.execute(new Runnable() { // from class: com.google.appinventor.components.runtime.ChartDataBase.10
            @Override // java.lang.Runnable
            public void run() {
                try {
                    YailList listValue = (YailList) list.get();
                    ChartDataBase.this.updateCurrentDataSourceValue(cloudDB, tag, listValue);
                    ChartDataBase.this.chartDataModel.importFromList(listValue);
                    ChartDataBase.this.refreshChart();
                } catch (InterruptedException e) {
                    Log.e(getClass().getName(), e.getMessage());
                } catch (ExecutionException e2) {
                    Log.e(getClass().getName(), e2.getMessage());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void importFromDataFileAsync(DataFile dataFile, YailList columns) {
        final Future<YailList> dataFileColumns = dataFile.getDataValue(columns);
        this.threadRunner.execute(new Runnable() { // from class: com.google.appinventor.components.runtime.ChartDataBase.11
            @Override // java.lang.Runnable
            public void run() {
                YailList dataResult = null;
                try {
                    dataResult = (YailList) dataFileColumns.get();
                } catch (InterruptedException e) {
                    Log.e(getClass().getName(), e.getMessage());
                } catch (ExecutionException e2) {
                    Log.e(getClass().getName(), e2.getMessage());
                }
                ChartDataBase.this.chartDataModel.importFromColumns(dataResult, true);
                ChartDataBase.this.refreshChart();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void importFromSpreadsheetAsync(final Spreadsheet sheets, YailList columns, final boolean useHeaders) {
        final Future<YailList> sheetColumns = sheets.getDataValue(columns, useHeaders);
        this.threadRunner.execute(new Runnable() { // from class: com.google.appinventor.components.runtime.ChartDataBase.12
            @Override // java.lang.Runnable
            public void run() {
                YailList dataColumns = null;
                try {
                    dataColumns = (YailList) sheetColumns.get();
                } catch (InterruptedException e) {
                    Log.e(getClass().getName(), e.getMessage());
                } catch (ExecutionException e2) {
                    Log.e(getClass().getName(), e2.getMessage());
                }
                if (sheets == ChartDataBase.this.dataSource) {
                    ChartDataBase.this.updateCurrentDataSourceValue(ChartDataBase.this.dataSource, null, null);
                }
                ChartDataBase.this.chartDataModel.importFromColumns(dataColumns, useHeaders);
                ChartDataBase.this.refreshChart();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void importFromWebAsync(final Web webComponent, YailList columns) {
        final Future<YailList> webColumns = webComponent.getDataValue(columns);
        this.threadRunner.execute(new Runnable() { // from class: com.google.appinventor.components.runtime.ChartDataBase.13
            @Override // java.lang.Runnable
            public void run() {
                YailList dataColumns = null;
                try {
                    dataColumns = (YailList) webColumns.get();
                } catch (InterruptedException e) {
                    Log.e(getClass().getName(), e.getMessage());
                } catch (ExecutionException e2) {
                    Log.e(getClass().getName(), e2.getMessage());
                }
                if (webComponent == ChartDataBase.this.dataSource) {
                    ChartDataBase.this.updateCurrentDataSourceValue(ChartDataBase.this.dataSource, null, null);
                }
                ChartDataBase.this.chartDataModel.importFromColumns(dataColumns, true);
                ChartDataBase.this.refreshChart();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void refreshChart() {
        this.container.getChartView().refresh(this.chartDataModel);
    }

    @Override // com.google.appinventor.components.runtime.Component
    public HandlesEventDispatching getDispatchDelegate() {
        return this.container.getDispatchDelegate();
    }

    public void Initialize() {
        this.initialized = true;
        if (this.dataSource != null) {
            Source(this.dataSource);
        } else {
            ElementsFromPairs(this.elements);
        }
    }

    @Override // com.google.appinventor.components.runtime.DataSourceChangeListener
    public void onDataSourceValueChange(final DataSource<?, ?> component, final String key, final Object newValue) {
        if (component == this.dataSource && isKeyValid(key)) {
            this.threadRunner.execute(new Runnable() { // from class: com.google.appinventor.components.runtime.ChartDataBase.14
                @Override // java.lang.Runnable
                public void run() {
                    if (ChartDataBase.this.lastDataSourceValue instanceof List) {
                        ChartDataBase.this.chartDataModel.removeValues((List) ChartDataBase.this.lastDataSourceValue);
                    }
                    ChartDataBase.this.updateCurrentDataSourceValue(component, key, newValue);
                    if (ChartDataBase.this.lastDataSourceValue instanceof List) {
                        ChartDataBase.this.chartDataModel.importFromList((List) ChartDataBase.this.lastDataSourceValue);
                    }
                    ChartDataBase.this.refreshChart();
                }
            });
        }
    }

    @Override // com.google.appinventor.components.runtime.DataSourceChangeListener
    public void onReceiveValue(RealTimeDataSource<?, ?> component, String key, Object value) {
        boolean importData;
        final Object finalValue;
        if (component == this.dataSource) {
            if (component instanceof BluetoothClient) {
                String valueString = (String) value;
                importData = valueString.startsWith(this.dataSourceKey);
                if (importData) {
                    value = valueString.substring(this.dataSourceKey.length());
                }
                finalValue = value;
            } else {
                importData = isKeyValid(key);
                finalValue = value;
            }
            if (importData) {
                this.container.$context().runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.ChartDataBase.15
                    @Override // java.lang.Runnable
                    public void run() {
                        ChartDataBase.this.tick = ChartDataBase.this.container.getSyncedTValue(ChartDataBase.this.tick);
                        YailList tuple = YailList.makeList(Arrays.asList(Integer.valueOf(ChartDataBase.this.tick), finalValue));
                        ChartDataBase.this.chartDataModel.addTimeEntry(tuple);
                        ChartDataBase.this.refreshChart();
                        ChartDataBase.access$308(ChartDataBase.this);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCurrentDataSourceValue(DataSource<?, ?> source, String key, Object newValue) {
        if (source == this.dataSource && isKeyValid(key)) {
            if (source instanceof Web) {
                YailList columns = ((Web) source).getColumns(YailList.makeList((List) this.webColumns));
                this.lastDataSourceValue = this.chartDataModel.getTuplesFromColumns(columns, true);
            } else if (source instanceof Spreadsheet) {
                YailList columns2 = ((Spreadsheet) source).getColumns(YailList.makeList((List) this.sheetsColumns), this.useSheetHeaders);
                this.lastDataSourceValue = this.chartDataModel.getTuplesFromColumns(columns2, this.useSheetHeaders);
            } else {
                this.lastDataSourceValue = newValue;
            }
        }
    }

    private boolean isKeyValid(String key) {
        return key == null || key.equals(this.dataSourceKey);
    }

    public void onChartGestureStart(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture) {
    }

    public void onChartGestureEnd(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture) {
    }

    public void onChartLongPressed(MotionEvent motionEvent) {
    }

    public void onChartDoubleTapped(MotionEvent motionEvent) {
    }

    public void onChartSingleTapped(MotionEvent motionEvent) {
    }

    public void onChartFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
    }

    public void onChartScale(MotionEvent motionEvent, float v, float v1) {
    }

    public void onChartTranslate(MotionEvent motionEvent, float v, float v1) {
    }

    public void onValueSelected(final Entry entry, Highlight highlight) {
        this.container.$form().runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.ChartDataBase.16
            @Override // java.lang.Runnable
            public void run() {
                if (entry instanceof PieEntry) {
                    ChartDataBase.this.EntryClick(entry.getLabel(), entry.getValue());
                } else {
                    ChartDataBase.this.EntryClick(Float.valueOf(entry.getX()), entry.getY());
                }
            }
        });
    }

    @SimpleEvent
    public void EntryClick(Object x, double y) {
        EventDispatcher.dispatchEvent(this, "EntryClick", x, Double.valueOf(y));
        this.container.EntryClick(this, x, y);
    }

    public void onNothingSelected() {
    }
}
