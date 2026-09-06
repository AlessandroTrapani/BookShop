package control.admin;

import java.io.IOException;

import dao.ProdottoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;

@WebServlet("/admin/modifica-prodotto")
public class AdminModificaProdottoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");

        Prodotto prodotto = null;
        String errore = null;

        if (idParametro == null || idParametro.trim().equals("")) {
            errore = "Prodotto non valido.";
        } else {
            try {
                int idProdotto = Integer.parseInt(idParametro);

                ProdottoDAO prodottoDAO = new ProdottoDAO();
                prodotto = prodottoDAO.trovaPerId(idProdotto);

                if (prodotto == null) {
                    errore = "Prodotto non trovato.";
                }

            } catch (NumberFormatException e) {
                errore = "Id prodotto non valido.";
            }
        }

        request.setAttribute("prodotto", prodotto);
        request.setAttribute("errore", errore);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/modifica-prodotto.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        String titolo = request.getParameter("titolo");
        String autore = request.getParameter("autore");
        String categoria = request.getParameter("categoria");
        String prezzoParametro = request.getParameter("prezzo");
        String quantitaParametro = request.getParameter("quantita");
        String immagine = request.getParameter("immagine");
        String descrizione = request.getParameter("descrizione");

        String errore = null;

        int idProdotto = 0;
        double prezzo = 0;
        int quantita = 0;

        if (idParametro == null || idParametro.trim().equals("")) {
            errore = "Prodotto non valido.";
        } else {
            try {
                idProdotto = Integer.parseInt(idParametro);
            } catch (NumberFormatException e) {
                errore = "Id prodotto non valido.";
            }
        }

        if (errore == null
                && (titolo == null || titolo.trim().equals("")
                || autore == null || autore.trim().equals("")
                || categoria == null || categoria.trim().equals("")
                || prezzoParametro == null || prezzoParametro.trim().equals("")
                || quantitaParametro == null || quantitaParametro.trim().equals(""))) {

            errore = "Titolo, autore, categoria, prezzo e quantità sono obbligatori.";
        }

        if (errore == null) {
            try {
                prezzo = Double.parseDouble(prezzoParametro.trim().replace(",", "."));
                quantita = Integer.parseInt(quantitaParametro.trim());

                if (prezzo <= 0) {
                    errore = "Il prezzo deve essere maggiore di zero.";
                } else if (quantita < 0) {
                    errore = "La quantità non può essere negativa.";
                }

            } catch (NumberFormatException e) {
                errore = "Prezzo e quantità devono essere valori numerici.";
            }
        }

        String stato = "DISPONIBILE";

        if (quantita == 0) {
            stato = "NON_DISPONIBILE";
        }

        Prodotto prodotto = new Prodotto();

        prodotto.setId(idProdotto);
        prodotto.setTitolo(titolo != null ? titolo.trim() : "");
        prodotto.setAutore(autore != null ? autore.trim() : "");
        prodotto.setCategoria(categoria != null ? categoria.trim() : "");
        prodotto.setPrezzo(prezzo);
        prodotto.setQuantita(quantita);
        prodotto.setImmagine(immagine != null ? immagine.trim() : "");
        prodotto.setDescrizione(descrizione != null ? descrizione.trim() : "");
        prodotto.setStato(stato);

        if (errore != null) {
            request.setAttribute("errore", errore);
            request.setAttribute("prodotto", prodotto);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/modifica-prodotto.jsp");
            dispatcher.forward(request, response);
            return;
        }

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        boolean aggiornato = prodottoDAO.aggiornaProdotto(prodotto);

        if (aggiornato) {
            response.sendRedirect(request.getContextPath() + "/admin/prodotti");
        } else {
            request.setAttribute("errore", "Errore durante l'aggiornamento del prodotto.");
            request.setAttribute("prodotto", prodotto);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/modifica-prodotto.jsp");
            dispatcher.forward(request, response);
        }
    }
}