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

public class MisDoctores extends Activity {
    //VARIABLES
    BaseDatos baseDatosMedica = new BaseDatos(1);
    TableLayout tablaBD;
    Button buttonInsertarBD;
    ArrayList<Button> buttonEdit = new ArrayList<Button>();
    ArrayList<Button> buttonDelete = new ArrayList<Button>();
    JSONArray arrayBD;
    String user="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_doctores);

        //PROCESO PARA OBTENER EL NOMBRE DE USUARIO
        Bundle extras = getIntent().getExtras();
        //Obtenemos datos enviados en el intent.
        if (extras != null) user = extras.getString("user");//usuario
        else user = "error";
        TextView txt_usr = (TextView) findViewById(R.id.textView12);
        txt_usr.setText(user + "\n\n");//cambiamos texto al nombre del usuario logueado


        //cosas que aparecen en el layout
        tablaBD = (TableLayout) findViewById(R.id.TablaMisDoctores);
        //CREANDO TABLA PARA MOSTRAR
        TableRow tabla = new TableRow(this);
        tabla.setBackgroundColor(Color.CYAN);
        //textview VARIABLES
        TextView viewHeaderId = new TextView(this);
        TextView viewHeaderNombre = new TextView(this);
        TextView viewHeaderEspecialidad = new TextView(this);
        TextView viewHeaderPassword = new TextView(this);
        //dar color a las letras
        viewHeaderId.setTextColor(Color.BLACK);
        viewHeaderNombre.setTextColor(Color.BLACK);
        viewHeaderEspecialidad.setTextColor(Color.BLACK);
        viewHeaderPassword.setTextColor(Color.BLACK);
        //setea valores de textViews
        viewHeaderId.setText("Id");
        viewHeaderNombre.setText("Nombre");
        viewHeaderEspecialidad.setText("Especialidad");
        //para la posicion de los textViews
        viewHeaderId.setPadding(3, 1, 3, 1);
        viewHeaderNombre.setPadding(3, 1, 3, 1);
        viewHeaderEspecialidad.setPadding(3, 1, 3, 1);
        //adiciona a la tabla
        tabla.addView(viewHeaderId);
        tabla.addView(viewHeaderNombre);
        tabla.addView(viewHeaderEspecialidad);
        tablaBD.addView(tabla, new TableLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT));

        try {
            arrayBD = new JSONArray(baseDatosMedica.mostrarBDMisDoctores(user));
            for (int i = 0; i < arrayBD.length(); i++) {
                JSONObject jsonChildNode = arrayBD.getJSONObject(i);
                String nombre = jsonChildNode.optString("nombre_doc");
                String especialidad = jsonChildNode.optString("especialidad_doc");
                String id = jsonChildNode.optString("id_doc");
                tabla = new TableRow(this);

                if (i % 2 == 0) {
                    tabla.setBackgroundColor(Color.LTGRAY);
                }
                else{
                    tabla.setBackgroundColor(Color.WHITE);
                }
                TextView viewId = new TextView(this);
                viewId.setText(id);
                viewId.setPadding(5, 1, 5, 1);
                tabla.addView(viewId);

                TextView viewNombre = new TextView(this);
                viewNombre.setText(nombre);
                viewNombre.setPadding(5, 1, 5, 1);
                tabla.addView(viewNombre);

                TextView viewEspecialidad = new TextView(this);
                viewEspecialidad.setText(especialidad);
                viewEspecialidad.setPadding(5, 1, 5, 1);
                tabla.addView(viewEspecialidad);
                //dar color a las letras
                viewId.setTextColor(Color.BLACK);
                viewNombre.setTextColor(Color.BLACK);
                viewEspecialidad.setTextColor(Color.BLACK);

                tablaBD.addView(tabla, new TableLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.MATCH_PARENT));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(MisDoctores.this, MenuPrincipalPaciente.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });
    }

}
