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
    ImageView btnAdminCitaa,btnAdminClientes;
    TextView tvBienvenidoem;
    ConexionMysql conexion;
    Bundle bundle;
    String Nombreem;
    int IDem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menua);
        btnAdminCitaa = findViewById(R.id.btnAdminCitaa);
        btnAdminClientes = findViewById(R.id.btnAdminClientes);

        bundle=getIntent().getExtras();
        IDem = bundle.getInt("IDem");
        Nombreem = bundle.getString("Nombreem");
        tvBienvenidoem=findViewById(R.id.tvBienvenidoem);

        conexion = new ConexionMysql();

        try {
            Consulta();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        btnAdminCitaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanaAdC = new Intent(Menua.this, ConsultaCita.class);
                startActivity(ventanaAdC);
            }
        });

        btnAdminClientes = findViewById(R.id.btnAdminClientes);
        btnAdminClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanaAdCli = new Intent(Menua.this, Registrocliente.class);
                startActivity(ventanaAdCli);
            }
        });
    }

    public void Consulta() throws SQLException {
        Connection c = conexion.Conectar();
        if(c !=null){
            PreparedStatement bienvenida = null;
            bienvenida = c.prepareStatement("Select Nombreem from empleado where IDem in (Select IDem from empleado_usuario where usuario= '" + Nombreem + "'"  );
            ResultSet rs = bienvenida.executeQuery();
            if (rs.next()){
                tvBienvenidoem.setText("Bienvenido " + rs.getString("Nombreem"));
            }
        }
        c.close();
    }

}


