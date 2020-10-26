package com.example.heimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.heimer.Modelo.Evento;
import com.example.heimer.database.ProdutoDAO;

import java.text.SimpleDateFormat;

public class CadastroEvento extends AppCompatActivity {

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
        setTitle("Cadastro de Eventos");
        carregarEvento();
    }

    private void carregarEvento(){
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null && intent.getExtras().get("eventoEdicao") != null){
            Evento evento = (Evento) intent.getExtras().get("eventoEdicao");
            EditText nome = findViewById(R.id.etNome);
            EditText local = findViewById(R.id.etLocal);
            EditText data = findViewById(R.id.etData);
            nome.setText(evento.getNome());
            local.setText(evento.getLocal());
            data.setText(evento.getData());
            id = evento.getId();
        }
    }

    public void onClickVoltar(View v){
        finish();
    }

    public void onClickCadastrar(View v){
        EditText etNome = findViewById(R.id.etNome);
        EditText etLocal = findViewById(R.id.etLocal);
        EditText etData = findViewById(R.id.etData);
        String nome = etNome.getText().toString();
        String local = etLocal.getText().toString();
        String data = etData.getText().toString();
        Evento evento = new Evento(id, nome, local, data);
        ProdutoDAO produtoDAO = new ProdutoDAO(getBaseContext());
        boolean salvou = produtoDAO.salvar(evento);
        if(salvou){
            finish();
        }else{
            Toast.makeText(CadastroEvento.this, "Erro ao salvar, tente mais tarde", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    public void onClickExcluir(View v){
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null && intent.getExtras().getSerializable("eventoExcluir") != null){
            Evento evento = (Evento) intent.getExtras().getSerializable("eventoExcluir");
            ProdutoDAO produtoDAO = new ProdutoDAO(getBaseContext());
            produtoDAO.excluir(evento);
            finish();
        }else{
            Toast.makeText(CadastroEvento.this, "Item não criado, ímpossivel Excluir.", Toast.LENGTH_LONG).show();
        }
    }
}