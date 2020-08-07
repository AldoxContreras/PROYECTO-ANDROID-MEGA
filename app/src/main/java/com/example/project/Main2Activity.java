package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    EditText etUsuario,etContraseña;
    Button bt1,bt2;
    Bundle bundle;
    ConexionMysql mysqlcon;
    ProgressBar pbInicio;
    String Nombreem, Nombrecli;
    int IDem, IDcli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        etUsuario=findViewById(R.id.etUsuario);
        etContraseña=findViewById(R.id.etContraseña);
        bt1=findViewById(R.id.btnIniciar);
        pbInicio=findViewById(R.id.pbInicio);
        mysqlcon = new ConexionMysql();


        pbInicio.setVisibility(View.INVISIBLE);
        bt1.setOnClickListener(this);
        bundle=getIntent().getExtras();

    }
    public void onClick (View v){
        if(v==bt1){
            InicioSesion inisesion=new InicioSesion();
            inisesion.execute("");
        }
    }//cierre del metodo onclick

    public class InicioSesion extends AsyncTask<String,String,String> {
        String mensaje="";
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
                    Intent ventanaa = new Intent(Main2Activity.this, Menua.class);
                    ventanaa.putExtra("IDem", IDem);
                    ventanaa.putExtra("Nombreem", Nombreem);
                    startActivity(ventanaa);
                }else if (tipo.equals("C")){
                    Intent ventanac = new Intent(Main2Activity.this, Menuc.class);
                    ventanac.putExtra("IDcli", IDcli);
                    ventanac.putExtra("Nombrecli",Nombrecli);
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
                        if (tipo.equals("C")){
                            String query2="Select IDcli from cliente where IDcli in (select IDcli from cliente_usuario where usuario=?)";
                            PreparedStatement ps2=conectar.prepareStatement(query2);
                            ps2.setString(1,Usuario);
                            ResultSet rs2=ps2.executeQuery();
                            if(rs2.next()){
                                exito = true;
                                IDcli=rs2.getInt("IDcli");
                            }

                        }
                        if (tipo.equals("E")){
                            String query3="Select IDem from empleado where IDem in (select IDem from empleado_usuario where usuario=?)";
                            PreparedStatement ps3=conectar.prepareStatement(query3);
                            ps3.setString(1,Usuario);
                            ResultSet rs3=ps3.executeQuery();
                            if(rs3.next()){
                                exito = true;
                                IDem=rs3.getInt("IDem");
                            }
                        }
                    }

                    else{
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



