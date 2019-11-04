package com.baseprojectmvvm.data.api;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import okhttp3.logging.HttpLoggingInterceptor;

class CustomHttpLogger implements HttpLoggingInterceptor.Logger {
    private static final String logName = "LumiApp";

    @Override
    public void log(String message) {
        if (!message.startsWith("{")) {
            Log.d(logName, message);
            return;
        }
        try {
            String prettyPrintJson = new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(message));
            Log.d(logName, prettyPrintJson);
        } catch (JsonSyntaxException m) {
            Log.d(logName, message);
        }
    }
}