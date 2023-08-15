package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.common.ChartType;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.ElementsUtil;
import com.google.appinventor.components.runtime.util.OnInitializeListener;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@DesignerComponent(category = ComponentCategory.CHARTS, description = "A component that allows visualizing data", version = 2)
@UsesLibraries(libraries = "mpandroidchart.jar")
@SimpleObject
/* loaded from: classes.dex */
public class Chart extends AndroidViewComponent implements ComponentContainer, OnInitializeListener {
    private int backgroundColor;
    private ChartView<?, ?, ?, ?, ?> chartView;
    private final ArrayList<ChartDataBase> dataComponents;
    private String description;
    private boolean gridEnabled;
    private YailList labels;
    private boolean legendEnabled;
    private int pieRadius;
    private int tick;
    private ChartType type;
    private final RelativeLayout view;
    private boolean zeroX;
    private boolean zeroY;

    public Chart(ComponentContainer container) {
        super(container);
        this.tick = 1;
        this.view = new RelativeLayout(container.$context());
        container.$add(this);
        this.dataComponents = new ArrayList<>();
        Type(ChartType.Line);
        Width(176);
        Height(144);
        BackgroundColor(0);
        Description("");
        PieRadius(100);
        LegendEnabled(true);
        GridEnabled(true);
        Labels(new YailList());
        XFromZero(false);
        YFromZero(false);
        $form().registerForOnInitialize(this);
    }

    @Override // com.google.appinventor.components.runtime.AndroidViewComponent
    public View getView() {
        return this.view;
    }

    @Override // com.google.appinventor.components.runtime.ComponentContainer
    public Activity $context() {
        return this.container.$context();
    }

    @Override // com.google.appinventor.components.runtime.ComponentContainer
    public Form $form() {
        return this.container.$form();
    }

    @Override // com.google.appinventor.components.runtime.ComponentContainer
    public void $add(AndroidViewComponent component) {
        throw new UnsupportedOperationException("ChartBase.$add() called");
    }

    @Override // com.google.appinventor.components.runtime.ComponentContainer
    public void setChildWidth(AndroidViewComponent component, int width) {
        throw new UnsupportedOperationException("ChartBase.setChildWidth called");
    }

    @Override // com.google.appinventor.components.runtime.ComponentContainer
    public void setChildHeight(AndroidViewComponent component, int height) {
        throw new UnsupportedOperationException("ChartBase.setChildHeight called");
    }

    @Override // com.google.appinventor.components.runtime.ComponentContainer
    public List<Component> getChildren() {
        return new ArrayList(this.dataComponents);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = JSONzip.probe)
    public ChartType Type() {
        return this.type;
    }

    @SimpleProperty(description = "Specifies the chart's type (area, bar, pie, scatter), which determines how to visualize the data.", userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = Component.TYPEFACE_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_CHART_TYPE)
    public void Type(ChartType type) {
        boolean chartViewExists = this.chartView != null;
        ChartView<?, ?, ?, ?, ?> newChartView = createChartViewFromType(type);
        if (chartViewExists) {
            this.view.removeView(this.chartView.getView());
        }
        this.type = type;
        this.chartView = newChartView;
        this.view.addView(this.chartView.getView(), 0);
        if (chartViewExists) {
            reinitializeChart();
        }
    }

    private ChartView<?, ?, ?, ?, ?> createChartViewFromType(ChartType type) {
        switch (type) {
            case Line:
                return new LineChartView(this);
            case Scatter:
                return new ScatterChartView(this);
            case Area:
                return new AreaChartView(this);
            case Bar:
                return new BarChartView(this);
            case Pie:
                return new PieChartView(this);
            default:
                throw new IllegalArgumentException("Invalid Chart type specified: " + type);
        }
    }

