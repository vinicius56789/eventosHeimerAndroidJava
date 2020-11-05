package com.example.heimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.heimer.Modelo.Evento;
import com.example.heimer.Modelo.Local;
import com.example.heimer.database.EventoDAO;
import com.example.heimer.database.LocalDAO;

public class CadastroEvento extends AppCompatActivity {

    private int id = 0;
    private Spinner nome_local;
    private ArrayAdapter<Local> localAdapter;
    private EditText nome;
    private EditText data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
        setTitle("Cadastro de Eventos");
        nome_local = findViewById(R.id.spinnerLocal);
        nome = findViewById(R.id.etNome);
        data = findViewById(R.id.etData);
        carregarLocais();
        carregarEvento();
    }

    private void carregarLocais(){
        LocalDAO localDAO = new LocalDAO(getBaseContext());
        localAdapter = new ArrayAdapter<Local>(CadastroEvento.this, android.R.layout.simple_spinner_item, localDAO.listar());
        nome_local.setAdapter(localAdapter);
    }

    private void carregarEvento(){
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null && intent.getExtras().get("eventoEdicao") != null){
            Evento evento = (Evento) intent.getExtras().get("eventoEdicao");
            nome.setText(evento.getNomeEvento());
            data.setText(evento.getData());
            int localEvento = obterPosicaoLocal(evento.getLocal());
            nome_local.setSelection(localEvento);
            id = evento.getId();
        }
    }

    public void onClickVoltar(View v){
        finish();
    }

    public void onClickCadastrar(View v){
        String sNome = nome.getText().toString();
        String sData = data.getText().toString();
        int posicaoLocal = nome_local.getSelectedItemPosition();
        Local local = (Local) localAdapter.getItem(posicaoLocal);
        Evento evento = new Evento(id, sNome, local, sData);
        EventoDAO eventoDAO = new EventoDAO(getBaseContext());
        boolean salvou = eventoDAO.salvar(evento);
        if(salvou){
            finish();
        }else{
            Toast.makeText(CadastroEvento.this, "Erro ao salvar, tente mais tarde", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    private int obterPosicaoLocal(Local local){
        for(int posicao = 0; posicao < localAdapter.getCount(); posicao++){
            if(localAdapter.getItem(posicao).getId() == local.getId()){
                return posicao;
            }
        }
        return 0;
    }

    public void onClickExcluir(View v){
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null && intent.getExtras().getSerializable("eventoExcluir") != null){
            Evento evento = (Evento) intent.getExtras().getSerializable("eventoExcluir");
            EventoDAO eventoDAO = new EventoDAO(getBaseContext());
            eventoDAO.excluir(evento);
            finish();
        }else{
            Toast.makeText(CadastroEvento.this, "Item não criado, ímpossivel Excluir.", Toast.LENGTH_LONG).show();
        }
    }
}