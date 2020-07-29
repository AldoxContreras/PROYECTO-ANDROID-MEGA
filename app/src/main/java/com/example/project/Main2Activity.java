package com.example.project;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    String Usua;
    String Nombre,Apellido1,Apellido2,Correo,Contrasena;
   EditText etNombre,Usuario,etPrimerApellido,etSegundoApellido,etCorreo,etContrasena;
   Button btnRegistrarse;

    ConexionMysql conexion;
    Bundle bundle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Usuario = findViewById(R.id.Usuario);
        etNombre =  findViewById(R.id.etNombre);
        etPrimerApellido = findViewById(R.id.etPrimerApellido);
        etSegundoApellido =  findViewById(R.id.etSegundoApellido);
        etCorreo = findViewById(R.id.etCorreo);
        btnRegistrarse =  findViewById(R.id.btnRegistrarse);
        etContrasena=findViewById(R.id.etContrasena);
        btnRegistrarse.setOnClickListener(this);
        conexion = new ConexionMysql();
        bundle=getIntent().getExtras();
}
    @Override
    public void onClick(View v) {
        if(v== btnRegistrarse){
           Usua=Usuario.getText().toString();
            Nombre = etNombre.getText().toString();
            Apellido1 = etPrimerApellido.getText().toString();
            Apellido2 = etSegundoApellido.getText().toString();
            Correo = etCorreo.getText().toString();
            Contrasena = etContrasena.getText().toString();
            operaABM opeaABM = new operaABM();
            opeaABM.execute("insert into cliente (IDcli, Nombre, Apellido1, Apellido2, Dirección,Telefono) values (?,?,?,?,?,?) ", "A");
        }
    } //cierre del on click
    public class operaABM extends AsyncTask<String, String, String>{
        boolean exito = false;
        String mensaje = "";
        @Override
    protected void onPostExecute (String r){
            Toast.makeText(getApplicationContext(), r, Toast.LENGTH_SHORT).show();
        if (exito){
            Intent ventana=new Intent(Main2Activity.this,MainActivity.class);
            startActivity(ventana);
        }
        }
        @Override
        protected void onPreExecute(){
        }
        @Override
        protected String doInBackground(String... strings){
            Connection c=conexion.Conectar();
            if(c!= null){
                try {
                    PreparedStatement ps=c.prepareStatement(strings[0]);
                    if(strings[1].equals("A")){
                        ps.setString(1,Usua);
                        ps.setString(2,Nombre);
                        ps.setString(3,Apellido1);
                        ps.setString(4,Apellido2);
                        ps.setString(5,Correo);
                        ps.setString(6,Contrasena);
                        if (ps.executeUpdate()>0){
                            if (strings[1].equals("A")){
                                mensaje="Registro exitoso";
                                exito=true;
                            }
                        }else{
                            if (strings[1].equals("A")){
                                mensaje="Registro no exitoso";
                            }
                        }
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
                try {
                    c.close();
                }catch (SQLException e){
                    e.printStackTrace(); }
                }else{
                    mensaje="Error al conectar";
                }
                return mensaje;
                }
            }
    }
