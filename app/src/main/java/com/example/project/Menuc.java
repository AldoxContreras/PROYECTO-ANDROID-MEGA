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
    ImageView btnCita, btnCarro, btnUbicacion;
    ConexionMysql conexion;
    TextView tvBienvenidoc;
    Bundle bundle;
    int IDcli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuc);
        getSupportActionBar().hide();//eliminar barra

        bundle = getIntent().getExtras();
//        IDcli = bundle.getInt("IDcli");
        conexion= new ConexionMysql();

        btnCita=findViewById(R.id.btnAgendarCita);
        btnCarro=findViewById(R.id.btnRegistrarV);
        tvBienvenidoc=findViewById(R.id.tvBienvenidoc);
        btnUbicacion=findViewById(R.id.btnUbicacion);

        btnCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanacita = new Intent(Menuc.this, Citac.class);
                ventanacita.putExtra("IDcli", IDcli);
                startActivity(ventanacita);
            }
        });

        btnCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanarca = new Intent(Menuc.this, Carroc.class);
                ventanarca.putExtra("IDcli",IDcli);
                startActivity(ventanarca);
            }
        });
        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanaubi = new Intent(Menuc.this, Ubicacionc.class);
                Menuc.this.startActivity(ventanaubi);
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
            bienvenida = c.prepareStatement("select * from cliente where IDcli = '" + IDcli + "'"  );
            ResultSet rs = bienvenida.executeQuery();
            if (rs.next()){
                tvBienvenidoc.setText("Bienvenido: " + rs.getString("Nombre"));
            }
        }
        c.close();
    }
}

