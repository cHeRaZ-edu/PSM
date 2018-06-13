package com.mecanicosgruas.edu.mecanicosgruas.SQLITE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LUNA on 04/06/2018.
 */

public class SQLHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "db_psm";
    private static final int DB_VERSION = 1;
    public SQLHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserTable.CREATE_TABLE);
        db.execSQL(ServiceTable.CREATE_TABLE);
        db.execSQL(ServiceDisplayTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
