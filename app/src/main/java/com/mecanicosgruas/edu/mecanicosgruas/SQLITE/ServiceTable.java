package com.mecanicosgruas.edu.mecanicosgruas.SQLITE;

/**
 * Created by LUNA on 04/06/2018.
 */

public class ServiceTable {
    static final String TABLE_NAME = "service";
    static final String COLUMN_ID = "id";
    static final String COLUMN_NAME = "service";
    static final String COLUMN_DESC_SERVICE = "desc_service";
    static final String COLUMN_CIUDAD = "ciudad";
    static final String COLUMN_COLONIA = "colonia";
    static final String COLUMN_CALLE = "calle";
    static final String COLUMN_NUM = "num";


    static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            +COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            +COLUMN_NAME + " TEXT,"
            +COLUMN_DESC_SERVICE + " TEXT,"
            +COLUMN_CIUDAD + " TEXT,"
            +COLUMN_COLONIA + " TEXT,"
            +COLUMN_CALLE + " TEXT,"
            +COLUMN_NUM+ " TEXT"
            +")";


}
