package org.example.crudBiblio.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.crudBiblio.Modelo.DAOGenerico;
import org.example.crudBiblio.Modelo.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

@WebServlet(name = "registroServlet", value = "/registro-servlet")
public class RegistroServlet extends HttpServlet {
    DAOGenerico<Usuario> daoUsuario;

    public void init() {
        daoUsuario = new DAOGenerico<>(Usuario.class);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());
            String dni = req.getParameter("dni");
            String nombre = req.getParameter("nombre");
            String email= req.getParameter("email");
            String password = req.getParameter("password");
            String tipo = req.getParameter("tipo");
            LocalDate penalizacion = null;


        Usuario usuario1 = new Usuario(dni, nombre, email, password, tipo, penalizacion);
            daoUsuario.add(usuario1);
            String jsonResponse = conversorJson.writeValueAsString(usuario1);
            out.println(jsonResponse);

        }

    }


