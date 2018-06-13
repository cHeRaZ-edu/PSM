package com.mecanicosgruas.edu.mecanicosgruas.SQLITE;

/**
 * Created by LUNA on 13/06/2018.
 */

public class ServiceDisplayTable {
    static final String TABLE_NAME  = "table_service_display";
    static final String COLUMN_ID = "id";
    static final String COLUMN_NAME = "name_service";
    static final String COLUMN_CIUDAD = "ciudad";
    static final String COLUMN_TELEFONO = "telefono";
    static final String COLUMN_ENDPOINT_IMG = "endpointImg";
    static final String COLUMN_NUM_STARTS = "num_stars";

    static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    +COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    +COLUMN_NAME + " TEXT,"
                    +COLUMN_CIUDAD + " TEXT,"
                    +COLUMN_TELEFONO + " TEXT,"
                    +COLUMN_ENDPOINT_IMG + " TEXT,"
                    +COLUMN_NUM_STARTS + " TEXT"
                    +")";
}
