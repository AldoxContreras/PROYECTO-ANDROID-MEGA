package com.example.project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class ConsultaCita extends AppCompatActivity {
    ListView listaCita;
    ConexionMysql conexion;
    String citas;
    int idcli;
    Bundle bundle;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultacita);
        bundle = getIntent().getExtras();
        idcli=bundle.getInt("idcli");
        listaCita = findViewById(R.id.listaCita);
        conexion=new ConexionMysql();
        Consultar con = new Consultar();
        con.execute("");
        /*listaCita.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                citas=(listaCita.getItemAtPosition(position).toString());
                Intent ventanaconcita=new Intent(ConsultaCita.this,Citac.class);
                ventanaconcita.putExtra("cita", citas);
                ventanaconcita.putExtra("idcli",idcli);
                startActivity(ventanaconcita);
                finish();
            }
        });*/
}
    public void llenarcita(ArrayList datos){
        ArrayAdapter adaptador = new ArrayAdapter(getApplicationContext(),R.layout.row,datos);
        listaCita.setAdapter(adaptador);
    }

    public class Consultar extends AsyncTask<String,String,String> {

        String mensaje="";
        boolean exito = false;
        ArrayList datos = new ArrayList();


        //Se ejecuta despues del doinbackground
        @Override
        protected void onPostExecute(String s){

            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            if(exito){

                llenarcita(datos);

            }else{
                datos.clear();
                llenarcita(datos);
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

                String query="select * from cita";

                try {
                    PreparedStatement ps=conectar.prepareStatement(query);
                    ResultSet rs=ps.executeQuery();

                    if(rs.next()){
                        exito=true;

                        do {

                            datos.add(rs.getString("Nombre"));
                            mensaje = "Citas disponibles";
                        }while (rs.next());

                    }
                    else{
                        mensaje="No hay citas disponibles";
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
