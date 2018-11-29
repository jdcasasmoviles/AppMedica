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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuPrincipalDoctor extends Activity implements OnClickListener {
    //VARIABLES
    BaseDatos baseDatosMedica = new BaseDatos(2);
    TableLayout tablaBD;
    Button buttonInsertarBD;
    ArrayList<Button> buttonEdit = new ArrayList<Button>();
    ArrayList<Button> buttonDelete = new ArrayList<Button>();
    JSONArray arrayBD;
    String user="";
    String id_doc="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_doctor);
        //PROCESO PARA OBTENER EL NOMBRE DE USUARIO
        Bundle extras = getIntent().getExtras();
        //Obtenemos datos enviados en el intent.
        if (extras != null) user = extras.getString("user");//usuario
        else user = "error";
        TextView txt_usr = (TextView) findViewById(R.id.textViewNombreDoctor);
        txt_usr.setText(user + "\n\n");//cambiamos texto al nombre del usuario logueado

        //CONSTRUYENDO LA TABLA
        tablaBD = (TableLayout) findViewById(R.id.tablaBDHistoria);
        buttonInsertarBD = (Button) findViewById(R.id.buttonInsertarTablaHistoria);
        buttonInsertarBD.setOnClickListener(this);
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
        TextView viewHeaderAccion = new TextView(this);
        //dar color a las letras
        viewHeaderId_hist.setTextColor(Color.BLACK);
        viewHeaderId_pac.setTextColor(Color.BLACK);
        viewHeaderId_doc.setTextColor(Color.BLACK);
        viewHeaderFech_hist.setTextColor(Color.BLACK);
        viewHeaderSint_hist.setTextColor(Color.BLACK);
        viewHeaderDesc_hist.setTextColor(Color.BLACK);
        viewHeaderAccion.setTextColor(Color.BLACK);
        //setea valores de textViews
        viewHeaderId_hist.setText("id_hist");
        viewHeaderId_pac.setText("id_pac");
        viewHeaderId_doc.setText("id_doc");
        viewHeaderFech_hist.setText("fecha");
        viewHeaderSint_hist.setText("sintomas");
        viewHeaderDesc_hist.setText("descripcion");
        viewHeaderAccion.setText("Accion");
        //para la posicion de los textViews
        viewHeaderId_hist.setPadding(8, 1, 8, 1);
        viewHeaderId_pac.setPadding(8, 1, 8, 1);
        viewHeaderId_doc.setPadding(8, 1, 8, 1);
        viewHeaderFech_hist.setPadding(8, 1, 8, 1);
        viewHeaderSint_hist.setPadding(8, 1, 8, 1);
        viewHeaderDesc_hist.setPadding(8, 1, 8, 1);
        viewHeaderAccion.setPadding(8, 1, 8, 1);
        //adiciona a la tabla
        tabla.addView(viewHeaderId_hist);
        tabla.addView(viewHeaderId_pac);
        tabla.addView(viewHeaderId_doc);
        tabla.addView(viewHeaderFech_hist);
        tabla.addView(viewHeaderSint_hist);
        tabla.addView(viewHeaderDesc_hist);
        tabla.addView(viewHeaderAccion);
        tablaBD.addView(tabla, new TableLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT));

        try {
            arrayBD = new JSONArray(baseDatosMedica.buscaHistoriarBDnombre_doc(user));
            for (int i = 0; i < arrayBD.length(); i++) {
                JSONObject jsonChildNode = arrayBD.getJSONObject(i);
                String id_hist = jsonChildNode.optString("id_hist");
                String id_pac = jsonChildNode.optString("id_pac");
                id_doc = jsonChildNode.optString("id_doc");
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
                viewid_hist.setPadding(8, 1, 8, 1);
                tabla.addView(viewid_hist);

                TextView viewid_pac = new TextView(this);
                viewid_pac.setText(id_pac);
                viewid_pac.setPadding(8, 1, 8, 1);
                tabla.addView(viewid_pac);

                TextView viewid_doc = new TextView(this);
                viewid_doc.setText(id_doc);
                viewid_doc.setPadding(8, 1, 8, 1);
                tabla.addView(viewid_doc);

                TextView viewfech_hist = new TextView(this);
                viewfech_hist.setText(fech_hist);
                viewfech_hist.setPadding(8, 1, 8, 1);
                tabla.addView(viewfech_hist);

                TextView viewsint_hist = new TextView(this);
                viewsint_hist.setText(sint_hist);
                viewsint_hist.setPadding(8, 1, 8, 1);
                tabla.addView(viewsint_hist);

                TextView viewdesc_hist = new TextView(this);
                viewdesc_hist.setText(desc_hist);
                viewdesc_hist.setPadding(8, 1, 8, 1);
                tabla.addView(viewdesc_hist);

                //dar color a las letras
                viewid_hist.setTextColor(Color.BLACK);
                viewid_pac.setTextColor(Color.BLACK);
                viewid_doc.setTextColor(Color.BLACK);
                viewfech_hist.setTextColor(Color.BLACK);
                viewsint_hist.setTextColor(Color.BLACK);
                viewdesc_hist.setTextColor(Color.BLACK);

                buttonEdit.add(i, new Button(this));
                buttonEdit.get(i).setId(Integer.parseInt(id_hist));
                buttonEdit.get(i).setTag("Edit");
                buttonEdit.get(i).setText("Edit");
                buttonEdit.get(i).setOnClickListener(this);
                tabla.addView(buttonEdit.get(i));

                buttonDelete.add(i, new Button(this));
                buttonDelete.get(i).setId(Integer.parseInt(id_hist));
                buttonDelete.get(i).setTag("Delete");
                buttonDelete.get(i).setText("Delete");
                buttonDelete.get(i).setOnClickListener(this);
                tabla.addView(buttonDelete.get(i));

                tablaBD.addView(tabla, new TableLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.MATCH_PARENT));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        findViewById(R.id.buttonVolverHistoria).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MenuPrincipalDoctor.this, Login.class));
            }
        });
        findViewById(R.id.buttonBuscarHistoria).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(MenuPrincipalDoctor.this, BuscarHistoria.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });
        findViewById(R.id.buttonMisPacientes).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(MenuPrincipalDoctor.this, MisPacientes.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });
        findViewById(R.id.button8).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(MenuPrincipalDoctor.this, AgregarPaciente.class);
                i.putExtra("user",user);
                startActivity(i);
            }
        });
        findViewById(R.id.buttonBuscarPaciente).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(MenuPrincipalDoctor.this, BuscarPaciente.class);
                i.putExtra("user",user);
                startActivity(i);
            }
        });

    }

    public void onClick(View view) {

        if (view.getId() == R.id.buttonInsertarTablaHistoria) {
            insertarBD();

        } else {
            for (int i = 0; i < buttonEdit.size(); i++) {
                if (view.getId() == buttonEdit.get(i).getId() && view.getTag().toString().trim().equals("Edit")) {
                    int id = buttonEdit.get(i).getId();
                    getDataByID(id);
                }
                else if (view.getId() == buttonDelete.get(i).getId() && view.getTag().toString().trim().equals("Delete")) {
                    int id = buttonDelete.get(i).getId();
                    borrarBD(id);
                }
            }
        }
    }

    //BORRANDO
    public void borrarBD(int id) {
        baseDatosMedica.borrarBDHistoria(id);
		/* restart acrtivity */
        finish();
        startActivity(getIntent());
    }
    //ACTUALIZANDO
    public void getDataByID(int id) {
        String id_pac="",
                fech_histEdit = null,
                sint_histEdit = null,
                desc_histEdit = null;
        JSONArray arrayPersonal;
        try {
            arrayPersonal = new JSONArray(baseDatosMedica.getBdByIdHistoria(id));
            for (int i = 0; i < arrayPersonal.length(); i++) {
                JSONObject jsonChildNode = arrayPersonal.getJSONObject(i);
                 id_pac = jsonChildNode.optString("id_pac");
               // id_docEdit = jsonChildNode.optString("id_doc");
                fech_histEdit = jsonChildNode.optString("fech_hist");
                sint_histEdit= jsonChildNode.optString("sint_hist");
                desc_histEdit = jsonChildNode.optString("desc_hist");
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

       /* final EditText editid_pac = new EditText(this);
        editid_pac.setText(id_pacEdit);
        layoutInput.addView(editid_pac);

        /*final EditText editid_doc  = new EditText(this);
        editid_doc.setText(id_docEdit);
        layoutInput.addView(editid_doc);*/

        final EditText editfech_hist = new EditText(this);
        editfech_hist.setText(fech_histEdit);
        layoutInput.addView(editfech_hist);

        final EditText editsint_hist = new EditText(this);
        editsint_hist.setText(sint_histEdit);
        layoutInput.addView(editsint_hist);

        final EditText editdesc_hist  = new EditText(this);
        editdesc_hist.setText(desc_histEdit);
        layoutInput.addView(editdesc_hist);
        AlertDialog.Builder builderEditBiodata = new AlertDialog.Builder(this);
        builderEditBiodata.setIcon(R.drawable.batagrams);
        builderEditBiodata.setTitle("Actualizar BD");
        builderEditBiodata.setView(layoutInput);
        final String finalId_pac = id_pac;
        builderEditBiodata.setPositiveButton("ACTUALIZAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String laporan = baseDatosMedica.actualizarTablaHistoria(viewId.getText().toString(),
                        finalId_pac,
                        id_doc,
                        editfech_hist.getText().toString(),
                        editsint_hist.getText().toString(),
                        editdesc_hist.getText().toString()
                );
                Toast.makeText(MenuPrincipalDoctor.this, laporan, Toast.LENGTH_SHORT).show();
				/* restart acrtivity */
                finish();
                startActivity(getIntent());
            }

        });

        builderEditBiodata.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builderEditBiodata.show();

    }

    //INSERTANDO
    public void insertarBD() {
        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        final EditText editid_pac = new EditText(this);
        editid_pac.setHint("id_pac");
        layoutInput.addView(editid_pac);

        /*final EditText editid_doc = new EditText(this);
        editid_doc.setHint("id_doc");
        layoutInput.addView(editid_doc);*/

        final EditText editfech_hist = new EditText(this);
        editfech_hist.setHint("fecha_hist(dd/mm/yy)");
        layoutInput.addView(editfech_hist);

        final EditText editsint_hist = new EditText(this);
        editsint_hist.setHint("sint_hist");
        layoutInput.addView(editsint_hist);

        final EditText editdesc_hist = new EditText(this);
        editdesc_hist.setHint("desc_hist");
        layoutInput.addView(editdesc_hist);

        //obteniendo id_doctor
        JSONArray arrayPersonalid_doc;
        try {
            arrayPersonalid_doc = new JSONArray(baseDatosMedica.obtenBDid_doc(user));
            for (int i = 0; i < arrayPersonalid_doc.length(); i++) {
                JSONObject jsonChildNode = arrayPersonalid_doc.getJSONObject(i);
                id_doc = jsonChildNode.optString("id_doc");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builderInsertBiodata = new AlertDialog.Builder(this);
        builderInsertBiodata.setIcon(R.drawable.batagrams);
        builderInsertBiodata.setTitle("Insertar datos en la BD");
        builderInsertBiodata.setView(layoutInput);
        builderInsertBiodata.setPositiveButton("iNSERTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String laporan = baseDatosMedica.insertarTablaHistoria(editid_pac.getText().toString(),
                        id_doc,
                        editfech_hist.getText().toString(),
                        editsint_hist.getText().toString(),
                        editdesc_hist.getText().toString()
                );
                Toast.makeText(MenuPrincipalDoctor.this, laporan, Toast.LENGTH_SHORT).show();
				/* restart acrtivity */
                finish();
                startActivity(getIntent());
            }

        });

        builderInsertBiodata.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builderInsertBiodata.show();
    }

}
