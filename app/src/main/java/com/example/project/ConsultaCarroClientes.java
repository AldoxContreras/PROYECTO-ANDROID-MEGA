package com.example.project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConsultaCarroClientes extends AppCompatActivity {

    ListView lvcarroscliente;
    ConexionMysql conexion;
    String vehiculo;

    int IDcli;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultacarrosclientes);

        bundle = getIntent().getExtras();
        IDcli = bundle.getInt("IDcli");

        lvcarroscliente = findViewById(R.id.lvcarroscliente);

        conexion = new ConexionMysql();

        Consultar con = new Consultar();
        con.execute("");

        lvcarroscliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vehiculo =(lvcarroscliente.getItemAtPosition(position).toString());

                Intent ventana = new Intent(ConsultaCarroClientes.this, Menu.class);
                ventana.putExtra("matricula", vehiculo );
                ventana.putExtra("IDcli", IDcli);
                startActivity(ventana);
                finish();
            }
        });
    }

    public void llenarlistavehiculo(ArrayList datos){
        ArrayAdapter adaptador = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,datos);
        lvcarroscliente.setAdapter(adaptador);

    }

    public class Consultar extends AsyncTask<String,String,String> {

        String mensaje="";
        boolean exito = false;
        ArrayList datos = new ArrayList();
      //  ArrayList datos2 = new ArrayList();

        //Se ejecuta despues del doinbackground
        @Override
        protected void onPostExecute(String s){

            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            if(exito){

                llenarlistavehiculo(datos);


            }else{
                datos.clear();
                llenarlistavehiculo(datos);


            }
        }

        //Se ejecuta antes del doinbackground
        @Override
        protected void onPreExecute(){


        }

        @Override
        protected String doInBackground(String... strings) {

        Connection conectar=conexion.Conectar();

            if (conectar != null){


                String query="select * from vehiculo where IDcli = ?";
                try {

                    PreparedStatement ps=conectar.prepareStatement(query);
                    ps.setInt(1,IDcli);
                    ResultSet rs=ps.executeQuery();

                    if(rs.next()){
                        exito=true;
                        IDcli=rs.getInt("IDcli");

                        do {

                            datos.add(rs.getString("matricula")+" - "+rs.getString("marca")+" - "+rs.getString("modelo"));
                            mensaje = "Vehiculos registrados";
                        }while (rs.next());


                    }
                    else{
                        mensaje="No hay vehiculos registrados";
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                    mensaje=e.getMessage();
                }

                try {
                    conectar.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    mensaje=e.getMessage();
                }

            }else{
                mensaje="Error al conectar con la base de datos";
            }

            return mensaje;
        }
    }
}
