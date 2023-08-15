package com.google.appinventor.components.runtime.util;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import java.io.IOException;
import java.util.List;

/* loaded from: classes.dex */
public class ListViewArrayAdapterImageTwoText {
    private ComponentContainer container;
    private List<YailDictionary> currentItems;
    private int detailTextColor;
    private int detailTextSize;
    private final Filter filter = new Filter() { // from class: com.google.appinventor.components.runtime.util.ListViewArrayAdapterImageTwoText.1
        @Override // android.widget.Filter
        protected Filter.FilterResults performFiltering(CharSequence charSequence) {
            String filterQuery = charSequence.toString().toLowerCase();
            Filter.FilterResults results = new Filter.FilterResults();
            if (filterQuery == null || filterQuery.length() == 0) {
                results.count = ListViewArrayAdapterImageTwoText.this.currentItems.size();
                results.values = ListViewArrayAdapterImageTwoText.this.currentItems;
            } else {
                ListViewArrayAdapterImageTwoText.this.filterCurrentItems.clear();
                for (YailDictionary item : ListViewArrayAdapterImageTwoText.this.currentItems) {
                    if (item.get(Component.LISTVIEW_KEY_MAIN_TEXT).toString().concat(ElementsUtil.toStringEmptyIfNull(item.get(Component.LISTVIEW_KEY_DESCRIPTION))).contains(charSequence)) {
                        ListViewArrayAdapterImageTwoText.this.filterCurrentItems.add(item);
                    }
                }
                results.count = ListViewArrayAdapterImageTwoText.this.filterCurrentItems.size();
                results.values = ListViewArrayAdapterImageTwoText.this.filterCurrentItems;
            }
            return results;
        }

        @Override // android.widget.Filter
        protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
            ListViewArrayAdapterImageTwoText.this.itemAdapter.clear();
            for (YailDictionary item : ListViewArrayAdapterImageTwoText.this.filterCurrentItems) {
                ListViewArrayAdapterImageTwoText.this.itemAdapter.add(item);
            }
        }
    };
    private List<YailDictionary> filterCurrentItems;
    private int imageHeight;
    private int imageWidth;
    private ArrayAdapter<YailDictionary> itemAdapter;
    private int textColor;
    private int textSize;

    public ListViewArrayAdapterImageTwoText(int textSize, int detailTextSize, int textColor, int detailTextColor, int imageWidth, int imageHeight, ComponentContainer container, List<YailDictionary> items) {
        this.textSize = textSize;
        this.detailTextSize = detailTextSize;
        this.textColor = textColor;
        this.detailTextColor = detailTextColor;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.container = container;
        this.currentItems = items;
        this.filterCurrentItems = items;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View createView() {
        LinearLayout linearLayout = new LinearLayout(this.container.$context());
        linearLayout.setOrientation(0);
        linearLayout.setPadding(15, 15, 15, 15);
        ImageView imageView = new ImageView(this.container.$context());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(this.imageWidth, this.imageHeight));
        imageView.setId(1);
        LinearLayout textLayout = new LinearLayout(this.container.$context());
        textLayout.setOrientation(1);
        textLayout.setPadding(10, 10, 10, 10);
        TextView textView1 = new TextView(this.container.$context());
        textView1.setPadding(10, 10, 10, 10);
        textView1.setId(2);
        TextView textView2 = new TextView(this.container.$context());
        textView2.setPadding(10, 10, 10, 10);
        textView2.setId(3);
        textLayout.addView(textView1);
        textLayout.addView(textView2);
        linearLayout.addView(imageView);
        linearLayout.addView(textLayout);
        return linearLayout;
    }

    public void setImage(ImageView imageView, String imagePath) {
        Drawable drawable = null;
        if (imagePath != null) {
            try {
                drawable = MediaUtil.getBitmapDrawable(this.container.$form(), imagePath);
            } catch (IOException e) {
                Log.e(Component.LISTVIEW_KEY_IMAGE, "Unable to load " + imagePath);
            }
            ViewUtil.setImage(imageView, drawable);
        }
    }

    public ArrayAdapter<YailDictionary> createAdapter() {
        this.itemAdapter = new ArrayAdapter<YailDictionary>(this.container.$context(), 17367044, 16908308, this.currentItems) { // from class: com.google.appinventor.components.runtime.util.ListViewArrayAdapterImageTwoText.2
            @Override // android.widget.ArrayAdapter, android.widget.Adapter
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = ListViewArrayAdapterImageTwoText.this.createView();
                ImageView image = (ImageView) view.findViewById(1);
                TextView text1 = (TextView) view.findViewById(2);
                TextView text2 = (TextView) view.findViewById(3);
                YailDictionary row = (YailDictionary) ListViewArrayAdapterImageTwoText.this.filterCurrentItems.get(position);
                String imageVal = row.get(Component.LISTVIEW_KEY_IMAGE).toString();
                String val1 = row.get(Component.LISTVIEW_KEY_MAIN_TEXT).toString();
                String val2 = ElementsUtil.toStringEmptyIfNull(row.get(Component.LISTVIEW_KEY_DESCRIPTION));
                ListViewArrayAdapterImageTwoText.this.setImage(image, imageVal);
                text1.setText(val1);
                text2.setText(val2);
                text1.setTextColor(ListViewArrayAdapterImageTwoText.this.textColor);
                text2.setTextColor(ListViewArrayAdapterImageTwoText.this.detailTextColor);
                text1.setTextSize(ListViewArrayAdapterImageTwoText.this.textSize);
                text2.setTextSize(ListViewArrayAdapterImageTwoText.this.detailTextSize);
                return view;
            }

            @Override // android.widget.ArrayAdapter, android.widget.Filterable
            public Filter getFilter() {
                return ListViewArrayAdapterImageTwoText.this.filter;
            }
        };
        return this.itemAdapter;
    }
}
