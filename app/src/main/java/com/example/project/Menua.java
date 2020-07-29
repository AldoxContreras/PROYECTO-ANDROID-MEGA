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

public class Menua extends AppCompatActivity {
    ImageView btnAdminCita,btnAdminClientes;
    TextView tvBienvenido;
    ConexionMysql conexion;
    Bundle bundle;
    int idem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menua);
        btnAdminCita = findViewById(R.id.btnAdminCita);
        btnAdminClientes = findViewById(R.id.btnAdminClientes);


        tvBienvenido = findViewById(R.id.tvBienvenidoc);
        conexion = new ConexionMysql();

        btnAdminCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanaAdC = new Intent(Menua.this, ConsultaCita.class);
                startActivity(ventanaAdC);
            }
        });


        try {
            Consulta();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        btnAdminClientes = findViewById(R.id.btnAdminClientes);
        btnAdminClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanaAdCli = new Intent(Menua.this, AdminCliente.class);
                startActivity(ventanaAdCli);
            }
        });
    }
    public void Consulta() throws SQLException {
        Connection c = conexion.Conectar();
        if(c !=null){
            PreparedStatement bienvenida = null;
            bienvenida = c.prepareStatement("select * from empleado where IDem = '" + idem + "'"  );
            ResultSet rs = bienvenida.executeQuery();
            if (rs.next()){
                tvBienvenido.setText("Bienvenido: " + rs.getString("Nombre"));
            }
        }
        c.close();
    }

}


