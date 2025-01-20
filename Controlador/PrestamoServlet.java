    package org.example.crudBiblio.Controlador;

    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import org.example.crudBiblio.Modelo.DAOGenerico;
    import org.example.crudBiblio.Modelo.Ejemplar;
    import org.example.crudBiblio.Modelo.Prestamo;
    import org.example.crudBiblio.Modelo.Usuario;

    import java.io.IOException;
    import java.io.PrintWriter;
    import java.time.LocalDate;
    import java.util.List;

    @WebServlet(name = "prestamosServlet", value = "/prestamo-servlet")
    public class PrestamoServlet extends HttpServlet {

        DAOGenerico<Prestamo> daoPrestamo;
        DAOGenerico<Ejemplar> daoEjemplar;
        DAOGenerico<Usuario> daoUsuario;
        ControladorPrestamo controladorPrestamo;
        ControladorEjemplar controladorEjemplar;
        public void init() {
            daoPrestamo = new DAOGenerico<>(Prestamo.class);
            daoEjemplar = new DAOGenerico<>(Ejemplar.class);
            daoUsuario = new DAOGenerico<>(Usuario.class);
            controladorPrestamo = new ControladorPrestamo();
             controladorEjemplar= new ControladorEjemplar();
        }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("application/json");

            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorJson = new ObjectMapper();
            conversorJson.registerModule(new JavaTimeModule());


            String idParam = request.getParameter("id");
            Integer id = null;
            if (idParam != null && !idParam.isEmpty()) {
                id = Integer.parseInt(idParam);
            }

            String idEjemplarParam = request.getParameter("idEjemplar");
            Integer idEjemplar = null;
            if (idEjemplarParam != null && !idEjemplarParam.isEmpty()) {
                idEjemplar = Integer.parseInt(idEjemplarParam);
            }

            String idUsuarioParam = request.getParameter("idUsuario");
            Integer idUsuario = null;
            if (idUsuarioParam != null && !idUsuarioParam.isEmpty()) {
                idUsuario = Integer.parseInt(idUsuarioParam);
            }

            String fechaInicioParam= request.getParameter("fechaInicio");
            LocalDate fechaInicio= null;
            if(fechaInicioParam != null && !fechaInicioParam.isEmpty()) {
                fechaInicio = LocalDate.parse(fechaInicioParam);
            }

            String fechaDevolucionParam= request.getParameter("fechaDevolucion");
            LocalDate fechaDevolucion= null;
            if(fechaDevolucionParam != null && !fechaDevolucionParam.isEmpty()) {
                fechaDevolucion = LocalDate.parse(fechaDevolucionParam);
            }
            String operacion = request.getParameter("operacion");

            if ("insert".equals(operacion)) {
                Ejemplar ejemplar = daoEjemplar.getbyId(idEjemplar);
                Usuario usuario = daoUsuario.getbyId(idUsuario);
                Prestamo prestamito = controladorPrestamo.realizarPrestamo(ejemplar, usuario, LocalDate.now());
                daoPrestamo.add(prestamito);
                controladorEjemplar.updateEjemplar(ejemplar);
                daoEjemplar.update(ejemplar);
                String jsonResponse = conversorJson.writeValueAsString(prestamito);
                impresora.println(jsonResponse);
            } else if ("update".equals(operacion)) {
                Prestamo prestamo= daoPrestamo.getbyId(id);
                prestamo.setFechaInicio(fechaInicio);
                //prestamo.setFechaDevolucion(fechaDevolucion);
                daoPrestamo.update(prestamo);

                String jsonResponse = conversorJson.writeValueAsString(prestamo);
                impresora.println(jsonResponse);
            } else if ("delete".equals(operacion)) {
                daoPrestamo.deletebyId(id);
                System.out.println("id: " + id);
                System.out.println("operacion: " + operacion);
                String jsonResponse = conversorJson.writeValueAsString("Usuario borrado: " + id);
                impresora.println(jsonResponse);
            }else if("selectBy".equals(operacion)){
                Prestamo prestamo = daoPrestamo.getbyId(id);
                String jsonResponse = conversorJson.writeValueAsString("Prestamo: " + prestamo);
                impresora.println(jsonResponse);
            }else if("devolver".equals(operacion)){
                Usuario usuario= daoUsuario.getbyId(idUsuario);
                String mensaje= controladorPrestamo.establecerPenalizacion(usuario);
                daoUsuario.update(usuario);
                for(Prestamo p: usuario.getPrestamos()){
                    Ejemplar ejemplar= p.getEjemplar();
                    daoEjemplar.update(ejemplar);
                }
                String jsonResponse = conversorJson.writeValueAsString(mensaje);
                impresora.println(jsonResponse);
            }

        }

        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("application/json");

            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorJson = new ObjectMapper();
            conversorJson.registerModule(new JavaTimeModule());

            List<Prestamo> lista = daoPrestamo.getAll();
            System.out.println("En java" + lista);

            String json_response = conversorJson.writeValueAsString(lista);
            System.out.println("En java json" + json_response);
            impresora.println(json_response);

        }

    }
