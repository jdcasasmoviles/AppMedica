package test.JDCAppMedica;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.v4.view.ViewPager.LayoutParams;
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

public class MenuPrincipalAdministradorDoctor extends Activity implements OnClickListener {
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
        setContentView(R.layout.activity_menu__princupal_administrador_doctor);

        //PROCESO PARA OBTENER EL NOMBRE DE USUARIO
        Bundle extras = getIntent().getExtras();
        //Obtenemos datos enviados en el intent.
        if (extras != null) user = extras.getString("user");//usuario
        else user = "error";
        TextView txt_usr = (TextView) findViewById(R.id.textViewnombreAdministrador);
        txt_usr.setText(user + "\n\n");//cambiamos texto al nombre del usuario logueado


        //cosas que aparecen en el layout
        tablaBD = (TableLayout) findViewById(R.id.tablaBD);
        buttonInsertarBD = (Button) findViewById(R.id.buttonInsertarBD);
        buttonInsertarBD.setOnClickListener(this);
        //CREANDO TABLA PARA MOSTRAR
        TableRow tabla = new TableRow(this);
        tabla.setBackgroundColor(Color.CYAN);
        //textview VARIABLES
        TextView viewHeaderId = new TextView(this);
        TextView viewHeaderNombre = new TextView(this);
        TextView viewHeaderEspecialidad = new TextView(this);
        TextView viewHeaderPassword = new TextView(this);
        TextView viewHeaderAccion = new TextView(this);
        //dar color a las letras
        viewHeaderId.setTextColor(Color.BLACK);
        viewHeaderNombre.setTextColor(Color.BLACK);
        viewHeaderEspecialidad.setTextColor(Color.BLACK);
        viewHeaderPassword.setTextColor(Color.BLACK);
        viewHeaderAccion.setTextColor(Color.BLACK);
        //setea valores de textViews
        viewHeaderId.setText("Id");
        viewHeaderNombre.setText("Nombre");
        viewHeaderEspecialidad.setText("Especialidad");
        viewHeaderPassword.setText("Password");
        viewHeaderAccion.setText("Accion");
        //para la posicion de los textViews
        viewHeaderId.setPadding(6, 1, 6, 1);
        viewHeaderNombre.setPadding(6, 1, 6, 1);
        viewHeaderEspecialidad.setPadding(6, 1, 6, 1);
        viewHeaderPassword.setPadding(6,1,6,1);
        viewHeaderAccion.setPadding(6, 1, 6, 1);
        //adiciona a la tabla
        tabla.addView(viewHeaderId);
        tabla.addView(viewHeaderNombre);
        tabla.addView(viewHeaderEspecialidad);
        tabla.addView(viewHeaderPassword);
        tabla.addView(viewHeaderAccion);
        tablaBD.addView(tabla, new TableLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT));

        try {
            arrayBD = new JSONArray(baseDatosMedica.mostrarBD());
            for (int i = 0; i < arrayBD.length(); i++) {
                JSONObject jsonChildNode = arrayBD.getJSONObject(i);
                String nombre = jsonChildNode.optString("nombre_doc");
                String especialidad = jsonChildNode.optString("especialidad_doc");
                String id = jsonChildNode.optString("id_doc");
                String password_doc = jsonChildNode.optString("password_doc");
                tabla = new TableRow(this);

                if (i % 2 == 0) {
                    tabla.setBackgroundColor(Color.LTGRAY);
                }
                else{
                    tabla.setBackgroundColor(Color.WHITE);
                }
                TextView viewId = new TextView(this);
                viewId.setText(id);
                viewId.setPadding(6, 1, 6, 1);
                tabla.addView(viewId);

                TextView viewNombre = new TextView(this);
                viewNombre.setText(nombre);
                viewNombre.setPadding(6, 1, 6, 1);
                tabla.addView(viewNombre);

                TextView viewEspecialidad = new TextView(this);
                viewEspecialidad.setText(especialidad);
                viewEspecialidad.setPadding(6, 1, 6, 1);
                tabla.addView(viewEspecialidad);

                TextView viewpassword_doc = new TextView(this);
                viewpassword_doc.setText(password_doc);
                viewpassword_doc.setPadding(6, 1, 6, 1);
                tabla.addView(viewpassword_doc);
                //dar color a las letras
                viewId.setTextColor(Color.BLACK);
                viewNombre.setTextColor(Color.BLACK);
                viewEspecialidad.setTextColor(Color.BLACK);
                viewpassword_doc.setTextColor(Color.BLACK);

                buttonEdit.add(i, new Button(this));
                buttonEdit.get(i).setId(Integer.parseInt(id));
                buttonEdit.get(i).setTag("Edit");
                buttonEdit.get(i).setText("Edit");
                buttonEdit.get(i).setOnClickListener(this);
                tabla.addView(buttonEdit.get(i));

                buttonDelete.add(i, new Button(this));
                buttonDelete.get(i).setId(Integer.parseInt(id));
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
        findViewById(R.id.buttonVolverDoctor).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(MenuPrincipalAdministradorDoctor.this, HiScreen.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });
    }


    public void onClick(View view) {

        if (view.getId() == R.id.buttonInsertarBD) {
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
        baseDatosMedica.borrarBD(id);
		/* restart acrtivity */
        finish();
        startActivity(getIntent());
    }
    //ACTUALIZANDO
    public void getDataByID(int id) {
        String nombreEdit = null,
                especialidadEdit = null;
        JSONArray arrayPersonal;
        try {
            arrayPersonal = new JSONArray(baseDatosMedica.getBdById(id));
            for (int i = 0; i < arrayPersonal.length(); i++) {
                JSONObject jsonChildNode = arrayPersonal.getJSONObject(i);
                nombreEdit = jsonChildNode.optString("nombre_doc");
                especialidadEdit = jsonChildNode.optString("especialidad_doc");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        // buat id tersembunyi di alertbuilder
        final TextView viewId = new TextView(this);
        viewId.setText(String.valueOf(id));
        viewId.setTextColor(Color.TRANSPARENT);
        layoutInput.addView(viewId);

        final EditText editNombre = new EditText(this);
        editNombre.setText(nombreEdit);
        layoutInput.addView(editNombre);

        final EditText editEspecialidad = new EditText(this);
        editEspecialidad.setText(especialidadEdit);
        layoutInput.addView(editEspecialidad);

        AlertDialog.Builder builderEditBiodata = new AlertDialog.Builder(this);
        builderEditBiodata.setIcon(R.drawable.batagrams);
        builderEditBiodata.setTitle("Actualizar BD");
        builderEditBiodata.setView(layoutInput);
        builderEditBiodata.setPositiveButton("ACTUALIZAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombre = editNombre.getText().toString();
                String especialidad = editEspecialidad.getText().toString();

                String laporan = baseDatosMedica.actualizarBD(viewId.getText().toString(), editNombre.getText().toString(),
                        editEspecialidad.getText().toString());
                Toast.makeText(MenuPrincipalAdministradorDoctor.this, laporan, Toast.LENGTH_SHORT).show();
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

        final EditText editNombre = new EditText(this);
        editNombre.setHint("nombre");
        layoutInput.addView(editNombre);

        final EditText editEspecialidad = new EditText(this);
        editEspecialidad.setHint("especialidad");
        layoutInput.addView(editEspecialidad);
        //Password
        final EditText editPassword = new EditText(this);
        editPassword.setHint("password");
        layoutInput.addView(editPassword);

        AlertDialog.Builder builderInsertBiodata = new AlertDialog.Builder(this);
        builderInsertBiodata.setIcon(R.drawable.batagrams);
        builderInsertBiodata.setTitle("Insertar datos en la BD");
        builderInsertBiodata.setView(layoutInput);
        builderInsertBiodata.setPositiveButton("iNSERTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombre = editNombre.getText().toString();
                String especialidad = editEspecialidad.getText().toString();
                String password = editPassword.getText().toString();
                String laporan = baseDatosMedica.insertarBDDoctor(nombre, especialidad,password);
                Toast.makeText(MenuPrincipalAdministradorDoctor.this, laporan, Toast.LENGTH_SHORT).show();
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
