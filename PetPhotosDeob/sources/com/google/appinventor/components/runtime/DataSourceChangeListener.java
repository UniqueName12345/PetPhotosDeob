package com.google.appinventor.components.runtime;

/* loaded from: classes.dex */
public interface DataSourceChangeListener {
    void onDataSourceValueChange(DataSource<?, ?> dataSource, String str, Object obj);

    void onReceiveValue(RealTimeDataSource<?, ?> realTimeDataSource, String str, Object obj);
}
