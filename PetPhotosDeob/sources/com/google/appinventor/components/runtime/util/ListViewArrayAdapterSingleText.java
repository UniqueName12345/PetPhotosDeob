package com.google.appinventor.components.runtime.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import java.util.List;

/* loaded from: classes.dex */
public class ListViewArrayAdapterSingleText {
    private ComponentContainer container;
    private List<YailDictionary> currentItems;
    private final Filter filter = new Filter() { // from class: com.google.appinventor.components.runtime.util.ListViewArrayAdapterSingleText.1
        @Override // android.widget.Filter
        protected Filter.FilterResults performFiltering(CharSequence charSequence) {
            String filterQuery = charSequence.toString().toLowerCase();
            Filter.FilterResults results = new Filter.FilterResults();
            if (filterQuery == null || filterQuery.length() == 0) {
                results.count = ListViewArrayAdapterSingleText.this.currentItems.size();
                results.values = ListViewArrayAdapterSingleText.this.currentItems;
            } else {
                for (YailDictionary item : ListViewArrayAdapterSingleText.this.currentItems) {
                    ListViewArrayAdapterSingleText.this.filterCurrentItems.clear();
                    if (item.get(Component.LISTVIEW_KEY_MAIN_TEXT).toString().contains(filterQuery)) {
                        ListViewArrayAdapterSingleText.this.filterCurrentItems.add(item);
                    }
                }
                results.count = ListViewArrayAdapterSingleText.this.filterCurrentItems.size();
                results.values = ListViewArrayAdapterSingleText.this.filterCurrentItems;
            }
            return results;
        }

        @Override // android.widget.Filter
        protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
            ListViewArrayAdapterSingleText.this.filterCurrentItems = (List) filterResults.values;
            ListViewArrayAdapterSingleText.this.itemAdapter.clear();
            if (ListViewArrayAdapterSingleText.this.filterCurrentItems != null) {
                for (int i = 0; i < ListViewArrayAdapterSingleText.this.filterCurrentItems.size(); i++) {
                    ListViewArrayAdapterSingleText.this.itemAdapter.add(ListViewArrayAdapterSingleText.this.filterCurrentItems.get(i));
                }
            }
        }
    };
    private List<YailDictionary> filterCurrentItems;
    private ArrayAdapter<YailDictionary> itemAdapter;
    private int textColor;
    private int textSize;

    public ListViewArrayAdapterSingleText(int textSize, int textColor, ComponentContainer container, List<YailDictionary> items) {
        this.textSize = textSize;
        this.textColor = textColor;
        this.container = container;
        this.currentItems = items;
        this.filterCurrentItems = items;
    }

    public ArrayAdapter<YailDictionary> createAdapter() {
        this.itemAdapter = new ArrayAdapter<YailDictionary>(this.container.$context(), 17367043, this.currentItems) { // from class: com.google.appinventor.components.runtime.util.ListViewArrayAdapterSingleText.2
            @Override // android.widget.ArrayAdapter, android.widget.Adapter
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(16908308);
                YailDictionary row = (YailDictionary) ListViewArrayAdapterSingleText.this.filterCurrentItems.get(position);
                String val1 = ElementsUtil.toStringEmptyIfNull(row.get(Component.LISTVIEW_KEY_MAIN_TEXT));
                text1.setText(val1);
                text1.setTextColor(ListViewArrayAdapterSingleText.this.textColor);
                text1.setTextSize(ListViewArrayAdapterSingleText.this.textSize);
                return view;
            }

            @Override // android.widget.ArrayAdapter, android.widget.Filterable
            public Filter getFilter() {
                return ListViewArrayAdapterSingleText.this.filter;
            }
        };
        return this.itemAdapter;
    }
}
