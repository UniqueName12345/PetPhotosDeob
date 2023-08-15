package com.google.appinventor.components.runtime;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.ChartDataSourceUtil;
import com.google.appinventor.components.runtime.util.CsvUtil;
import com.google.appinventor.components.runtime.util.JsonUtil;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import kawa.lang.SyntaxForms;
import org.json.JSONException;

@DesignerComponent(category = ComponentCategory.STORAGE, description = "Component that allows reading CSV and JSON data. The DataFile contains functionality relevant to accessing CSV or JSON parsed data in the form of rows or columns. Can be used together with the ChartData2D component to import data directly from a file to the Chart. The component may also be dragged and dropped on a Chart after a file has been selected and parsed successfully to create ChartData components automatically from the file onto the Chart.", iconName = "images/dataFile.png", nonVisible = SyntaxForms.DEBUGGING, version = 1)
@SimpleObject
/* loaded from: classes.dex */
public class DataFile extends FileBase implements DataSource<YailList, Future<YailList>> {
    private YailList columnNames;
    private YailList columns;
    private YailList rows;
    private final ExecutorService threadRunner;

    public DataFile(ComponentContainer container) {
        super(container);
        this.rows = new YailList();
        this.columns = new YailList();
        this.columnNames = new YailList();
        this.threadRunner = Executors.newSingleThreadExecutor();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns a list of rows corresponding to the Data File's content.")
    public YailList Rows() {
        return getYailListPropertyHelper(new Callable<YailList>() { // from class: com.google.appinventor.components.runtime.DataFile.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public YailList call() {
                return DataFile.this.rows;
            }
        });
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns a list of columns corresponding to the Data File's content.")
    public YailList Columns() {
        return getYailListPropertyHelper(new Callable<YailList>() { // from class: com.google.appinventor.components.runtime.DataFile.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public YailList call() {
                return DataFile.this.columns;
            }
        });
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns the elements of the first row of the Data File's contents.")
    public YailList ColumnNames() {
        return getYailListPropertyHelper(new Callable<YailList>() { // from class: com.google.appinventor.components.runtime.DataFile.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public YailList call() {
                return DataFile.this.columnNames;
            }
        });
    }

    private YailList getYailListPropertyHelper(Callable<YailList> propertyCallable) {
        try {
            return (YailList) this.threadRunner.submit(propertyCallable).get();
        } catch (InterruptedException e) {
            Log.e(getClass().getName(), e.getMessage());
            return new YailList();
        } catch (ExecutionException e2) {
            Log.e(getClass().getName(), e2.getMessage());
            return new YailList();
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = JSONzip.probe)
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET)
    public void SourceFile(String source) {
        ReadFile("//" + source);
    }

    @SimpleFunction(description = "Indicates source file to load data from. The expected format of the contents of the file are either CSV or JSON.Prefix the filename with / to read from a specific file on the SD card. for instance /myFile.txt will read the file /sdcard/myFile.txt. To read assets packaged with an application (also works for the Companion) start the filename with // (two slashes). If a filename does not start with a slash, it will be read from the applications private storage (for packaged apps) and from /sdcard/AppInventor/data for the Companion.The results of the reading are stored in the Rows, Columns and ColumnNames properties of the component.")
    public void ReadFile(String fileName) {
        readFromFile(fileName);
    }

    public YailList getColumn(String column) {
        int index = this.columnNames.indexOf(column) - 1;
        return index < 0 ? new YailList() : (YailList) this.columns.getObject(index);
    }

    @Override // com.google.appinventor.components.runtime.FileBase
    protected void afterRead(String result) {
        try {
            if (result.charAt(0) == '{') {
                try {
                    this.columns = JsonUtil.getColumnsFromJson(result);
                    this.rows = ChartDataSourceUtil.getTranspose(this.columns);
                } catch (JSONException e) {
                    this.rows = CsvUtil.fromCsvTable(result);
                    this.columns = ChartDataSourceUtil.getTranspose(this.rows);
                }
            } else {
                this.rows = CsvUtil.fromCsvTable(result);
                this.columns = ChartDataSourceUtil.getTranspose(this.rows);
            }
            this.columnNames = this.rows.size() > 0 ? (YailList) this.rows.getObject(0) : new YailList();
        } catch (Exception e2) {
            Log.e(getClass().getName(), "Unable to parse DataFile", e2);
        }
    }

    @Override // com.google.appinventor.components.runtime.DataSource
    public Future<YailList> getDataValue(final YailList columns) {
        return this.threadRunner.submit(new Callable<YailList>() { // from class: com.google.appinventor.components.runtime.DataFile.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public YailList call() {
                ArrayList<YailList> resultingColumns = new ArrayList<>();
                for (int i = 0; i < columns.size(); i++) {
                    String columnName = columns.getString(i);
                    YailList column = DataFile.this.getColumn(columnName);
                    resultingColumns.add(column);
                }
                return YailList.makeList((List) resultingColumns);
            }
        });
    }
}
