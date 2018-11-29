package test.JDCAppMedica;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuscarPaciente extends Activity {
    //VARIABLES
    BaseDatos baseDatosMedica = new BaseDatos(4);
    TableLayout tablaBD;
    JSONArray arrayBD;
    String user="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_paciente);
        //PROCESO PARA OBTENER EL NOMBRE DE USUARIO
        Bundle extras = getIntent().getExtras();
        //Obtenemos datos enviados en el intent.
        if (extras != null) user = extras.getString("user");//usuario
        else user = "error";
        //VOLVER
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(BuscarPaciente.this, MenuPrincipalDoctor.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });
    }

    public void botonBuscarPaciente(View view){
        EditText editText_nombre_pac= (EditText) findViewById(R.id.editText_nombre_pac);
        Toast mensaje = Toast.makeText(getApplicationContext(), "Buscando......", Toast.LENGTH_LONG);
        mensaje.show();
        //CONSTRUYENDO LA TABLA
        tablaBD = (TableLayout) findViewById(R.id.tablaBDBuscarPaciente);
        tablaBD.removeAllViews();
        //CREANDO TABLA PARA MOSTRAR
        TableRow tabla = new TableRow(this);
        tabla.setBackgroundColor(Color.CYAN);
        //textview VARIABLES
        TextView viewHeaderid_pac = new TextView(this);
        TextView viewHeadernombre_pac = new TextView(this);
        TextView viewHeaderfech_pac = new TextView(this);
        //dar color a las letras
        viewHeaderid_pac.setTextColor(Color.BLACK);
        viewHeadernombre_pac.setTextColor(Color.BLACK);
        viewHeaderfech_pac.setTextColor(Color.BLACK);
        //setea valores de textViews
        viewHeaderid_pac.setText("id_pac");
        viewHeadernombre_pac.setText("nombre_pac");
        viewHeaderfech_pac.setText("fech_pac");
        //para la posicion de los textViews
        viewHeaderid_pac.setPadding(4, 1, 4, 1);
        viewHeadernombre_pac.setPadding(4, 1, 4, 1);
        viewHeaderfech_pac.setPadding(4, 1, 4, 1);
        //adiciona a la tabla
        tabla.addView(viewHeaderid_pac);
        tabla.addView(viewHeadernombre_pac);
        tabla.addView(viewHeaderfech_pac);

        tablaBD.addView(tabla, new TableLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT));

        try {
            arrayBD = new JSONArray(baseDatosMedica.buscarPaciente(editText_nombre_pac.getText().toString()));
            for (int i = 0; i < arrayBD.length(); i++) {
                JSONObject jsonChildNode = arrayBD.getJSONObject(i);
                String id_pac = jsonChildNode.optString("id_pac");
                String nombre_pac = jsonChildNode.optString("nombre_pac");
                String fech_pac = jsonChildNode.optString("fech_pac");
                tabla = new TableRow(this);
                if (i % 2 == 0) {
                    tabla.setBackgroundColor(Color.LTGRAY);
                } else {
                    tabla.setBackgroundColor(Color.WHITE);
                }
                TextView viewid_pac = new TextView(this);
                viewid_pac.setText(id_pac);
                viewid_pac.setPadding(4, 1, 4, 1);
                tabla.addView(viewid_pac);

                TextView viewnombre_pac = new TextView(this);
                viewnombre_pac.setText(nombre_pac);
                viewnombre_pac.setPadding(4, 1, 4, 1);
                tabla.addView(viewnombre_pac);

                TextView viewfech_nac = new TextView(this);
                viewfech_nac.setText(fech_pac);
                viewfech_nac.setPadding(4, 1, 4, 1);
                tabla.addView(viewfech_nac);

                //dar color a las letras
                viewid_pac.setTextColor(Color.BLACK);
                viewnombre_pac.setTextColor(Color.BLACK);
                viewfech_nac.setTextColor(Color.BLACK);

                tablaBD.addView(tabla, new TableLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.MATCH_PARENT));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
