package com.mycards.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mycards.business.Model;
import com.mycards003.app.R;

import java.util.ArrayList;
import java.util.List;

public class Download extends AsyncTask<Object, Void, Object> {
    private Activity activity;
    private AdapterView adapterView;

    @Override
    protected Object doInBackground(Object... objects) {
        List<Model> list = new ArrayList<Model>();
        Model model = (Model) objects[0];
        this.activity = (Activity) objects[1];
        this.adapterView = (AdapterView) objects[2];

        list.addAll(new API(model).list());

        return list;
    }

    @Override
    protected void onPostExecute(Object result) {
        ArrayAdapter<Model> arrayAdapter = new ArrayAdapter<Model>(activity, android.R.layout.simple_list_item_1, (List<Model>) result);
        adapterView.setAdapter(arrayAdapter);
    }
}