package com.mycards.api;

import android.os.AsyncTask;

import com.mycards.business.Model;

public class Delete extends AsyncTask<Object, Void, Object> {

    private Model model;

    @Override
    protected Object doInBackground(Object... objects) {
        model = (Model) objects[0];
        new API(model).delete();

        return null;
    }
}
