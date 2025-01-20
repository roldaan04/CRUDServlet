package org.example.crudBiblio.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.crudBiblio.Modelo.DAOGenerico;
import org.example.crudBiblio.Modelo.Ejemplar;
import org.example.crudBiblio.Modelo.Libro;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ejemplarServlet", value = "/ejemplar-servlet")
public class EjemplarServlet extends HttpServlet {
    DAOGenerico<Ejemplar> dao;
    ControladorEjemplar controladorEjemplar;
    public void init() {
        dao = new DAOGenerico<>(Ejemplar.class);
        controladorEjemplar=new ControladorEjemplar();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        int id = Integer.parseInt(request.getParameter("id"));
        String isbn=request.getParameter("isbn");
        String estado = request.getParameter("estado");
        String operacion = request.getParameter("operacion");

        if ("insert".equals(operacion)) {
            Ejemplar ejemplar = new Ejemplar(isbn, estado);
            dao.add(ejemplar);
            String jsonResponse = conversorJson.writeValueAsString(ejemplar);
            impresora.println(jsonResponse);

    } else if ("update".equals(operacion)) {
            Ejemplar ejemplar = dao.getbyId(id);
            controladorEjemplar.updateEjemplar(ejemplar);
            dao.update(ejemplar);
            String jsonResponse = conversorJson.writeValueAsString(ejemplar);
            impresora.println(jsonResponse);
        } else if ("delete".equals(operacion)) {
            Ejemplar ejemplar = dao.getbyId(id);
            dao.delete(ejemplar);
            String jsonResponse = conversorJson.writeValueAsString(ejemplar);
            impresora.println(jsonResponse);
        }else if("selectBy".equals(operacion)){
            Ejemplar ejemplar = dao.getbyId(id);
            String jsonResponse = conversorJson.writeValueAsString(ejemplar);
            impresora.println(jsonResponse);
        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        List<Ejemplar> lista = dao.getAll();
        System.out.println("En java" + lista);

        String json_response = conversorJson.writeValueAsString(lista);
        System.out.println("En java json" + json_response);
        impresora.println(json_response);

    }
}

