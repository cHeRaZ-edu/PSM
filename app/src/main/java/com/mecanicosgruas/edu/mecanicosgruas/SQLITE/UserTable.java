package com.mecanicosgruas.edu.mecanicosgruas.SQLITE;

/**
 * Created by LUNA on 04/06/2018.
 */

public class UserTable {
    static final String TABLE_NAME = "user";
    static final String COLUMN_ID = "id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_LASTNAME = "last_name";
    static final String COLUMN_NICKNAME = "nickname0";
    static final String COLUMN_TELEFONO = "telefono";
    static final String COLUMN_COLOR = "color";


    static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    +COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    +COLUMN_NAME + " TEXT ,"
                    +COLUMN_LASTNAME + " TEXT,"
                    +COLUMN_NICKNAME + " TEXT,"
                    +COLUMN_TELEFONO + " TEXT,"
                    +COLUMN_COLOR+" TEXT"
                    +")";
}
