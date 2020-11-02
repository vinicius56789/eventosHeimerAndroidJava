package com.example.heimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.heimer.Modelo.Local;
import com.example.heimer.database.LocalDAO;

public class CadastroLocal extends AppCompatActivity {

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_local);
        setTitle("Cadastro de Locais");
        carregarLocal();
    }

    private void carregarLocal(){
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null && intent.getExtras().get("localEditar") != null){
            Local local = (Local) intent.getExtras().get("localEditar");
            EditText nome = findViewById(R.id.etNomeLocal);
            EditText bairro = findViewById(R.id.etBairro);
            EditText cidade = findViewById(R.id.etCidade);
            EditText capacidade = findViewById(R.id.etCapacidade);
            nome.setText(local.getNome());
            bairro.setText(local.getBairro());
            cidade.setText(local.getCidade());
            capacidade.setText(local.getCapacidade());
            id = local.getId();
        }
    }

    public void onClickVoltar(View v){
        finish();
    }

    public void onClickCadastrar(View v){
        EditText etNome = findViewById(R.id.etNomeLocal);
        EditText etBairro = findViewById(R.id.etBairro);
        EditText etCidade = findViewById(R.id.etCidade);
        EditText etCapacidade = findViewById(R.id.etCapacidade);
        String nome = etNome.getText().toString();
        String bairro = etBairro.getText().toString();
        String cidade = etCidade.getText().toString();
        int capacidade = Integer.parseInt(etCapacidade.getText().toString());
        Local local = new Local(id, nome, bairro, cidade, capacidade);
        LocalDAO localDAO = new LocalDAO(getBaseContext());
        boolean salvou = localDAO.salvar(local);
        if(salvou){
            finish();
        }else{
            Toast.makeText(CadastroLocal.this, "Erro ao salvar, tente mais tarde", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    public void onClickExcluir(View v){
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null && intent.getExtras().getSerializable("localExcluir") != null){
            Local local = (Local) intent.getExtras().getSerializable("localExcluir");
            LocalDAO localDAO = new LocalDAO(getBaseContext());
            localDAO.excluir(local);
            finish();
        }else{
            Toast.makeText(CadastroLocal.this, "Item não criado, ímpossivel Excluir.", Toast.LENGTH_LONG).show();
        }
    }
}