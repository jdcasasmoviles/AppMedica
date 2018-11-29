package test.JDCAppMedica;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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

public class MenuPrincipalAdministradorPaciente extends Activity implements View.OnClickListener {
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
        setContentView(R.layout.activity_menu_principal_administrador_paciente);
        //PROCESO PARA OBTENER EL NOMBRE DE USUARIO
        Bundle extras = getIntent().getExtras();
        //Obtenemos datos enviados en el intent.
        if (extras != null) user = extras.getString("user");//usuario
        else user = "error";
        TextView txt_usr = (TextView) findViewById(R.id.textView10);
        txt_usr.setText(user + "\n\n");//cambiamos texto al nombre del usuario logueado


        //cosas que aparecen en el layout
        tablaBD = (TableLayout) findViewById(R.id.tablaBDPacienteAdministrador);
        buttonInsertarBD = (Button) findViewById(R.id.buttonAgregarPacienteAdministrador);
        buttonInsertarBD.setOnClickListener(this);
        //CREANDO TABLA PARA MOSTRAR
        TableRow tabla = new TableRow(this);
        tabla.setBackgroundColor(Color.CYAN);
        //textview VARIABLES
        TextView viewHeaderId = new TextView(this);
        TextView viewHeaderNombre = new TextView(this);
        TextView viewHeaderfech_pac = new TextView(this);
        TextView viewHeaderPassword_pac = new TextView(this);
        TextView viewHeaderAccion = new TextView(this);
        //dar color a las letras
        viewHeaderId.setTextColor(Color.BLACK);
        viewHeaderNombre.setTextColor(Color.BLACK);
        viewHeaderfech_pac.setTextColor(Color.BLACK);
        viewHeaderPassword_pac.setTextColor(Color.BLACK);
        viewHeaderAccion.setTextColor(Color.BLACK);
        //setea valores de textViews
        viewHeaderId.setText("Id");
        viewHeaderNombre.setText("Nombre");
        viewHeaderfech_pac.setText("fecha_nac");
        viewHeaderPassword_pac.setText("Password");
        viewHeaderAccion.setText("Accion");
        //para la posicion de los textViews
        viewHeaderId.setPadding(6, 1, 6, 1);
        viewHeaderNombre.setPadding(6, 1, 6, 1);
        viewHeaderfech_pac.setPadding(6, 1, 6, 1);
        viewHeaderPassword_pac.setPadding(6, 1, 6, 1);
        viewHeaderAccion.setPadding(6, 1, 6, 1);
        //adiciona a la tabla
        tabla.addView(viewHeaderId);
        tabla.addView(viewHeaderNombre);
        tabla.addView(viewHeaderfech_pac);
        tabla.addView(viewHeaderPassword_pac);
        tabla.addView(viewHeaderAccion);
        tablaBD.addView(tabla, new TableLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT));

        try {
            arrayBD = new JSONArray(baseDatosMedica.mostrarBD());
            for (int i = 0; i < arrayBD.length(); i++) {
                JSONObject jsonChildNode = arrayBD.getJSONObject(i);
                String id = jsonChildNode.optString("id_pac");
                String nombre = jsonChildNode.optString("nombre_pac");
                String fech_pac = jsonChildNode.optString("fech_pac");
                String password_pac = jsonChildNode.optString("password_pac");
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

                TextView viewfech_pac = new TextView(this);
                viewfech_pac.setText(fech_pac);
                viewfech_pac.setPadding(6, 1, 6, 1);
                tabla.addView(viewfech_pac);

                TextView viewpassword_pac = new TextView(this);
                viewpassword_pac.setText(password_pac);
                viewpassword_pac.setPadding(6, 1, 6, 1);
                tabla.addView(viewpassword_pac);
                //dar color a las letras
                viewId.setTextColor(Color.BLACK);
                viewNombre.setTextColor(Color.BLACK);
                viewfech_pac.setTextColor(Color.BLACK);
                viewpassword_pac.setTextColor(Color.BLACK);

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
        findViewById(R.id.button4Volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(MenuPrincipalAdministradorPaciente.this, HiScreen.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });
    }

    public void onClick(View view) {

        if (view.getId() == R.id.buttonAgregarPacienteAdministrador) {
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
        baseDatosMedica.borrarBDPaciente(id);
		/* restart acrtivity */
        finish();
        startActivity(getIntent());
    }
    //ACTUALIZANDO
    public void getDataByID(int id) {
        String nombre_pac = null,
                fech_pac = null;
        JSONArray arrayPersonal;
        try {
            arrayPersonal = new JSONArray(baseDatosMedica.getBdByIdPaciente(id));
            for (int i = 0; i < arrayPersonal.length(); i++) {
                JSONObject jsonChildNode = arrayPersonal.getJSONObject(i);
                nombre_pac = jsonChildNode.optString("nombre_pac");
                fech_pac = jsonChildNode.optString("fech_pac");
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
        editNombre.setText(nombre_pac);
        layoutInput.addView(editNombre);

        final EditText editfech_pac= new EditText(this);
        editfech_pac.setText(fech_pac);
        layoutInput.addView(editfech_pac);

        AlertDialog.Builder builderEditBiodata = new AlertDialog.Builder(this);
        builderEditBiodata.setIcon(R.drawable.batagrams);
        builderEditBiodata.setTitle("Actualizar BD");
        builderEditBiodata.setView(layoutInput);
        builderEditBiodata.setPositiveButton("ACTUALIZAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String laporan = baseDatosMedica.actualizarBDPaciente(viewId.getText().toString(), editNombre.getText().toString(),
                        editfech_pac.getText().toString());
                Toast.makeText(MenuPrincipalAdministradorPaciente.this, laporan, Toast.LENGTH_SHORT).show();
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
        editEspecialidad.setHint("fecha_nacimiento(dd/mm/yy)");
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
                String fech_pac = editEspecialidad.getText().toString();
                String password = editPassword.getText().toString();
                String laporan = baseDatosMedica.insertarTablaPaciente(nombre, fech_pac,password);
                Toast.makeText(MenuPrincipalAdministradorPaciente.this, laporan, Toast.LENGTH_SHORT).show();
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
