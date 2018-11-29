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

public class BuscarHistoria extends Activity {
    //VARIABLES
    BaseDatos baseDatosMedica = new BaseDatos(2);
    TableLayout tablaBD;
    JSONArray arrayBD;
    String user="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_historia);
        //PROCESO PARA OBTENER EL NOMBRE DE USUARIO
        Bundle extras = getIntent().getExtras();
        //Obtenemos datos enviados en el intent.
        if (extras != null) user = extras.getString("user");//usuario
        else user = "error";
        findViewById(R.id.buttonVolver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(BuscarHistoria.this, MenuPrincipalDoctor.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });
    }

    public void botonBuscarHistoria(View view){
        EditText  editText_id_pac= (EditText) findViewById(R.id.editText_id_pac);
        Toast mensaje = Toast.makeText(getApplicationContext(), "Buscando......", Toast.LENGTH_LONG);
        mensaje.show();
        //CONSTRUYENDO LA TABLA
        tablaBD = (TableLayout) findViewById(R.id.tablaBDBuscarHistoria);
        tablaBD.removeAllViews();
        //CREANDO TABLA PARA MOSTRAR
        TableRow tabla = new TableRow(this);
        tabla.setBackgroundColor(Color.CYAN);
        //textview VARIABLES
        TextView viewHeaderId_hist = new TextView(this);
        TextView viewHeaderId_pac = new TextView(this);
        TextView viewHeaderId_doc = new TextView(this);
        TextView viewHeaderFech_hist = new TextView(this);
        TextView viewHeaderSint_hist = new TextView(this);
        TextView viewHeaderDesc_hist = new TextView(this);
        //dar color a las letras
        viewHeaderId_hist.setTextColor(Color.BLACK);
        viewHeaderId_pac.setTextColor(Color.BLACK);
        viewHeaderId_doc.setTextColor(Color.BLACK);
        viewHeaderFech_hist.setTextColor(Color.BLACK);
        viewHeaderSint_hist.setTextColor(Color.BLACK);
        viewHeaderDesc_hist.setTextColor(Color.BLACK);
        //setea valores de textViews
        viewHeaderId_hist.setText("id_hist");
        viewHeaderId_pac.setText("id_pac");
        viewHeaderId_doc.setText("id_doc");
        viewHeaderFech_hist.setText("fecha");
        viewHeaderSint_hist.setText("sintomas");
        viewHeaderDesc_hist.setText("descripcion");
        //para la posicion de los textViews
        viewHeaderId_hist.setPadding(6, 1, 6, 1);
        viewHeaderId_pac.setPadding(6, 1, 6, 1);
        viewHeaderId_doc.setPadding(6, 1, 6, 1);
        viewHeaderFech_hist.setPadding(6, 1, 6, 1);
        viewHeaderSint_hist.setPadding(6, 1, 6, 1);
        viewHeaderDesc_hist.setPadding(6, 1, 6, 1);
        //adiciona a la tabla
        tabla.addView(viewHeaderId_hist);
        tabla.addView(viewHeaderId_pac);
        tabla.addView(viewHeaderId_doc);
        tabla.addView(viewHeaderFech_hist);
        tabla.addView(viewHeaderSint_hist);
        tabla.addView(viewHeaderDesc_hist);
        tablaBD.addView(tabla, new TableLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT));

        try {
            arrayBD = new JSONArray(baseDatosMedica.buscaHistoriarBDid(editText_id_pac.getText().toString()));
            for (int i = 0; i < arrayBD.length(); i++) {
                JSONObject jsonChildNode = arrayBD.getJSONObject(i);

                String id_hist = jsonChildNode.optString("id_hist");
                String id_pac = jsonChildNode.optString("id_pac");
                String id_doc = jsonChildNode.optString("id_doc");
                String fech_hist = jsonChildNode.optString("fech_hist");
                String sint_hist = jsonChildNode.optString("sint_hist");
                String desc_hist = jsonChildNode.optString("desc_hist");
                tabla = new TableRow(this);
                if (i % 2 == 0) {
                    tabla.setBackgroundColor(Color.LTGRAY);
                } else {
                    tabla.setBackgroundColor(Color.WHITE);
                }
                TextView viewid_hist = new TextView(this);
                viewid_hist.setText(id_hist);
                viewid_hist.setPadding(6, 1, 6, 1);
                tabla.addView(viewid_hist);

                TextView viewid_pac = new TextView(this);
                viewid_pac.setText(id_pac);
                viewid_pac.setPadding(6, 1, 6, 1);
                tabla.addView(viewid_pac);

                TextView viewid_doc = new TextView(this);
                viewid_doc.setText(id_doc);
                viewid_doc.setPadding(6, 1, 6, 1);
                tabla.addView(viewid_doc);

                TextView viewfech_hist = new TextView(this);
                viewfech_hist.setText(fech_hist);
                viewfech_hist.setPadding(6, 1, 6, 1);
                tabla.addView(viewfech_hist);

                TextView viewsint_hist = new TextView(this);
                viewsint_hist.setText(sint_hist);
                viewsint_hist.setPadding(6, 1, 6, 1);
                tabla.addView(viewsint_hist);

                TextView viewdesc_hist = new TextView(this);
                viewdesc_hist.setText(desc_hist);
                viewdesc_hist.setPadding(6, 1, 6, 1);
                tabla.addView(viewdesc_hist);

                //dar color a las letras
                viewid_hist.setTextColor(Color.BLACK);
                viewid_pac.setTextColor(Color.BLACK);
                viewid_doc.setTextColor(Color.BLACK);
                viewfech_hist.setTextColor(Color.BLACK);
                viewsint_hist.setTextColor(Color.BLACK);
                viewdesc_hist.setTextColor(Color.BLACK);

                tablaBD.addView(tabla, new TableLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.MATCH_PARENT));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
