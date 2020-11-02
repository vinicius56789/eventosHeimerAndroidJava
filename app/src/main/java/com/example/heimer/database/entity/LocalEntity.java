package com.example.heimer.database.entity;

import android.provider.BaseColumns;

public class LocalEntity implements BaseColumns {

    private LocalEntity(){}

    public static final String TABLE_NAME = "local";
    public static final String COLUMN_NAME_NOME = "nome";
    public static final String COLUMN_NAME_BAIRRO = "bairro";
    public static final String COLUMN_NAME_CIDADE = "cidade";
    public static final String COLUMN_NAME_CAPACIDADE = "capacidade";

}
