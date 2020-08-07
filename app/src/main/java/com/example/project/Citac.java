package com.example.project;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Citac extends AppCompatActivity {
    Button  btnregistrar;
    ConexionMysql conexion;
    EditText etfecha, ethora, etmotivo;
    Spinner spcitamatricula;
    Bundle bundle;
    String hora;
    String fecha;
    String motivo;
    String matricula;
    //String IDcli;
    int IDcli, IDem, IDcita;



    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrocita);

        conexion = new ConexionMysql();

       // spcitamatricula=findViewById(R.id.spcitamatricula);
        //consultarListaMatricula();
       // ArrayAdapter<CharSequence> adaptador =new ArrayAdapter(this,android.R.layout.simple_spinner_item,listaMatricula);
        // spcitamatricula.setAdapter(adaptador);


       // spcitamatricula.findViewById(R.id.spcitamatricula);
        etfecha=findViewById(R.id.etfecha);
        ethora=findViewById(R.id.ethora);
        etmotivo=findViewById(R.id.etmotivo);
        btnregistrar=findViewById(R.id.btnregistrar);
        conexion = new ConexionMysql();
        bundle = getIntent().getExtras();
        IDcli = bundle.getInt("IDcli");
        IDem = bundle.getInt("IDem");
        matricula = bundle.getString("matricula");
    } }

       /* Consultar con  = new Consultar();
        con.execute("");

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                IDcita=IDcita;
                hora = ethora.getText().toString();
                fecha = etfecha.getText().toString();
                motivo = etmotivo.getText().toString();
                IDcli=IDcli;
                matricula=matricula;
                IDem=IDem;
                spcitamatricula=spcitamatricula.get

                operaABM operaabm = new operaABM();
                operaabm.execute("insert into cita(IDcita, hora, fecha, motivo, IDcli, matricula, IDem) values (?,?,?,?,?,1)", "A");
            }
        });


    }
    public void llenarspinner(ArrayList datosspinner){
        ArrayAdapter adaptadorspinner = new ArrayAdapter(getApplicationContext(),R.layout.row,datosspinner);
        spcitamatricula.setAdapter(adaptadorspinner);
    }
    public class Consultar extends AsyncTask<String,String,String>{
        String mensaje="";
        boolean exito=false;
        ArrayList datosspinner = new ArrayList();
        @Override
        protected void onPostExecute(String s){

            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            if(exito){

                llenarspinner(datosspinner);

            }else{
                datosspinner.clear();
                llenarspinner(datosspinner);
            }
        }
    @Override
    protected void onPreExecute(){


    }
        @Override
        protected String doInBackground(String... strings) {

            Connection conectar=conexion.Conectar();
            if(conectar!=null){
                String query="Select matricula from vehiculo where  IDcli = '"+matricula+"'";
          try{
              PreparedStatement ps=conectar.prepareStatement(query);
              ResultSet rs=ps.executeQuery();
              if (rs.next()){

              }
          }



            }

    public class operaABM extends AsyncTask<String,String,String>{
        String mensaje = "";
        boolean exito= false;

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
                        ps.setInt(1, IDcli);
                        ps.setString(2, hora);
                        ps.setString(3, fecha);
                        ps.setString(4,motivo);
                        ps.setInt(5, IDcli);
                        ps.setString(6,matricula);
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
*/