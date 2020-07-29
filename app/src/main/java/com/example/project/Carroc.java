package com.example.project;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Carroc extends AppCompatActivity {
TextView etModelo,etMarca, etMatricula;
Button btnRegistrar;
Bundle bundle;
String modelo,marca,matricula;
ConexionMysql conexion;
int IDcli;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carroc);
        getSupportActionBar().hide();//eliminar barra

        etModelo=findViewById(R.id.etModelo);
        etMarca=findViewById(R.id.etMarca);
        etMatricula=findViewById(R.id.etMatricula);
        btnRegistrar=findViewById(R.id.btnRegistrar);
        conexion = new ConexionMysql();
        bundle = getIntent().getExtras();
        IDcli = bundle.getInt("IDcli");

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            matricula=etMatricula.getText().toString();
            marca=etMarca.getText().toString();
            modelo=etModelo.getText().toString();

            IDcli=IDcli;

                operaABM operaabm = new operaABM();
                operaabm.execute("insert into vehiculo(matricula, marca, modelo,IDcli) values (?,?,?,?)", "A");
               //  operaabm.execute("insert into vehiculo(matricula, marca, modelo, IDcli) values (?,?,?,?)", "A");
            }
        });
        conexion = new ConexionMysql();
}
    public class operaABM extends AsyncTask<String,String,String>{
        String mensaje = "";

        @Override
        protected void onPostExecute(String r) {

            Toast.makeText(getApplicationContext(), r, Toast.LENGTH_LONG).show();

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {

            Connection c = conexion.Conectar();

            if(c != null) {

                try {
                    PreparedStatement ps = c.prepareStatement(strings[0]);

                    if (strings[1].equals("A")) {
                        ps.setString(1, matricula);
                        ps.setString(2, marca);
                        ps.setString(3, modelo);
                        ps.setInt(4, IDcli);
                    }

                        if (ps.executeUpdate() > 0) {
                        if (strings[1].equals("A")) {
                            mensaje = "Registro guardado";
                        }
                    } else {
                        if (strings[1].equals("A")) {
                            mensaje = "Error al guardar el registro";
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                mensaje = "Error al conectar";
            }
            return mensaje;
        }
    }
}
