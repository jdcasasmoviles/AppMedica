package test.JDCAppMedica;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuPrincipalPaciente extends Activity implements OnClickListener {
    //VARIABLES
    BaseDatos baseDatosMedica = new BaseDatos(2);
    TableLayout tablaBD;
    Button buttonInsertarBD;
    ArrayList<Button> buttonEdit = new ArrayList<Button>();
    ArrayList<Button> buttonDelete = new ArrayList<Button>();
    JSONArray arrayBD;
    String user="";
    String id_pac="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_paciente);
        //PROCESO PARA OBTENER EL NOMBRE DE USUARIO
        Bundle extras = getIntent().getExtras();
        //Obtenemos datos enviados en el intent.
        if (extras != null) user = extras.getString("user");//usuario
        else user = "error";
        TextView txt_usr = (TextView) findViewById(R.id.textViewNombrePaciente);
        txt_usr.setText(user + "\n\n");//cambiamos texto al nombre del usuario logueado

        //CONSTRUYENDO LA TABLA
        System.out.println("ESTOYYY........Menu paciente");
        tablaBD = (TableLayout) findViewById(R.id.tablaPacienteHistorialMedico);
        //CREANDO TABLA PARA MOSTRAR
        TableRow tabla = new TableRow(this);
        tabla.setBackgroundColor(Color.CYAN);
        //textview VARIABLES
        TextView viewHeaderid_hist = new TextView(this);
        TextView viewHeaderfech_hist = new TextView(this);
        TextView viewHeadernombre_doc = new TextView(this);
        TextView viewHeaderespecialidad_doc = new TextView(this);
        TextView viewHeaderAccion = new TextView(this);
        //dar color a las letras
        viewHeaderid_hist.setTextColor(Color.BLACK);
        viewHeaderfech_hist.setTextColor(Color.BLACK);
        viewHeadernombre_doc.setTextColor(Color.BLACK);
        viewHeaderespecialidad_doc.setTextColor(Color.BLACK);
        viewHeaderAccion.setTextColor(Color.BLACK);
        //setea valores de textViews
        viewHeaderid_hist.setText("id_hist");
        viewHeaderfech_hist.setText("fech_hist");
        viewHeadernombre_doc.setText("nombre_doc");
        viewHeaderespecialidad_doc.setText("especialidad_doc");
        viewHeaderAccion.setText("Accion");
        //para la posicion de los textViews
        viewHeaderid_hist.setPadding(5, 1, 5, 1);
        viewHeaderfech_hist.setPadding(5, 1, 5, 1);
        viewHeadernombre_doc.setPadding(5, 1, 5, 1);
        viewHeaderespecialidad_doc.setPadding(5, 1, 5, 1);

        viewHeaderAccion.setPadding(5, 1, 5, 1);
        //adiciona a la tabla
        tabla.addView(viewHeaderid_hist);
        tabla.addView(viewHeaderfech_hist);
        tabla.addView(viewHeadernombre_doc);
        tabla.addView(viewHeaderespecialidad_doc);
        tabla.addView(viewHeaderAccion);
        tablaBD.addView(tabla, new TableLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT));

        try {
            arrayBD = new JSONArray(baseDatosMedica.buscaHistoriarBDnombre_pac(user));
            for (int i = 0; i < arrayBD.length(); i++) {
                JSONObject jsonChildNode = arrayBD.getJSONObject(i);
                String id_hist = jsonChildNode.optString("id_hist");
                String fech_hist = jsonChildNode.optString("fech_hist");
                String nombre_doc = jsonChildNode.optString("nombre_doc");
                String especialidad_doc = jsonChildNode.optString("especialidad_doc");
                tabla = new TableRow(this);
                if (i % 2 == 0) {
                    tabla.setBackgroundColor(Color.LTGRAY);
                } else {
                    tabla.setBackgroundColor(Color.WHITE);
                }

                TextView viewid_hist = new TextView(this);
                viewid_hist.setText(id_hist);
                viewid_hist.setPadding(5, 1, 5, 1);
                tabla.addView(viewid_hist);

                TextView viewfech_hist = new TextView(this);
                viewfech_hist.setText(fech_hist);
                viewfech_hist.setPadding(5, 1, 5, 1);
                tabla.addView(viewfech_hist);

                TextView viewnombre_doc = new TextView(this);
                viewnombre_doc.setText(nombre_doc);
                viewnombre_doc.setPadding(5, 1, 5, 1);
                tabla.addView(viewnombre_doc);

                TextView viewespecialidad_doc = new TextView(this);
                viewespecialidad_doc.setText(especialidad_doc);
                viewespecialidad_doc.setPadding(5, 1, 5, 1);
                tabla.addView(viewespecialidad_doc);

                //dar color a las letras
                viewid_hist.setTextColor(Color.BLACK);
                viewfech_hist.setTextColor(Color.BLACK);
                viewnombre_doc.setTextColor(Color.BLACK);
                viewespecialidad_doc.setTextColor(Color.BLACK);

                buttonEdit.add(i, new Button(this));
                buttonEdit.get(i).setId(Integer.parseInt(id_hist));
                buttonEdit.get(i).setTag("Edit");
                buttonEdit.get(i).setText("SELECCIONAR");
                buttonEdit.get(i).setOnClickListener(this);
                tabla.addView(buttonEdit.get(i));
                tablaBD.addView(tabla, new TableLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.MATCH_PARENT));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        findViewById(R.id.buttonVollverPaciente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(MenuPrincipalPaciente.this, Login.class);
                startActivity(i);
            }
        });
        findViewById(R.id.buttonBuscarHistoria).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(MenuPrincipalPaciente.this, MisDoctores.class);
                i.putExtra("user",user);
                startActivity(i);
            }
        });
    }

    public void onClick(View view) {
        if (view.getId() == R.id.buttonInsertarTablaHistoria) {
            //insertarBD();
        }
        else {
            for (int i = 0; i < buttonEdit.size(); i++) {
                if (view.getId() == buttonEdit.get(i).getId() && view.getTag().toString().trim().equals("Edit")) {
                    int id = buttonEdit.get(i).getId();
                    getDataByID(id);
                }
            }
        }
    }

    //SELECCIONAR
    public void getDataByID(int id) {
        String id_histEdit=null,
                fech_histEdit = null,
                sint_histEdit = null,
                desc_histEdit = null;
        JSONArray arrayPersonal;
        try {
            arrayPersonal = new JSONArray(baseDatosMedica.getBdByIdHistoria(id));
            for (int i = 0; i < arrayPersonal.length(); i++) {
                JSONObject jsonChildNode = arrayPersonal.getJSONObject(i);
                id_histEdit = "id_hist : "+jsonChildNode.optString("id_hist");
                fech_histEdit="fecha_hist : "+jsonChildNode.optString("fech_hist");
                sint_histEdit="sintomas : "+jsonChildNode.optString("sint_hist");
                desc_histEdit ="descripcion : "+jsonChildNode.optString("desc_hist");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        final TextView viewId = new TextView(this);
        viewId.setText(String.valueOf(id));
        viewId.setTextColor(Color.TRANSPARENT);
        layoutInput.addView(viewId);

        final EditText editid_hist = new EditText(this);
        editid_hist.setText(id_histEdit);
        editid_hist.setEnabled(false);
        layoutInput.addView(editid_hist);

        final EditText editfech_hist = new EditText(this);
        editfech_hist.setText(fech_histEdit);
        editfech_hist.setEnabled(false);
        layoutInput.addView(editfech_hist);

        final EditText editsint_hist = new EditText(this);
        editsint_hist.setText(sint_histEdit);
        editsint_hist.setEnabled(false);
        layoutInput.addView(editsint_hist);

        final EditText editdesc_hist  = new EditText(this);
        editdesc_hist.setText(desc_histEdit);
        editdesc_hist.setEnabled(false);
        layoutInput.addView(editdesc_hist);

        AlertDialog.Builder builderEditBiodata = new AlertDialog.Builder(this);
        builderEditBiodata.setIcon(R.drawable.batagrams);
        builderEditBiodata.setTitle("Info Historial Paciente");
        builderEditBiodata.setView(layoutInput);
        final String finalId_pac = id_pac;

        builderEditBiodata.setNegativeButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builderEditBiodata.show();

    }

}
