package com.example.project;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;


public class Citac extends AppCompatActivity implements View.OnClickListener {
    Button  btnConfirmar;
    ConexionMysql conexion;
    EditText etFecha, etHora, etMotivo;

    Bundle bundle;
    String  hora, motivo;
    String fecha;


    private int dia, mes, ano, hora2,minutos;
    int IDcli, IDem, IDcita, matricula;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita);
        getSupportActionBar().hide();//eliminar barra

        conexion = new ConexionMysql();

        etFecha=findViewById(R.id.etFecha);
        etHora=findViewById(R.id.etHora);
        etMotivo=findViewById(R.id.etMotivo);
        btnConfirmar=findViewById(R.id.btnConfirmar);
        conexion = new ConexionMysql();
        bundle = getIntent().getExtras();
        IDcli = bundle.getInt("IDcli");
        IDem = bundle.getInt("IDem");
        IDcita = bundle.getInt("IDcita");
        matricula=bundle.getInt("matricula");

    btnConfirmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hora = etHora.getText().toString();
                fecha = etFecha.getText().toString();
                motivo = etMotivo.getText().toString();
                IDcita=IDcita;
                IDcli=IDcli;
                matricula=matricula;
                IDem=IDem;
                operaABM operaabm = new operaABM();
                operaabm.execute("insert into cita(IDcita, hora, fecha, motivo, IDcli, matricula, IDem) values (?,?,?,?,?,?)", "A");
            }
        });


    }

    @Override
    public void onClick(View v) {

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
                        ps.setInt(1, IDcita);
                        ps.setString(2, hora);
                        ps.setString(3, fecha);
                        ps.setString(4,motivo);
                        ps.setInt(5, IDcli);
                        ps.setInt(6,matricula);
                        ps.setInt(7,IDem);

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
