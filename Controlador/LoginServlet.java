package org.example.crudBiblio.Controlador;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.crudBiblio.Modelo.DAOGenerico;
import org.example.crudBiblio.Modelo.Usuario;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {
    DAOGenerico<Usuario> DAO= new DAOGenerico<>(Usuario.class);

    public void init(){
        DAO = new DAOGenerico<>(Usuario.class);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/html");
        PrintWriter out = response.getWriter();
        String nombre = request.getParameter("nombre");
        String password = request.getParameter("password");
        out.println(DAO.buscarNombre(nombre, password));
    }

}
