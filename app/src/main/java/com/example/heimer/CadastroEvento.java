package com.example.heimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.heimer.Modelo.Evento;

public class CadastroEvento extends AppCompatActivity {

    private final int RESULT_CODE_NOVO_EVENTO = 1;
    private final int RESULT_CODE_EDITAR_EVENTO = 20;
    private final int RESULT_CODE_EXCLUIR_EVENTO = 30;
    private boolean edicao = false;
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
            edicao = true;
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
        Intent intent = new Intent();
        if(edicao){
            intent.putExtra("eventoEditado", evento);
            setResult(RESULT_CODE_EDITAR_EVENTO, intent);
        }else{
            intent.putExtra("novoEvento", evento);
            setResult(RESULT_CODE_NOVO_EVENTO, intent);
        }
        finish();
    }

    public void onClickExcluir(View v){
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null && intent.getExtras().getSerializable("eventoExcluir") != null){
            Evento evento = (Evento) intent.getExtras().getSerializable("eventoExcluir");
            intent.putExtra("eventoExcluido", evento);
            setResult(RESULT_CODE_EXCLUIR_EVENTO, intent);
            finish();
        }else{
            Toast.makeText(CadastroEvento.this, "Item não criado, ímpossivel Excluir.", Toast.LENGTH_LONG).show();
        }
    }
}