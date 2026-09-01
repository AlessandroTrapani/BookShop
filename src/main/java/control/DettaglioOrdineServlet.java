package control;

import java.io.IOException;

import dao.OrdineDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Ordine;
import model.Utente;

@WebServlet("/dettaglio-ordine")
public class DettaglioOrdineServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessione = request.getSession();

        Utente utenteLoggato = (Utente) sessione.getAttribute("utenteLoggato");

        if (utenteLoggato == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String idParametro = request.getParameter("id");

        Ordine ordine = null;
        String errore = null;

        if (idParametro == null || idParametro.trim().equals("")) {
            errore = "Ordine non valido.";
        } else {
            try {
                int idOrdine = Integer.parseInt(idParametro);

                OrdineDAO ordineDAO = new OrdineDAO();
                ordine = ordineDAO.trovaOrdinePerUtente(idOrdine, utenteLoggato.getId());

                if (ordine == null) {
                    errore = "Ordine non trovato.";
                }

            } catch (NumberFormatException e) {
                errore = "Id ordine non valido.";
            }
        }

        request.setAttribute("ordine", ordine);
        request.setAttribute("errore", errore);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/pagine/dettaglio-ordine.jsp");
        dispatcher.forward(request, response);
    }
}