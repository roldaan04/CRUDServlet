package org.example.crudBiblio.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.crudBiblio.Modelo.DAOGenerico;
import org.example.crudBiblio.Modelo.Libro;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "librosServlet", value = "/libros-servlet")
public class LibroServlet extends HttpServlet {

    DAOGenerico<Libro> daolibro;

    public void init(){
        daolibro = new DAOGenerico<>(Libro.class);
    }
    //INSERT, UPDATE, DELETE, SELECTByISBN
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String isbn = request.getParameter("isbn");
        String operacion = request.getParameter("operacion");

        if ("insert".equals(operacion)) {
            Libro libro = new Libro(isbn, titulo, autor);
            daolibro.add(libro);
            String jsonResponse = conversorJson.writeValueAsString(libro);
            impresora.println(jsonResponse);

        } else if ("update".equals(operacion)) {
            Libro libro = new Libro(isbn, titulo, autor);
            daolibro.update(libro);
            String jsonResponse = conversorJson.writeValueAsString(libro);
            impresora.println(jsonResponse);

        } else if ("delete".equals(operacion)) {
            Libro libro = new Libro(isbn, titulo, autor);
            daolibro.delete(libro);
            String jsonResponse = conversorJson.writeValueAsString(libro);
            impresora.println(jsonResponse);

        } else if ("deleteBy".equals(operacion)) {
            daolibro.deletebyId(isbn);
            String jsonResponse = conversorJson.writeValueAsString("Libro borrado por ISBN: " + isbn);
            impresora.println(jsonResponse);

        }
    }
//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//            String titulo = request.getParameter("titulo");
//            String autor = request.getParameter("autor");
//            String isbn = request.getParameter("isbn");
//            String opcion= request.getParameter("opcion");
//
//            response.setContentType("application/html");
//            PrintWriter out = response.getWriter();
//
//
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet Parametros Servlet</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>CRUD Servlet</h1>");
//
//            if(opcion.equals("insert")){
//                Libro libro = new Libro(titulo, autor, isbn);
//                System.out.println("a");
//                daolibro.add(libro);
//                out.println("El libro creado es: " + libro.toString());
//            }else if(opcion.equals("update")){
//                Libro libro = new Libro(titulo, autor, isbn);
//                daolibro.update(libro);
//                out.println("El libro actualizado es: " + libro.toString());
//            }else if(opcion.equals("delete")){
//                Libro libro = new Libro(titulo, autor, isbn);
//                daolibro.delete(libro);
//                out.println("El libro eliminado es: " + libro.toString());
//            } else if (opcion.equals("deleteBy")) {
//                Libro libro = new Libro(titulo, autor, isbn);
//                daolibro.deletebyId(isbn);
//                out.println("El libro eliminado es: " + libro.toString());
//            }
//
//        out.println("</body>");
//        out.println("</html>");
//    }

    //SELECTALL
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        List<Libro> listaLibros  = daolibro.getAll();
        System.out.println("En java" + listaLibros);

        String json_response = conversorJson.writeValueAsString(listaLibros);
        System.out.println("En java json" + json_response);
        impresora.println(json_response);

    }



    public void destroy(){

    }
}

