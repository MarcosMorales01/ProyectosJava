package Datos;

import Lista.ListaDDeque;
import Lista.Nodo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * José Marcos Morales Gómez
 */

public class DatosLista extends Datos {
    ListaDDeque Nombre = null;
    ListaDDeque Profesion = null;
    ListaDDeque Calificacion = null; 
    String Path;
    
    boolean CampoNom;
    boolean CampoProf;
    boolean CampoCal;
    
    public DatosLista(String path, boolean nom, boolean prof, boolean cal ) throws IOException {
        Path = path;
        CampoNom = nom; 
        CampoProf = prof;
        CampoCal = cal;
        leer();
    }
    
    
    @Override
    public void leer() throws IOException{
        String linea = "";
        String palabras[]; 
        String url = "D:\\Equipo 2\\Morales\\Documents\\UADY\\4° Semestre\\Estructura de Datos\\Egresados.csv";
        //cambia la url por el Path que recive en el Constructor
        
        
        if(CampoNom){
            Nombre = new ListaDDeque();
        }
        if(CampoProf){
            Profesion = new ListaDDeque();
        }
        if(CampoCal){
            Calificacion = new ListaDDeque();
        }
        
        try {
            FileReader Lector = new FileReader(url);
            BufferedReader buffer = new BufferedReader(Lector);
            if(buffer.ready()){
                buffer.readLine();
            }
            
            int i = 1;
            while(buffer.ready()){
                if(!(linea = buffer.readLine()).equals("\0")){
                   palabras=linea.split(",");
                   
                   if(CampoNom){
                       Nombre.insertLast(new Elemento(fHash(palabras[0]), i));
                     
                   }
                   
                   if(CampoProf){
                       Profesion.insertLast(new Elemento(fHash(palabras[1]), i));
                       
                   }
                   
                   if(CampoCal){
                       Calificacion.insertLast(new Elemento(fHash(palabras[2]), i));
                       
                   }
                }
                
                i++;
            }
            
            Lector.close();
            buffer.close();

        }catch(java.io.FileNotFoundException e){
            JOptionPane.showMessageDialog(null,"No se ha podido encontrar el archivo con los alumnos");
        }
    }
    
    /*
    public int fHash(String cadena){
        String x="0";
        for (int i = 0; i < cadena.length(); i=i+8) {
            x=x + (int)(cadena.charAt(i));
        }
        //System.out.println(x);
        return Integer.parseInt(x);
    }*/

    @Override
    public ArrayList BusquedaNombre(int indice) {
        ArrayList ANombre = new ArrayList<>();
        
        Nodo actual = Nombre.getInicio();
        while (actual != null) {
            if(((Elemento)actual.getDato()).getDato()==indice){
                ANombre.add(actual.getDato());
            }
            actual = actual.getSiguiente();
        }

        return ANombre;

    }
    
    @Override
    public ArrayList Busqueda(String n, String p, String c){
        ArrayList ANombre;
        ArrayList AProfesion;
        ArrayList ACalificacion;
        ArrayList temp = new ArrayList<>();
        
        if(Nombre != null && Profesion == null && Calificacion == null){
            ANombre = BusquedaNombre(fHash(n));
            return ANombre;
        }
        
        if(Nombre == null && Profesion != null && Calificacion == null){
            AProfesion = BusquedaProfesion(fHash(p));
            return AProfesion;
        }
        
        if (Nombre == null && Profesion == null && Calificacion != null) {
            ACalificacion = BusquedaCalificacion(fHash(c));
            return ACalificacion;
        }
    
                
        if (Nombre != null && Profesion != null && Calificacion == null) {
            ANombre = BusquedaNombre(fHash(n));
            AProfesion = BusquedaProfesion(fHash(p));

            for (int i = 0; i < ANombre.size(); i++) {
                for (int j = 0; j < AProfesion.size(); j++) {
                    boolean x =((Elemento)(ANombre.get(i))).getLinea()==((Elemento)(AProfesion.get(j))).getLinea();
                    if(x){
                        temp.add(ANombre.get(i));
                    }
                }
            }
            return temp;
        }

        if (Nombre != null && Profesion == null && Calificacion != null) {
            ANombre = BusquedaNombre(fHash(n));
            ACalificacion = BusquedaCalificacion(fHash(c));

            for (int i = 0; i < ANombre.size(); i++) {
                for (int j = 0; j < ACalificacion.size(); j++) {
                    boolean x =((Elemento)(ANombre.get(i))).getLinea()==((Elemento)(ACalificacion.get(j))).getLinea();
                    if(x){
                        temp.add(ANombre.get(i));
                    }
                }
            }
            return temp;
        }

        if (Nombre == null && Profesion != null && Calificacion != null) {
            ACalificacion = BusquedaCalificacion(fHash(c));
            AProfesion = BusquedaProfesion(fHash(p));

            for (int i = 0; i < ACalificacion.size(); i++) {
                for (int j = 0; j < AProfesion.size(); j++) {
                    boolean x = ((Elemento) (ACalificacion.get(i))).getLinea() == ((Elemento) (AProfesion.get(j))).getLinea();
                    if (x) {
                        temp.add(ACalificacion.get(i));
                    }
                }
            }
            return temp;
        }
              
        if (Nombre != null && Profesion != null && Calificacion != null) {
            ANombre = BusquedaNombre(fHash(n));
            AProfesion = BusquedaProfesion(fHash(p));
            ACalificacion = BusquedaCalificacion(fHash(c));
            
            for (int i = 0; i < ANombre.size(); i++) {
                for (int j = 0; j< AProfesion.size(); j++) {
                    for (int k = 0; k < ACalificacion.size(); k++) {
                        boolean x = ((Elemento) (ACalificacion.get(i))).getLinea() == ((Elemento) (AProfesion.get(j))).getLinea() 
                                && ((Elemento) (AProfesion.get(j))).getLinea() == ((Elemento) (ANombre.get(k))).getLinea();
                        if (x) {
                            temp.add(ACalificacion.get(i));
                        }
                    }
                }
            }
            return temp;
        }
        
        return temp;

    }

    @Override
    public ArrayList BusquedaProfesion(int indice) {
        ArrayList AProfesion = new ArrayList<Elemento>();

        Nodo actual = Profesion.getInicio();
        while (actual != null) {
            if (((Elemento) actual.getDato()).getDato() == indice) {
                AProfesion.add(actual.getDato());
            }
            actual = actual.getSiguiente();
        }

        return AProfesion;
    }

    @Override
    public ArrayList BusquedaCalificacion(int indice) {
        ArrayList ACalificacion = new ArrayList<>();

        Nodo actual = Calificacion.getInicio();
        while (actual != null) {
            if (((Elemento) actual.getDato()).getDato() == indice) {
                ACalificacion.add(actual.getDato());
            }
            actual = actual.getSiguiente();
        }
        return ACalificacion;
    }
    /*
    public static void main (String [] arsg) throws IOException{
        DatosLista d = new DatosLista("hola", false, true, false);
        ArrayList a = d.Busqueda("", "Educacion", "83");
        for (int i = 0; i < a.size(); i++) {
            System.out.println(((Elemento)a.get(i)).getLinea());
        }
    }*/
}