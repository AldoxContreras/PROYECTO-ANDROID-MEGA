package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Menuc extends AppCompatActivity {
    ImageView btnCita, btnCarro, btnUbicacionc;
    ConexionMysql conexion;
    TextView tvBienvenidocli;
    Bundle bundle;
    String Nombrecli;
    int IDcli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuc);


        tvBienvenidocli=findViewById(R.id.tvBienvenidocli);
        bundle = getIntent().getExtras();
        IDcli = bundle.getInt("IDcli");
        //Nombrecli = bundle.getString("Nombrecli");

        conexion= new ConexionMysql();




        btnCita=findViewById(R.id.btnCita);
        btnCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanacita = new Intent(Menuc.this, Citac.class);
                ventanacita.putExtra("IDcli", IDcli);
                startActivity(ventanacita);
            }
        });

        btnCarro=findViewById(R.id.btnCarro);
        btnCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanarca = new Intent(Menuc.this, Carroc.class);
                ventanarca.putExtra("IDcli",IDcli);
                startActivity(ventanarca);
            }
        });

        btnUbicacionc=findViewById(R.id.btnUbicacionc);
        btnUbicacionc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanaubi = new Intent(Menuc.this, Ubicacionc.class);
                startActivity(ventanaubi);
            }
        });

        try {
            Consulta();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Consulta() throws SQLException {
        Connection c = conexion.Conectar();
        if(c !=null){
            PreparedStatement bienvenida = null;
            bienvenida = c.prepareStatement("Select * from cliente where IDcli= '"+IDcli+"'");
            ResultSet rs = bienvenida.executeQuery();
            if (rs.next()){
                tvBienvenidocli.setText("Bienvenid@ " + rs.getString("Nombrecli"));
            }
        }
        c.close();
    }
}
