package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminCliente extends AppCompatActivity {
    ConexionMysql conexion;
    EditText etIDcli,etNombrecli,etApellidoCli,etApellidoCli2,etDireccioncli,etTelefonocli;
    ImageView btnguardarCli,btnconsultarCli,btnmodificarCli,btneliminarCli, btnVolverAtras;
    String nombre,apellido,apellido2,direccion,telefono;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admincliente);

        btnVolverAtras=findViewById(R.id.btnVolverAtras);
        etIDcli=findViewById(R.id.etIDcli);
        etNombrecli=findViewById(R.id.etNombrecli);
        etApellidoCli=findViewById(R.id.etApellidoCli);
        etApellidoCli2=findViewById(R.id.etApellidoCli2);
        etDireccioncli=findViewById(R.id.etDireccioncli);
        etTelefonocli=findViewById(R.id.etTelefonocli);

        btnguardarCli=findViewById(R.id.btnguardarCli);
        btnconsultarCli=findViewById(R.id.btnconsultarCli);
        btneliminarCli=findViewById(R.id.btneliminarCli);
        btnmodificarCli=findViewById(R.id.btnmodificarCli);

        conexion=new ConexionMysql();

        btnVolverAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanamenuprincipal = new Intent(AdminCliente.this, Menua.class);
                startActivity(ventanamenuprincipal);
            }
        });

        btnguardarCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=Integer.parseInt(etIDcli.getText().toString());
                nombre=etNombrecli.getText().toString();
                apellido=etApellidoCli.getText().toString();
                apellido2=etApellidoCli2.getText().toString();
                direccion=etDireccioncli.getText().toString();
                telefono=etTelefonocli.getText().toString();

                OperaABM opera=new OperaABM();
                opera.execute("insert into cliente(IDcli,Nombre,Apellido1,Apellido2,Direccion,Telefono) values(?,?,?,?,?,?)","G");
            }
        });//cierre del onclick del btnguardar

        btnconsultarCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });//cierre del onclick btnconsultar

        btnmodificarCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombre=etNombrecli.getText().toString();
                apellido=etApellidoCli.getText().toString();
                apellido2=etApellidoCli2.getText().toString();
                direccion=etDireccioncli.getText().toString();
                telefono=etTelefonocli.getText().toString();

                OperaABM opera=new OperaABM();
                opera.execute("update cliente set Nombre=?,Apellido1=?,Apellido2=?,Direccion=?,Telefono=? where IDcli=?","M");
            }
        });//cierre del onclick btnmodificar

        btneliminarCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=Integer.parseInt(etIDcli.getText().toString());

                OperaABM opera=new OperaABM();
                opera.execute("delete from cliente where IDcli=?","E");
            }
        });//cierre del onclick btneliminar

        btnconsultarCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etIDcli.getText().toString().isEmpty())
                {
                    id=-1;
                }else{
                    id=Integer.parseInt(etIDcli.getText().toString());
                }

                nombre=etNombrecli.getText().toString();
                Consultar consulta=new Consultar();
                consulta.execute("");
            }
        });

    }//cierre del oncreate

    public class OperaABM extends AsyncTask<String,String,String>{
        String mensaje="";
        boolean exito=false;

        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected void onPostExecute(String msj){
            Toast.makeText(getApplicationContext(),msj,Toast.LENGTH_LONG).show();

            if(exito){
                etIDcli.setText("");
                etNombrecli.setText("");
                etApellidoCli.setText("");
                etApellidoCli2.setText("");
                etDireccioncli.setText("");
                etTelefonocli.setText("");
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            Connection con=conexion.Conectar();

            if(con!=null){
                try {
                    PreparedStatement ps =con.prepareStatement(strings[0]);
                    if(strings[1].equals("G")){
                        ps.setInt(1,id);
                        ps.setString(1,nombre);
                        ps.setString(2,apellido);
                        ps.setString(3,apellido2);
                        ps.setString(4,direccion);
                        ps.setString(5,telefono);
                    }
                    if(strings[1].equals("M")){
                        ps.setInt(1,id);
                        ps.setString(2,nombre);
                        ps.setString(3,apellido);
                        ps.setString(4,apellido2);
                        ps.setString(5,direccion);
                        ps.setString(6,telefono);

                    }
                    if(strings[1].equals("E")){
                        ps.setInt(1,id);
                    }

                    if(ps.executeUpdate()>0){
                        exito=true;
                        if(strings[1].equals("G")){
                            mensaje="Registro guardado";
                        }
                        if(strings[1].equals("M")){
                            mensaje="Registro modificado";
                        }
                        if(strings[1].equals("E")){
                            mensaje="Registro eliminado";
                        }

                    }else{
                        if(strings[1].equals("G")){
                            mensaje="Registro no guardado";
                        }
                        if(strings[1].equals("M")){
                            mensaje="Registro no modificado";
                        }
                        if(strings[1].equals("E")){
                            mensaje="Registro no eliminado";
                        }

                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                mensaje="Error al conectar con la base de datos";
            }

            return mensaje;
        }
    }//cierre de la subclase operaABM

    public class Consultar extends AsyncTask<String,String,String>{
        String mensaje="";
        boolean exito=false;

        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected void onPostExecute(String msj){
            if(exito){
                etIDcli.setText(id+"");
                etNombrecli.setText(nombre);
                etApellidoCli.setText(apellido);
                etApellidoCli2.setText(apellido2);
                etDireccioncli.setText(direccion);
                etTelefonocli.setText(telefono);
            }
            else{
                Toast.makeText(getApplicationContext(),msj,Toast.LENGTH_LONG).show();
                etNombrecli.setText("");
                etApellidoCli.setText("");
                etApellidoCli2.setText("");
                etDireccioncli.setText("");
                etTelefonocli.setText("");
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            Connection con =conexion.Conectar();
            if(con!=null){
                String query="select * from cliente where Nombre =? or IDcli=?";

                try {
                    PreparedStatement ps=con.prepareStatement(query);
                    ps.setString(1,nombre);
                    ps.setInt(2,id);

                    ResultSet rs=ps.executeQuery();

                    if(rs.next()){

                        exito=true;

                        id=rs.getInt("IDcli");
                        nombre=rs.getString("Nombre");
                        apellido=rs.getString("Apellido1");
                        apellido2=rs.getString("Apellido2");
                        direccion=rs.getString("Direccion");
                        telefono=rs.getString("Telefono");
                    }
                    else
                    {
                        mensaje="Empleado no encontrado";
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else{
                mensaje="Error al conectar con la base de datos";
            }


            return mensaje;
        }
    }//cierre de la subclase consulta

}//cierre de la clase principal
