package co.edu.eafit.equations;

import java.util.ArrayList;

public class Tabla
{
    private ArrayList<ArrayList<String>> filas;
    private String result;
    public ArrayList<ArrayList<String>> getArray(){
        return this.filas;
    }
    public Tabla()
    {
        filas = new ArrayList<ArrayList<String>>();
    }
    public void addHead(String[] head)
    {
        ArrayList<String> cabecera = new ArrayList<String>();
        for(String text: head){
            cabecera.add(text);
        }
        filas.add(cabecera);
    }
    public void addRow(ArrayList<String> item)
    {
        filas.add(item);
    }
    public String getResult(){return this.result;}
    public void setResult(String result){this.result=result;}
}