package com.example.heimer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.heimer.Modelo.Evento;
import com.example.heimer.Modelo.Local;
import com.example.heimer.database.entity.EventoEntity;
import com.example.heimer.database.entity.LocalEntity;

import java.util.ArrayList;
import java.util.List;

public class EventoDAO {

    private String SQL_LISTAR_TODOS = "SELECT evento._id, evento.nome, evento.data, evento.local, local.nome, local.bairro, local.cidade, local.capacidade FROM " +
            EventoEntity.TABLE_NAME +  " INNER JOIN " + LocalEntity.TABLE_NAME + " ON " +
            EventoEntity.TABLE_NAME + "." + EventoEntity.COLUMN_NAME_LOCAL + " = " + LocalEntity.TABLE_NAME + "." + LocalEntity._ID;
    private DBGateway dbGateway;

    public EventoDAO(Context context){
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean salvar(Evento evento){
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventoEntity.COLUMN_NAME_NOME, evento.getNome());
        contentValues.put(EventoEntity.COLUMN_NAME_DATA, evento.getData());
        contentValues.put(EventoEntity.COLUMN_NAME_LOCAL, evento.getLocal().getId());
        if(evento.getId() > 0){
            return dbGateway.getDatabase().update(EventoEntity.TABLE_NAME, contentValues, EventoEntity._ID + "=?", new String[]{String.valueOf(evento.getId())}) > 0;
        }
        return dbGateway.getDatabase().insert(EventoEntity.TABLE_NAME, null, contentValues) > 0;
    }

    public List<Evento> listar(){
        List<Evento> eventos = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_LISTAR_TODOS, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(EventoEntity._ID));
            int idLocal = cursor.getInt(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_LOCAL));
            String nome = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_NOME));
            String nomeLocal = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_NOME));
            String bairro = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CIDADE));
            int capacidade = cursor.getInt(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CAPACIDADE));
            Local local = new Local(idLocal, nomeLocal, bairro, cidade, capacidade);
            String data =  cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_DATA));
            eventos.add(new Evento(id, nome, local, data));
        }
        cursor.close();
        return eventos;
    }

    public boolean excluir(Evento evento){
        return dbGateway.getDatabase().delete(EventoEntity.TABLE_NAME, EventoEntity._ID + "=?", new String[]{String.valueOf(evento.getId())}) > 0;
    }

}
