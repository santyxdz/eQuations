package co.edu.eafit.equations;

import android.app.Application;

/**
 * Created by Santi on 30/09/2015.
 * Clase Global para compartir informacion que debe persistir en el tiempo, como la funcion y otros.
 */
public class GlobalClass extends Application {
    /*Variables*/
    private String example;

    /*Getters*/
    public String getExample(){
        return this.example;
    }

    /*Setter*/
    public void setExample(String example){
        this.example = example;
    }

    /*
    // Método para enviar los datos de los editText a la variable Global
    public void setGlobal() {
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        globalVariable.setNombre(etNombre.getText().toString());
        globalVariable.setApellido(etApellido.getText().toString());
    }

    // Método para obtener los datos desde la clase global y asignarlos al editText
    public void getGlobal() {
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        // Obtenemos valores desde la clase global
        final String nombre = globalVariable.getNombre();
        final String apellido = globalVariable.getApellido();

        // Asignar los valores obtener a los editText
        etNombre.setText(nombre);
        etApellido.setText(apellido);
    }
    */
}
