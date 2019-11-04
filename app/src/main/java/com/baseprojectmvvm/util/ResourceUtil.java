package com.baseprojectmvvm.util;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.baseprojectmvvm.App;
import com.baseprojectmvvm.R;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ResourceUtil {
    private static ResourceUtil instance;

    private ResourceUtil() {

    }

    public synchronized static ResourceUtil getInstance() {
        if (instance == null) {
            instance = new ResourceUtil();
        }

        return instance;
    }

    public static int getResourceIdFromResourceName(String drawableName) {
        try {
            Class<R.drawable> res = R.drawable.class;
            Field field = res.getField(drawableName);
            return field.getInt(null);
        } catch (Exception e) {

        }
        return -1;
    }

    public Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(App.getAppContext(), resId);
    }

    public int getColor(int colorResId) {
        return ContextCompat.getColor(App.getAppContext(), colorResId);
    }

    public String getString(int resId) {
        return App.getAppContext().getString(resId);
    }

    public List<String> getStringArray(int resId) {
        String[] stringArray = App.getAppContext().getResources().getStringArray(resId);
        return Arrays.asList(stringArray);
    }

    public Typeface getFont(int font) {
        return ResourcesCompat.getFont(App.getAppContext(), font);
    }

}