    private void reinitializeChart() {
        Iterator<ChartDataBase> it = this.dataComponents.iterator();
        while (it.hasNext()) {
            ChartDataBase dataComponent = it.next();
            dataComponent.initChartData();
        }
        Description(this.description);
        BackgroundColor(this.backgroundColor);
        LegendEnabled(this.legendEnabled);
        GridEnabled(this.gridEnabled);
        Labels(this.labels);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public String Description() {
        return this.description;
    }

    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void Description(String text) {
        this.description = text;
        this.chartView.setDescription(this.description);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int BackgroundColor() {
        return this.backgroundColor;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = Component.DEFAULT_VALUE_COLOR_NONE, editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR)
    public void BackgroundColor(int argb) {
        this.backgroundColor = argb;
        this.chartView.setBackgroundColor(argb);
    }

    @SimpleProperty(description = "Sets the Pie Radius of a Pie Chart from 0% to 100%, where the percentage indicates the percentage of the hole fill. 100% means that a full Pie Chart is drawn, while values closer to 0% correspond to hollow Pie Charts.", userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "100", editorType = PropertyTypeConstants.PROPERTY_TYPE_CHART_PIE_RADIUS)
    public void PieRadius(int percent) {
        this.pieRadius = percent;
        if (this.chartView instanceof PieChartView) {
            ((PieChartView) this.chartView).setPieRadius(percent);
        }
    }

    @SimpleProperty
    public boolean LegendEnabled() {
        return this.legendEnabled;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void LegendEnabled(boolean enabled) {
        this.legendEnabled = enabled;
        this.chartView.setLegendEnabled(enabled);
    }

    @SimpleProperty
    public boolean GridEnabled() {
        return this.gridEnabled;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void GridEnabled(boolean enabled) {
        this.gridEnabled = enabled;
        if (this.chartView instanceof AxisChartView) {
            ((AxisChartView) this.chartView).setGridEnabled(enabled);
        }
    }

    @SimpleProperty
    public YailList Labels() {
        return this.labels;
    }

    @SimpleProperty(description = "Changes the Chart's X axis labels to the specified List of Strings,  provided that the Chart Type is set to a Chart with an Axis (applies to Area, Bar, Line, Scatter Charts). The labels are applied in order, starting from the smallest x value on the Chart, and continuing in order. If a label is not specified for an x value, a default value is used (the x value of the axis tick at that location).")
    public void Labels(YailList labels) {
        this.labels = labels;
        if (this.chartView instanceof AxisChartView) {
            List<String> stringLabels = new ArrayList<>();
            for (int i = 0; i < labels.size(); i++) {
                String label = labels.getString(i);
                stringLabels.add(label);
            }
            ((AxisChartView) this.chartView).setLabels(stringLabels);
        }
    }

    @SimpleProperty(userVisible = JSONzip.probe)
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void LabelsFromString(String labels) {
        YailList labelsList = ElementsUtil.elementsFromString(labels);
        Labels(labelsList);
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void XFromZero(boolean zero) {
        this.zeroX = zero;
        if (this.chartView instanceof AxisChartView) {
            ((AxisChartView) this.chartView).setXMinimum(zero);
        }
    }

    @SimpleProperty
    public boolean XFromZero() {
        return this.zeroX;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void YFromZero(boolean zero) {
        this.zeroY = zero;
        if (this.chartView instanceof AxisChartView) {
            ((AxisChartView) this.chartView).setYMinimum(zero);
        }
    }

    @SimpleProperty
    public boolean YFromZero() {
        return this.zeroY;
    }

    @SimpleFunction
    public void SetDomain(double minimum, double maximum) {
        this.zeroX = minimum == 0.0d;
        if (this.chartView instanceof AxisChartView) {
            ((AxisChartView) this.chartView).setXBounds(minimum, maximum);
        }
    }

    @SimpleFunction
    public void SetRange(double minimum, double maximum) {
        this.zeroY = minimum == 0.0d;
        if (this.chartView instanceof AxisChartView) {
            ((AxisChartView) this.chartView).setYBounds(minimum, maximum);
        }
    }

    @SimpleEvent
    public void EntryClick(Component series, Object x, double y) {
        EventDispatcher.dispatchEvent(this, "EntryClick", series, x, Double.valueOf(y));
    }

    public ChartDataModel<?, ?, ?, ?, ?> createChartModel() {
        return this.chartView.createChartModel();
    }

    public void refresh() {
        this.chartView.refresh();
    }

    public ChartView<?, ?, ?, ?, ?> getChartView() {
        return this.chartView;
    }

    public void addDataComponent(ChartDataBase dataComponent) {
        this.dataComponents.add(dataComponent);
    }

    @Override // com.google.appinventor.components.runtime.util.OnInitializeListener
    public void onInitialize() {
        if (this.chartView instanceof PieChartView) {
            ((PieChartView) this.chartView).setPieRadius(this.pieRadius);
            this.chartView.refresh();
        }
    }

    public int getSyncedTValue(int dataSeriesT) {
        int returnValue;
        if (this.tick - dataSeriesT > 1) {
            returnValue = this.tick;
        } else {
            returnValue = dataSeriesT;
        }
        this.tick = returnValue + 1;
        return returnValue;
    }
}
