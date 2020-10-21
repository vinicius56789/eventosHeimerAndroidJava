package com.example.heimer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.EventLog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.heimer.Modelo.Evento;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_NOVO_EVENTO = 1;
    private final int RESULT_CODE_NOVO_EVENTO = 10;
    private final int REQUEST_CODE_EDITAR_EVENTO = 2;
    private final int RESULT_CODE_EDITAR_EVENTO = 20;
    private final int RESULT_CODE_EXCLUIR_EVENTO = 30;
    private ListView lvEventos;
    private ArrayAdapter<Evento> adapterEvento;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Próximos Eventos");
        lvEventos = findViewById(R.id.lvEvento);
        ArrayList<Evento> eventos = new ArrayList<Evento>();
        adapterEvento = new ArrayAdapter<Evento>(MainActivity.this, android.R.layout.simple_list_item_1, eventos);
        lvEventos.setAdapter(adapterEvento);
        definirOnclickListenerListView();
    }

    private void definirOnclickListenerListView(){
        lvEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evento eventoClicado = adapterEvento.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastroEvento.class);
                intent.putExtra("eventoEdicao", eventoClicado);
                intent.putExtra("eventoExcluir", eventoClicado);
                startActivityForResult(intent, REQUEST_CODE_EDITAR_EVENTO);
            }
        });
    }

    public void onClickCadastrarEvento(View v){
        Intent intent = new Intent(MainActivity.this, CadastroEvento.class);
        startActivityForResult(intent, REQUEST_CODE_NOVO_EVENTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_NOVO_EVENTO && resultCode == RESULT_CODE_NOVO_EVENTO){
            Evento evento = (Evento) data.getExtras().getSerializable("novoEvento");
            evento.setId(++id);
            this.adapterEvento.add(evento);
        }else if(requestCode == REQUEST_CODE_EDITAR_EVENTO && resultCode == RESULT_CODE_EDITAR_EVENTO){
            Evento eventoEditado = (Evento) data.getExtras().getSerializable("eventoEditado");
            Toast.makeText(MainActivity.this, "Evento editado", Toast.LENGTH_LONG).show();
            for(int i = 0; i < adapterEvento.getCount(); i++){
                Evento evento = adapterEvento.getItem(i);
                if(evento.getId() == eventoEditado.getId()){
                    adapterEvento.remove(evento);
                    adapterEvento.insert(eventoEditado, i);
                    break;
                }
            }
        }else if(requestCode == REQUEST_CODE_EDITAR_EVENTO && resultCode == RESULT_CODE_EXCLUIR_EVENTO){
            Evento eventoExcluido = (Evento) data.getExtras().getSerializable("eventoExcluido");
            Toast.makeText(MainActivity.this, "Evento Excluído da lista", Toast.LENGTH_LONG).show();
            for(int i = 0; i < adapterEvento.getCount(); i++){
                Evento evento = adapterEvento.getItem(i);
                if(evento.getId() == eventoExcluido.getId()){
                    adapterEvento.remove(evento);
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}