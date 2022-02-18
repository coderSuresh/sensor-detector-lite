package com.subitech.sensordetectorlite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.MessageFormat;

public class CustomAdapter extends BaseAdapter {
    String[] name, version, vendor;
    LayoutInflater layoutInflater;
    public CustomAdapter(Context applicationContext, String[] name, String[] version, String[] vendor) {
        this.name = name;
        this.vendor = vendor;
        this.version = version;
        layoutInflater = LayoutInflater.from(applicationContext);
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.custom_list, null);
        TextView textView = view.findViewById(R.id.list_txt);
        TextView textView1 = view.findViewById(R.id.list_txt_ver);
        TextView textView2 = view.findViewById(R.id.list_txt_ven);
        textView.setText(MessageFormat.format("{0}. {1}", (i+1), name[i]));
        textView1.setText(MessageFormat.format("Version: {0}", version[i]));
        textView2.setText(MessageFormat.format("Seller: {0}", vendor[i]));
        return view;
    }
}
