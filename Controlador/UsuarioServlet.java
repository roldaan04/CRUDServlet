package org.example.crudBiblio.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.crudBiblio.Modelo.DAOGenerico;
import org.example.crudBiblio.Modelo.Libro;
import org.example.crudBiblio.Modelo.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

@WebServlet(name = "usuarioServlet", value = "/usuario-servlet")
public class UsuarioServlet extends HttpServlet {
    DAOGenerico<Usuario> daoUsuario;
    public void init(){
        daoUsuario = new DAOGenerico<>(Usuario.class);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        int id= Integer.parseInt(request.getParameter("id"));
        String dni = request.getParameter("dni");
        String nombre = request.getParameter("nombre");
        String email= request.getParameter("email");
        String password = request.getParameter("password");
        String tipo = request.getParameter("tipo");
        LocalDate penalizacion = null;
        String operacion = request.getParameter("operacion");

        if ("insert".equals(operacion)) {
            Usuario usuario= new Usuario(dni, nombre, email, password, tipo, penalizacion);
            daoUsuario.add(usuario);
            String jsonResponse = conversorJson.writeValueAsString(usuario);
            impresora.println(jsonResponse);

        } else if ("update".equals(operacion)) {
            Usuario usuario= daoUsuario.getbyId(id);
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(password);
            usuario.setTipo(tipo);
            daoUsuario.update(usuario);
            String jsonResponse = conversorJson.writeValueAsString(usuario);
            impresora.println(jsonResponse);

        } else if ("delete".equals(operacion)) {
            Usuario usuario= new Usuario(dni, nombre, email, password, tipo, penalizacion);
            daoUsuario.delete(usuario);
            String jsonResponse = conversorJson.writeValueAsString(usuario);
            impresora.println(jsonResponse);

        } else if ("deleteby".equals(operacion)) {
            daoUsuario.deletebyId(id);
            String jsonResponse = conversorJson.writeValueAsString("Usuario borrado: " + id);
            impresora.println(jsonResponse);
        }else if("selectBy".equals(operacion)){
            daoUsuario.getbyId(id);
            String jsonResponse = conversorJson.writeValueAsString("Usuario: " + daoUsuario.getbyId(id));
            impresora.println(jsonResponse);
        }


    }
}
