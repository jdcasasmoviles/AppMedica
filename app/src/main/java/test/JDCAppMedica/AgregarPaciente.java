package test.JDCAppMedica;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

public class AgregarPaciente extends Activity implements View.OnClickListener{
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
        setContentView(R.layout.activity_agregar_paciente);
        //PROCESO PARA OBTENER EL NOMBRE DE USUARIO
        Bundle extras = getIntent().getExtras();
        //Obtenemos datos enviados en el intent.
        if (extras != null) user = extras.getString("user");//usuario
        else user = "error";
        TextView txt_usr = (TextView) findViewById(R.id.textView17);
        txt_usr.setText(user + "\n\n");//cambiamos texto al nombre del usuario logueado
        //cosas que aparecen en el layout
        buttonInsertarBD = (Button) findViewById(R.id.button6);
        buttonInsertarBD.setOnClickListener(this);
        //VOLVER
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(AgregarPaciente.this, MenuPrincipalDoctor.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });
    }

    public void onClick(View view) {
        //INSERTAR DATOS
        if (view.getId() == R.id.button6) {
            insertarBD();
        }
    }

    //INSERTANDO
    public void insertarBD() {
        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        final EditText editNombre = new EditText(this);
        editNombre.setHint("Nombre");
        layoutInput.addView(editNombre);

        final EditText editEspecialidad = new EditText(this);
        editEspecialidad.setHint("Fecha de nacimiento(dd/mm/yy)");
        layoutInput.addView(editEspecialidad);
        //Password
        final EditText editPassword = new EditText(this);
        editPassword.setHint("Password");
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
                TextView datosInsertados = (TextView) findViewById(R.id.textView18);
                datosInsertados.setText("Datos de nuevo paciente \nNombre :"+nombre+"\nFecha de nacimiento : "+fech_pac+"\nPassword : "+password);
                String laporan = baseDatosMedica.insertarTablaPaciente(nombre, fech_pac,password);
                Toast.makeText(AgregarPaciente.this, laporan, Toast.LENGTH_SHORT).show();
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
