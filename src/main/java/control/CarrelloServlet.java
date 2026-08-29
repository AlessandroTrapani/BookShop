package control;

import java.io.IOException;

import dao.ProdottoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Carrello;
import model.Prodotto;
import model.Utente;

@WebServlet("/carrello")
public class CarrelloServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessione = request.getSession();

        
        if (adminLoggato(sessione)) {
            response.sendRedirect(request.getContextPath() + "/admin/home");
            return;
        }

        Carrello carrello = (Carrello) sessione.getAttribute("carrello");

        if (carrello == null) {
            carrello = new Carrello();
            sessione.setAttribute("carrello", carrello);
        }

        request.setAttribute("carrello", carrello);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/pagine/carrello.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessione = request.getSession();

        if (adminLoggato(sessione)) {
            response.sendRedirect(request.getContextPath() + "/admin/home");
            return;
        }

        Carrello carrello = (Carrello) sessione.getAttribute("carrello");

        if (carrello == null) {
            carrello = new Carrello();
            sessione.setAttribute("carrello", carrello);
        }

        String azione = request.getParameter("azione");

        if (azione == null) {
            response.sendRedirect(request.getContextPath() + "/carrello");
            return;
        }

        if (azione.equals("aggiungi")) {
            aggiungiProdotto(request, carrello);
        } else if (azione.equals("aggiorna")) {
            aggiornaQuantita(request, carrello);
        } else if (azione.equals("rimuovi")) {
            rimuoviProdotto(request, carrello);
        } else if (azione.equals("svuota")) {
            carrello.svuota();
        }

        sessione.setAttribute("carrello", carrello);
        response.sendRedirect(request.getContextPath() + "/carrello");
    }

    private boolean adminLoggato(HttpSession sessione) {
        boolean admin = false;

        if (sessione != null) {
            Utente utenteLoggato = (Utente) sessione.getAttribute("utenteLoggato");

            if (utenteLoggato != null && "ADMIN".equals(utenteLoggato.getRuolo())) {
                admin = true;
            }
        }

        return admin;
    }

    private void aggiungiProdotto(HttpServletRequest request, Carrello carrello) {
        try {
            int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));

            if (quantita <= 0) {
                quantita = 1;
            }

            ProdottoDAO prodottoDAO = new ProdottoDAO();
            Prodotto prodotto = prodottoDAO.trovaPerId(idProdotto);

            if (prodotto != null
                    && "DISPONIBILE".equals(prodotto.getStato())
                    && prodotto.getQuantita() > 0) {

                if (quantita > prodotto.getQuantita()) {
                    quantita = prodotto.getQuantita();
                }

                carrello.aggiungiProdotto(prodotto, quantita);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    private void aggiornaQuantita(HttpServletRequest request, Carrello carrello) {
        try {
            int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));

            carrello.aggiornaQuantita(idProdotto, quantita);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    private void rimuoviProdotto(HttpServletRequest request, Carrello carrello) {
        try {
            int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
            carrello.rimuoviProdotto(idProdotto);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}