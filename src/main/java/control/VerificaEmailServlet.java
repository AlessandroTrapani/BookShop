package control;

import java.io.IOException;

import dao.UtenteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/verifica-email")
public class VerificaEmailServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        boolean valida = true;
        String messaggio = "Email disponibile.";

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (email == null || email.trim().equals("")) {
            valida = false;
            messaggio = "L'email è obbligatoria.";
        } else {
            UtenteDAO utenteDAO = new UtenteDAO();

            if (utenteDAO.emailEsistente(email.trim())) {
                valida = false;
                messaggio = "Email già registrata.";
            }
        }

        String json = "{"
                + "\"valida\":" + valida + ","
                + "\"messaggio\":\"" + messaggio + "\""
                + "}";

        response.getWriter().write(json);
    }
}