package control;

import java.io.IOException;
import java.util.UUID;

import dao.UtenteDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Utente;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/pagine/login.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String errore = null;

        if (email == null || email.trim().equals("") || password == null || password.trim().equals("")) {
            errore = "Email e password sono obbligatorie.";
        } else {
            UtenteDAO utenteDAO = new UtenteDAO();
            Utente utente = utenteDAO.trovaPerEmailEPassword(email.trim(), password.trim());

            if (utente == null) {
                errore = "Credenziali non valide.";
            } else {
                HttpSession sessione = request.getSession();
                sessione.setAttribute("utenteLoggato", utente);

                String tokenAccesso = UUID.randomUUID().toString();
                sessione.setAttribute("tokenAccesso", tokenAccesso);

                if ("ADMIN".equals(utente.getRuolo())) {
                    response.sendRedirect(request.getContextPath() + "/admin/home");
                } else {
                    response.sendRedirect(request.getContextPath() + "/home");
                }

                return;
            }
        }

        request.setAttribute("errore", errore);
        request.setAttribute("email", email);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/pagine/login.jsp");
        dispatcher.forward(request, response);
    }
}