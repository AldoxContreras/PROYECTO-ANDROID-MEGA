package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etUsuario,etContraseña;
    Button bt1,bt2;
    Bundle bundle;
    ConexionMysql mysqlcon;
    ProgressBar pbInicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUsuario=findViewById(R.id.etUsuario);
        etContraseña=findViewById(R.id.etContraseña);
        bt1=findViewById(R.id.btnIniciar);
        bt2 = findViewById (R.id.btnRegistrar);
        pbInicio=findViewById(R.id.pbInicio);
        mysqlcon = new ConexionMysql();
        getSupportActionBar().hide();
        bt2.setOnClickListener(this);
        bt1.setOnClickListener(this);

    }
    public void onClick (View v){
        if(v==bt1){
            InicioSesion inisesion=new InicioSesion();
            inisesion.execute("");
        }
        if(v==bt2){
            Intent intentReg = new Intent(MainActivity.this,Main2Activity.class);
            MainActivity.this.startActivity(intentReg);
        }
    }//cierre del metodo onclick
    public class InicioSesion extends AsyncTask<String,String,String> {
        String mensaje="",nombreEmp;
        boolean exito=false;
        String Usuario=etUsuario.getText().toString();

        String Contraseña=etContraseña.getText().toString();
        String tipo;
        @Override
        protected void onPostExecute(String s){
            pbInicio.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            if(exito){
                if(tipo.equals("E")){
                    Intent ventanaa = new Intent(MainActivity.this, Menua.class);
                    startActivity(ventanaa);
                }else if (tipo.equals("C")){
                    Intent ventanac = new Intent(MainActivity.this, Menuc.class);
                    startActivity(ventanac);
                }
            }
        }//Cierre onPost
        @Override
        protected void onPreExecute(){
            pbInicio.setVisibility(View.VISIBLE);
        }//Cierre onPre
        @Override
        protected String doInBackground(String... strings) {
            Connection conectar=mysqlcon.Conectar();
            if(conectar !=null){
                String query="Select * from usuario where usuario=? and contrasenia=?";
                try {
                    PreparedStatement ps=conectar.prepareStatement(query);
                    ps.setString(1,Usuario);
                    ps.setString(2,Contraseña);
                    ResultSet rs=ps.executeQuery();
                    if(rs.next()){
                        exito = true;
                        tipo=rs.getString("tipo");
                    }else{
                        mensaje="Usuario o contraseña incorrecto";
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    mensaje=e.getMessage();
                }
                try {
                    conectar.close();
                }catch (SQLException e){
                    e.printStackTrace();
                    mensaje=e.getMessage();
                }

            }
            else {
                mensaje="Error al conectar con la base de datos";
            }
            return mensaje;
        }//Cierre doInBack

    } //Cierre subclase iniciar sesion
}//Cierre clase principal



