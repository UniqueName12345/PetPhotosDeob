package com.google.appinventor.components.runtime.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import java.util.List;

/* loaded from: classes.dex */
public class ListViewArrayAdapterTwoTextLinear {
    private static ComponentContainer container;
    private List<YailDictionary> currentItems;
    private int detailTextColor;
    private int detailTextSize;
    private final Filter filter;
    private List<YailDictionary> filterCurrentItems;
    private ArrayAdapter<YailDictionary> itemAdapter;
    private int textColor;
    private int textSize;

    public ListViewArrayAdapterTwoTextLinear(int textSize, int detailTextSize, int textColor, int detailTextColor, ComponentContainer container2, List<YailDictionary> items) {
        this.textSize = textSize;
        this.detailTextSize = detailTextSize;
        this.textColor = textColor;
        this.detailTextColor = detailTextColor;
        container = container2;
        this.currentItems = items;
        this.filterCurrentItems = items;
        this.filter = new Filter() { // from class: com.google.appinventor.components.runtime.util.ListViewArrayAdapterTwoTextLinear.1
            @Override // android.widget.Filter
            protected Filter.FilterResults performFiltering(CharSequence charSequence) {
                String filterQuery = charSequence.toString().toLowerCase();
                Filter.FilterResults results = new Filter.FilterResults();
                if (filterQuery == null || filterQuery.length() == 0) {
                    results.count = ListViewArrayAdapterTwoTextLinear.this.currentItems.size();
                    results.values = ListViewArrayAdapterTwoTextLinear.this.currentItems;
                } else {
                    ListViewArrayAdapterTwoTextLinear.this.filterCurrentItems.clear();
                    for (YailDictionary item : ListViewArrayAdapterTwoTextLinear.this.currentItems) {
                        if (item.get(Component.LISTVIEW_KEY_MAIN_TEXT).toString().concat(ElementsUtil.toStringEmptyIfNull(item.get(Component.LISTVIEW_KEY_DESCRIPTION))).contains(charSequence)) {
                            ListViewArrayAdapterTwoTextLinear.this.filterCurrentItems.add(item);
                        }
                    }
                    results.count = ListViewArrayAdapterTwoTextLinear.this.filterCurrentItems.size();
                    results.values = ListViewArrayAdapterTwoTextLinear.this.filterCurrentItems;
                }
                return results;
            }

            @Override // android.widget.Filter
            protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
                ListViewArrayAdapterTwoTextLinear.this.filterCurrentItems = (List) filterResults.values;
                ListViewArrayAdapterTwoTextLinear.this.itemAdapter.clear();
                for (Object o : ListViewArrayAdapterTwoTextLinear.this.filterCurrentItems) {
                    ListViewArrayAdapterTwoTextLinear.this.itemAdapter.add((YailDictionary) o);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View createView() {
        LinearLayout linearLayout = new LinearLayout(container.$context());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        linearLayout.setId(1);
        linearLayout.setOrientation(0);
        linearLayout.setPadding(15, 15, 15, 15);
        TextView textView1 = new TextView(container.$context());
        textView1.setPadding(10, 10, 10, 10);
        textView1.setGravity(3);
        textView1.setLayoutParams(new LinearLayout.LayoutParams(-1, -2, 1.0f));
        textView1.setId(2);
        TextView textView2 = new TextView(container.$context());
        textView2.setPadding(10, 10, 10, 10);
        textView2.setGravity(5);
        textView2.setLayoutParams(new LinearLayout.LayoutParams(-1, -2, 1.0f));
        textView2.setId(3);
        linearLayout.addView(textView1);
        linearLayout.addView(textView2);
        return linearLayout;
    }

    public ArrayAdapter<YailDictionary> createAdapter() {
        this.itemAdapter = new ArrayAdapter<YailDictionary>(container.$context(), 0, this.currentItems) { // from class: com.google.appinventor.components.runtime.util.ListViewArrayAdapterTwoTextLinear.2
            @Override // android.widget.ArrayAdapter, android.widget.Adapter
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = ListViewArrayAdapterTwoTextLinear.this.createView();
                TextView text1 = (TextView) view.findViewById(2);
                TextView text2 = (TextView) view.findViewById(3);
                YailDictionary row = (YailDictionary) ListViewArrayAdapterTwoTextLinear.this.filterCurrentItems.get(position);
                String val1 = ElementsUtil.toStringEmptyIfNull(row.get(Component.LISTVIEW_KEY_MAIN_TEXT));
                String val2 = ElementsUtil.toStringEmptyIfNull(row.get(Component.LISTVIEW_KEY_DESCRIPTION));
                text1.setText(val1);
                text2.setText(val2);
                text1.setTextColor(ListViewArrayAdapterTwoTextLinear.this.textColor);
                text2.setTextColor(ListViewArrayAdapterTwoTextLinear.this.detailTextColor);
                text1.setTextSize(ListViewArrayAdapterTwoTextLinear.this.textSize);
                text2.setTextSize(ListViewArrayAdapterTwoTextLinear.this.detailTextSize);
                return view;
            }

            @Override // android.widget.ArrayAdapter, android.widget.Filterable
            public Filter getFilter() {
                return ListViewArrayAdapterTwoTextLinear.this.filter;
            }
        };
        return this.itemAdapter;
    }
}
