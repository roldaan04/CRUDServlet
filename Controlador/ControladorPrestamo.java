package org.example.crudBiblio.Controlador;

import org.example.crudBiblio.Modelo.Ejemplar;
import org.example.crudBiblio.Modelo.Prestamo;
import org.example.crudBiblio.Modelo.Usuario;

import java.time.LocalDate;
import java.util.List;

public class ControladorPrestamo {


        public LocalDate calcularFechaLimite(Prestamo p){
            return p.getFechaInicio().plusDays(15);
        }

        public LocalDate registrarDevolucion(Prestamo p){
            Ejemplar devolucion= p.getEjemplar();
            LocalDate fechaDevuelto= LocalDate.now();
            devolucion.setEstado("Disponible");
            return fechaDevuelto;
        }

        public Prestamo realizarPrestamo(Ejemplar e, Usuario u, LocalDate fechaInicio){
            boolean validacion= true;
            String mensaje= null;
            if(u.getPrestamos().size() > 3){
                validacion= false;
                mensaje= "El usuario tiene mas de 3 prestamos activos";
            }
            if(!(e.getEstado().equals("Disponible"))){
                validacion= false;
                mensaje= "El ejemplar no esta disponible";
            }
            if(u.getPenalizacionHasta() != null){
                validacion= false;
                mensaje= "La penalizacion esta activa";
            }
            if(validacion){
                Prestamo p= new Prestamo(u, e, fechaInicio);
                return p;
            }else{
                System.out.println(mensaje);
            }
            return null;
        }

        public String establecerPenalizacion(Usuario u){ //si no se pasa la fecha no será penalizado pero se actualiza el estado (se devuleve)
            int dias=0;
            String mensaje= null;
            for(Prestamo prestamo : u.getPrestamos()){
                LocalDate fecha= registrarDevolucion(prestamo);
                if(registrarDevolucion(prestamo).isAfter(prestamo.getFechaDevolucion())){
                    dias+= 15;
                }
            }
            if(dias>0){
                u.setPenalizacionHasta(LocalDate.now().plusDays(dias));
                mensaje= "Se ha establecido penalización";
            }else{
                mensaje= "No se ha establecido penalización";
            }
            return mensaje;
        }

    }


