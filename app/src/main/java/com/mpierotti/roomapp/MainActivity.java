package com.mpierotti.roomapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Inicializar variables

    EditText editText;
    Button btAdd, btReset;
    RecyclerView recyclerView;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asignar variables

        editText = findViewById(R.id.edit_text);
        btAdd = findViewById(R.id.bt_add);
        btReset = findViewById(R.id.bt_reset);
        recyclerView = findViewById(R.id.recycler_view);

        //Inicializar database
        database = RoomDB.getInstance(this);
        //Store database value in data list
        dataList = database.mainDAO().getAll();

        //inicializar linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        // Set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //inicializar adapter
        adapter = new MainAdapter(MainActivity.this, dataList);
        //set adapter
        recyclerView.setAdapter(adapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get string del edit text
                String sText = editText.getText().toString().trim();
                if(!sText.equals("")){
                    MainData data = new MainData();
                    data.setText(sText);
                    database.mainDAO().insert(data);
                    editText.setText("");
                    dataList.clear();
                    dataList.addAll(database.mainDAO().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.mainDAO().reset(dataList);
                dataList.clear();
                dataList.addAll(database.mainDAO().getAll());
                adapter.notifyDataSetChanged();
            }
        });
    }
}