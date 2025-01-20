package org.example.crudBiblio.Controlador;

import org.example.crudBiblio.Modelo.DAOGenerico;
import org.example.crudBiblio.Modelo.Ejemplar;

import java.util.List;

public class ControladorEjemplar {

        public int calcularStock(List<Ejemplar> listaEjemplares){
            int contador = 0;
            for(Ejemplar e: listaEjemplares){
                if(e.getEstado().equals("Disponible")){
                    contador++;
                }
            }
            return contador;
        }

        public void updateEjemplar(Ejemplar e){
            e.setEstado("Prestado");
        }
    }


