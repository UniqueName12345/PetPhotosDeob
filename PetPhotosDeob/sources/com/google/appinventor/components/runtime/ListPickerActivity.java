package com.google.appinventor.components.runtime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.google.appinventor.components.runtime.util.AnimationUtil;

/* loaded from: classes.dex */
public class ListPickerActivity extends AppInventorCompatActivity implements AdapterView.OnItemClickListener {
    static int backgroundColor;
    static int itemColor;
    MyAdapter adapter;
    private String closeAnim = "";
    private android.widget.ListView listView;
    EditText txtSearchBox;

    @Override // com.google.appinventor.components.runtime.AppInventorCompatActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        styleTitleBar();
        android.widget.LinearLayout viewLayout = new android.widget.LinearLayout(this);
        viewLayout.setOrientation(1);
        Intent myIntent = getIntent();
        if (myIntent.hasExtra(ListPicker.LIST_ACTIVITY_ANIM_TYPE)) {
            this.closeAnim = myIntent.getStringExtra(ListPicker.LIST_ACTIVITY_ANIM_TYPE);
        }
        if (myIntent.hasExtra(ListPicker.LIST_ACTIVITY_ORIENTATION_TYPE)) {
            String orientation = myIntent.getStringExtra(ListPicker.LIST_ACTIVITY_ORIENTATION_TYPE).toLowerCase();
            if (orientation.equals("portrait")) {
                setRequestedOrientation(1);
            } else if (orientation.equals("landscape")) {
                setRequestedOrientation(0);
            }
        }
        if (myIntent.hasExtra(ListPicker.LIST_ACTIVITY_TITLE)) {
            String title = myIntent.getStringExtra(ListPicker.LIST_ACTIVITY_TITLE);
            setTitle(title);
        }
        if (myIntent.hasExtra(ListPicker.LIST_ACTIVITY_ARG_NAME)) {
            String[] items = getIntent().getStringArrayExtra(ListPicker.LIST_ACTIVITY_ARG_NAME);
            this.listView = new android.widget.ListView(this);
            this.listView.setOnItemClickListener(this);
            this.listView.setScrollingCacheEnabled(false);
            itemColor = myIntent.getIntExtra(ListPicker.LIST_ACTIVITY_ITEM_TEXT_COLOR, -1);
            backgroundColor = myIntent.getIntExtra(ListPicker.LIST_ACTIVITY_BACKGROUND_COLOR, -16777216);
            viewLayout.setBackgroundColor(backgroundColor);
            this.adapter = new MyAdapter(this, items);
            this.listView.setAdapter((ListAdapter) this.adapter);
            String showFilterBar = myIntent.getStringExtra(ListPicker.LIST_ACTIVITY_SHOW_SEARCH_BAR);
            this.txtSearchBox = new EditText(this);
            this.txtSearchBox.setSingleLine(true);
            this.txtSearchBox.setWidth(-2);
            this.txtSearchBox.setPadding(10, 10, 10, 10);
            this.txtSearchBox.setHint("Search list...");
            if (!isClassicMode()) {
                this.txtSearchBox.setBackgroundColor(-1);
            }
            if (showFilterBar == null || !showFilterBar.equalsIgnoreCase("true")) {
                this.txtSearchBox.setVisibility(8);
            }
            this.txtSearchBox.addTextChangedListener(new TextWatcher() { // from class: com.google.appinventor.components.runtime.ListPickerActivity.1
                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    ListPickerActivity.this.adapter.getFilter().filter(cs);
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable arg0) {
                }
            });
        } else {
            setResult(0);
            finish();
            AnimationUtil.ApplyCloseScreenAnimation(this, this.closeAnim);
        }
        viewLayout.addView(this.txtSearchBox);
        viewLayout.addView(this.listView);
        setContentView(viewLayout);
        viewLayout.requestLayout();
        InputMethodManager imm = (InputMethodManager) getSystemService("input_method");
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        getWindow().setSoftInputMode(3);
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [android.widget.Adapter] */
    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selected = (String) parent.getAdapter().getItem(position);
        Intent resultIntent = new Intent();
        resultIntent.putExtra(ListPicker.LIST_ACTIVITY_RESULT_NAME, selected);
        resultIntent.putExtra(ListPicker.LIST_ACTIVITY_RESULT_INDEX, position + 1);
        this.closeAnim = selected;
        setResult(-1, resultIntent);
        finish();
        AnimationUtil.ApplyCloseScreenAnimation(this, this.closeAnim);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        AnimationUtil.ApplyCloseScreenAnimation(this, this.closeAnim);
        super.onBackPressed();
    }

    /* loaded from: classes.dex */
    private static class MyAdapter extends ArrayAdapter<String> {
        private final Context mContext;

        public MyAdapter(Context context, String[] items) {
            super(context, 17367040, items);
            this.mContext = context;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public long getItemId(int position) {
            return getItem(position).hashCode();
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = (TextView) convertView;
            if (tv == null) {
                tv = (TextView) LayoutInflater.from(this.mContext).inflate(17367043, parent, false);
            }
            tv.setText(getItem(position));
            tv.setTextColor(ListPickerActivity.itemColor);
            return tv;
        }
    }
}
