package test.JDCAppMedica;
public class BaseDatos extends Conexion {
	//String IP_Server="masterwishmaster.esy.es";//IP DE NUESTRO PC LOCALHOST
    String IP_Server="192.168.1.47";
    String URL ;
    String url = "";
    String response = "";
    public BaseDatos(int numeroTabla){
        if(numeroTabla==1){
            URL = "http://"+IP_Server+"/ServidorMedico/serverTablaDoctor.php";
            System.out.println("Estoy usando tabla doctor");
        }
        else if(numeroTabla==2){
            URL = "http://"+IP_Server+"/ServidorMedico/serverTablaHistoria.php";
            System.out.println("Estoy usando tabla historia");
        }
        else if(numeroTabla==3){
            URL = "http://"+IP_Server+"/ServidorMedico/serverTablaUsuarios.php";
            System.out.println("Estoy usando tabla usuarios");
        }
        else if(numeroTabla==4){
            URL = "http://"+IP_Server+"/ServidorMedico/serverTablaPaciente.php";
            System.out.println("Estoy usando tabla pacientes");
        }
    }

    //******************************MOSTRAR PARA CUALQUIER TABLA*******************************************************************************************
    public String mostrarBD() {
        try {
            url = URL + "?operacion=view";
            System.out.println("URL mostrar BD: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }
//*****************************************************TABLA DOCTOR******************************************************************************************************************
    public String insertarBDDoctor(String nombre_doc, String especialidad_doc,String password_doc) {
        nombre_doc=nombre_doc.replace(" ","%20");
        especialidad_doc=especialidad_doc.replace(" ","%20");
        password_doc=password_doc.replace(" ","%20");
        try {
            url = URL + "?operacion=insert&nombre_doc=" + nombre_doc + "&especialidad_doc=" + especialidad_doc+ "&password_doc=" + password_doc;
            System.out.println("URL insertar BD : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String getBdById(int id_doc) {
        try {
            url = URL + "?operacion=get_bd_by_id&id_doc=" + id_doc;
            System.out.println("URL get_id BD : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String actualizarBD(String id_doc, String nombre_doc, String especialidad_doc) {
        nombre_doc=nombre_doc.replace(" ","%20");
        especialidad_doc=especialidad_doc.replace(" ","%20");
        try {
            url = URL + "?operacion=update&id_doc=" + id_doc + "&nombre_doc=" + nombre_doc + "&especialidad_doc=" + especialidad_doc;
            System.out.println("URL update BD : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String borrarBD(int id_doc) {
        try {
            url = URL + "?operacion=delete&id_doc=" + id_doc;
            System.out.println("URL borrado de la BD : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String mostrarBDMisDoctores(String nombre_pac) {
        nombre_pac=nombre_pac.replace(" ","%20");
        try {
            url = URL + "?operacion=viewMisDoctores"+ "&nombre_pac=" + nombre_pac;;
            System.out.println("URL mostrar mis doctoresBD: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

//********************************************TABLA HISTORIA*************************************************************************************************
    public String actualizarTablaHistoria(String id_hist,String id_pac, String id_doc,String fech_hist,String sint_hist,String desc_hist) {
        sint_hist=sint_hist.replace(" ","%20");
        desc_hist=desc_hist.replace(" ","%20");
        try {
            url = URL + "?operacion=update&id_hist=" + id_hist +
                    "&id_pac=" + id_pac +
                    "&id_doc=" + id_doc +
                    "&fech_hist=" + fech_hist +
                    "&sint_hist=" + sint_hist +
                    "&desc_hist=" + desc_hist;
            System.out.println("URL update BD : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String insertarTablaHistoria(String id_pac, String id_doc,String fech_hist,String sint_hist,String desc_hist) {
        sint_hist=sint_hist.replace(" ","%20");
        desc_hist=desc_hist.replace(" ","%20");
        try {
            url = URL + "?operacion=insert&id_pac=" + id_pac +
                    "&id_doc=" + id_doc +
                    "&fech_hist=" + fech_hist +
                    "&sint_hist=" + sint_hist +
                    "&desc_hist=" + desc_hist;
            System.out.println("URL insertar BD : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String getBdByIdHistoria(int id_hist) {
        try {
            url = URL + "?operacion=get_bd_by_id&id_hist=" + id_hist;
            System.out.println("URL Insert BD : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String borrarBDHistoria(int id_hist) {
        try {
            url = URL + "?operacion=delete&id_hist=" + id_hist;
            System.out.println("URL borrado de la BD : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String buscaHistoriarBDid(String id_pac){
        try {
            url = URL + "?operacion=buscaHistoriarBDid&id_pac=" + id_pac;
            System.out.println("URL buscar BD por id : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String buscaHistoriarBDnombre_doc(String nombre_doc){
        nombre_doc=nombre_doc.replace(" ","%20");
        try {
            url = URL + "?operacion=buscaHistoriarBDnombre_doc&nombre_doc=" + nombre_doc;
            System.out.println("URL buscar BD por id : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String buscaHistoriarBDnombre_pac(String nombre_pac){
        nombre_pac=nombre_pac.replace(" ","%20");
        try {
            url = URL + "?operacion=buscaHistoriarBDnombre_pac&nombre_pac=" + nombre_pac;
            System.out.println("URL buscar BD por nombre : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String obtenBDid_doc(String nombre_doc){
        nombre_doc=nombre_doc.replace(" ","%20");
        try {
            url = URL + "?operacion=obtenBDid_doc&nombre_doc=" + nombre_doc;
            System.out.println("URL busacar BD id_doc : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }
//*************************************TABLA USUARIOS********************************************************************************

    public String mostrarBDMisPacientes(String nombre_doc) {
        nombre_doc=nombre_doc.replace(" ","%20");
        try {
            url = URL + "?operacion=viewMisPacientes"+ "&nombre_doc=" + nombre_doc;;
            System.out.println("URL mostrar mis pacientes en BD: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String buscarUsuario(String usuario) {
    usuario=usuario.replace(" ","%20");
    try {
        url = URL + "?operacion=buscarUsuario&usuario=" + usuario;
        System.out.println("URL buscar BD : " + url);
        response = call(url);
    } catch (Exception e) {
    }
    return response;
}
//*****************************************TABLA PACIENTE**************************************************************************
public String buscarPaciente(String nombre_pac) {
    nombre_pac=nombre_pac.replace(" ","%20");
    try {
        url = URL + "?operacion=buscaPacienteBDnombre_pac&nombre_pac=" + nombre_pac;
        System.out.println("URL buscar BD : " + url);
        response = call(url);
    } catch (Exception e) {
    }
    return response;
}

    public String actualizarBDPaciente(String id_pac, String nombre_pac,String fech_pac) {
        nombre_pac=nombre_pac.replace(" ","%20");
        try {
            url = URL + "?operacion=update&id_pac=" + id_pac + "&nombre_pac=" + nombre_pac + "&fech_pac=" + fech_pac;
            System.out.println("URL update BD : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String insertarTablaPaciente(String nombre_pac,String fech_pac,String password_pac) {
        nombre_pac=nombre_pac.replace(" ","%20");
        password_pac=password_pac.replace(" ","%20");
        try {
            url = URL + "?operacion=insert&nombre_pac=" + nombre_pac +
                    "&fech_pac=" + fech_pac +
                    "&password_pac=" + password_pac;
            System.out.println("URL insertar BD : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String getBdByIdPaciente(int id_pac) {
        try {
            url = URL + "?operacion=get_bd_by_id&id_pac=" + id_pac;
            System.out.println("URL buscar id BD : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }


    public String borrarBDPaciente(int id_pac) {
        try {
            url = URL + "?operacion=delete&id_pac=" + id_pac;
            System.out.println("URL borrado de la BD : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }


}
