package com.example.latihanandroid7.dataController;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PrefsHelper {
    private static PrefsHelper instance;
    SharedPreferences prefs;
    Context ctx;

    private PrefsHelper(Context ctx) {
        this.ctx = ctx;
        this.prefs = ctx.getSharedPreferences("SAMPLE", Context.MODE_PRIVATE);
    }

    public static PrefsHelper sharedInstance(Context context) {
        if (instance == null) {
            instance = new PrefsHelper(context);
        }
        return instance;
    }

    public boolean isSplash() { return prefs.getBoolean("splash", true); }

    public void setSplash(boolean s) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("splash", s);
        editor.apply();
    }
}
