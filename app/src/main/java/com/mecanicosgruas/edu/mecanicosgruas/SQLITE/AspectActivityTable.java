package com.mecanicosgruas.edu.mecanicosgruas.SQLITE;

/**
 * Created by LUNA on 04/06/2018.
 */

public class AspectActivityTable {
    static final String TABLE_NAME = "activity_aspect";
    static final String COLUMN_ID = "id";
    static final String COLUMN_NAME_ACTIVITY = "name_activity";
    static final String COLUMN_COLOR = "color";




    static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("
            +COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            +COLUMN_NAME_ACTIVITY+ " TEXT,"
            +COLUMN_COLOR+ " TEXT"
            +")";
}
