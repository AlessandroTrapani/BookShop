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

@WebServlet("/registrazione")
public class RegistrazioneServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/pagine/registrazione.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confermaPassword = request.getParameter("confermaPassword");

        String errore = null;

        if (nome == null || nome.trim().equals("")
                || cognome == null || cognome.trim().equals("")
                || email == null || email.trim().equals("")
                || password == null || password.trim().equals("")
                || confermaPassword == null || confermaPassword.trim().equals("")) {

            errore = "Tutti i campi sono obbligatori.";

        } else if (password.trim().length() < 5) {

            errore = "La password deve contenere almeno 5 caratteri.";

        } else if (!password.equals(confermaPassword)) {

            errore = "Le password non coincidono.";

        } else {
            UtenteDAO utenteDAO = new UtenteDAO();

            if (utenteDAO.emailEsistente(email.trim())) {
                errore = "Email già registrata.";
            } else {
                Utente nuovoUtente = new Utente();

                nuovoUtente.setNome(nome.trim());
                nuovoUtente.setCognome(cognome.trim());
                nuovoUtente.setEmail(email.trim());
                nuovoUtente.setPassword(password.trim());
                nuovoUtente.setRuolo("UTENTE");

                boolean inserito = utenteDAO.inserisciUtente(nuovoUtente);

                if (inserito) {
                    Utente utenteCreato = utenteDAO.trovaPerEmailEPassword(email.trim(), password.trim());

                    HttpSession sessione = request.getSession();
                    sessione.setAttribute("utenteLoggato", utenteCreato);

                    String tokenAccesso = UUID.randomUUID().toString();
                    sessione.setAttribute("tokenAccesso", tokenAccesso);

                    response.sendRedirect(request.getContextPath() + "/home");
                    return;
                } else {
                    errore = "Errore durante la registrazione.";
                }
            }
        }

        request.setAttribute("errore", errore);
        request.setAttribute("nome", nome);
        request.setAttribute("cognome", cognome);
        request.setAttribute("email", email);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/pagine/registrazione.jsp");
        dispatcher.forward(request, response);
    }
}