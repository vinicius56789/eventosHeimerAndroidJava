package com.example.heimer.Modelo;

import java.io.Serializable;

public class Evento implements Serializable {

    private int id;
    private String nome;
    private Local local;
    private String data;

    public Evento(int id, String nome, Local local, String data) {
        this.id = id;
        this.nome = nome;
        this.local = local;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeEvento() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Evento: " + getNomeEvento() + " - Local: " + getLocal() + " - Data: " + getData();
    }
}
