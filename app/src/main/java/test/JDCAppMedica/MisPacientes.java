package test.JDCAppMedica;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MisPacientes extends Activity {
    //VARIABLES
    BaseDatos baseDatosMedica = new BaseDatos(4);
    TableLayout tablaBD;
    Button buttonInsertarBD;
    ArrayList<Button> buttonEdit = new ArrayList<Button>();
    ArrayList<Button> buttonDelete = new ArrayList<Button>();
    JSONArray arrayBD;
    String user="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_pacientes);
        //PROCESO PARA OBTENER EL NOMBRE DE USUARIO
        Bundle extras = getIntent().getExtras();
        //Obtenemos datos enviados en el intent.
        if (extras != null) user = extras.getString("user");//usuario
        else user = "error";
        TextView txt_usr = (TextView) findViewById(R.id.textView14);
        txt_usr.setText(user + "\n\n");//cambiamos texto al nombre del usuario logueado


        //cosas que aparecen en el layout
        tablaBD = (TableLayout) findViewById(R.id.TablaMisPacientes);
        //CREANDO TABLA PARA MOSTRAR
        TableRow tabla = new TableRow(this);
        tabla.setBackgroundColor(Color.CYAN);
        //textview VARIABLES
        TextView viewHeaderId = new TextView(this);
        TextView viewHeaderNombre = new TextView(this);
        TextView viewHeaderfech_pac = new TextView(this);;
        //dar color a las letras
        viewHeaderId.setTextColor(Color.BLACK);
        viewHeaderNombre.setTextColor(Color.BLACK);
        viewHeaderfech_pac.setTextColor(Color.BLACK);
        //setea valores de textViews
        viewHeaderId.setText("Id");
        viewHeaderNombre.setText("Nombre");
        viewHeaderfech_pac.setText("fecha_nac");
        //para la posicion de los textViews
        viewHeaderId.setPadding(3, 1, 3, 1);
        viewHeaderNombre.setPadding(3, 1, 3, 1);
        viewHeaderfech_pac.setPadding(3, 1, 3, 1);
        //adiciona a la tabla
        tabla.addView(viewHeaderId);
        tabla.addView(viewHeaderNombre);
        tabla.addView(viewHeaderfech_pac);
        tablaBD.addView(tabla, new TableLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT));

        try {
            arrayBD = new JSONArray(baseDatosMedica.mostrarBDMisPacientes(user));
            for (int i = 0; i < arrayBD.length(); i++) {
                JSONObject jsonChildNode = arrayBD.getJSONObject(i);
                String id = jsonChildNode.optString("id_pac");
                String nombre = jsonChildNode.optString("nombre_pac");
                String fech_pac = jsonChildNode.optString("fech_pac");
                tabla = new TableRow(this);

                if (i % 2 == 0) {
                    tabla.setBackgroundColor(Color.LTGRAY);
                }
                else{
                    tabla.setBackgroundColor(Color.WHITE);
                }
                TextView viewId = new TextView(this);
                viewId.setText(id);
                viewId.setPadding(3, 1, 3, 1);
                tabla.addView(viewId);

                TextView viewNombre = new TextView(this);
                viewNombre.setText(nombre);
                viewNombre.setPadding(3, 1, 3, 1);
                tabla.addView(viewNombre);

                TextView viewfech_pac = new TextView(this);
                viewfech_pac.setText(fech_pac);
                viewfech_pac.setPadding(3, 1, 3, 1);
                tabla.addView(viewfech_pac);
                //dar color a las letras
                viewId.setTextColor(Color.BLACK);
                viewNombre.setTextColor(Color.BLACK);
                viewfech_pac.setTextColor(Color.BLACK);

                tablaBD.addView(tabla, new TableLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.MATCH_PARENT));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(MisPacientes.this, MenuPrincipalDoctor.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });
    }


}
