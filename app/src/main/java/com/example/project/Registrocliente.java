package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Registrocliente extends AppCompatActivity {
    ConexionMysql conexion;
    EditText etnombre,etape1,etape2,etdireccion,ettelefono,etusuario,etcontra,etestado;
    Button  btnregistrar;
    String nombre,apellido,apellido2,direccion,telefono,estado, usuario, contraseña;
    Bundle bundle;
    int IDcli;
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrocliente);
        getSupportActionBar().hide();//eliminar barra

        conexion = new ConexionMysql();

        etnombre=findViewById(R.id.etnombre);
        etape1=findViewById(R.id.etape1);
        etape2=findViewById(R.id.etape1);
        etdireccion=findViewById(R.id.etdireccion);
        ettelefono=findViewById(R.id.ettelefono);
        etestado=findViewById(R.id.etestado);


        etusuario=findViewById(R.id.etusuario);
        etcontra=findViewById(R.id.etcontra);

        btnregistrar=findViewById(R.id.btnregistrar);


        bundle = getIntent().getExtras();
       IDcli = bundle.getInt("IDcli");

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IDcli=IDcli;

               nombre=etnombre.getText().toString();
               apellido=etape1.getText().toString();
               apellido2=etape2.getText().toString();
               direccion=etdireccion.getText().toString();
               telefono=ettelefono.getText().toString();
               estado=etestado.getText().toString();


               usuario=etusuario.getText().toString();
               contraseña=etcontra.getText().toString();



                Registrocliente.operaABM operaabm = new Registrocliente.operaABM();
                operaabm.execute("insert into cliente (Nombre, Apellido1, Apellido2,Direccion,Telefono,Estado) values (?,?,?,?,?)", "A");
                operaabm.execute("insert into usuario (usuario,contrasenia, tipo) values (?,?,'C')","U");
                operaabm.execute("insert into cliente_usuario(usuario) values (?)", "CU");

            }
        });

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
                        ps.setString(1, nombre);
                        ps.setString(2, apellido);
                        ps.setString(3, apellido2);
                        ps.setString(4, direccion);
                        ps.setString(5,telefono);
                        ps.setString(6, estado);
                    }if(strings[2].equals("U")){
                        ps.setString(1,usuario);
                        ps.setString(2,contraseña);
                    }
                    if (strings[3].equals("CU")){
                        ps.setInt(1,IDcli);
                        ps.setString(2,usuario);
                    }

                    if (ps.executeUpdate() > 0) {
                        if (strings[1].equals("A")||strings[2].equals("CU")||strings[3].equals("U")) {
                            mensaje = "Registro guardado";
                        }
                    } else {
                        if (strings[1].equals("A")||strings[2].equals("CU")||strings[3].equals("U")) {
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
