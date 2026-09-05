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

@WebServlet("/admin/nuovo-prodotto")
public class AdminNuovoProdottoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/nuovo-prodotto.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String titolo = request.getParameter("titolo");
        String autore = request.getParameter("autore");
        String categoria = request.getParameter("categoria");
        String prezzoParametro = request.getParameter("prezzo");
        String quantitaParametro = request.getParameter("quantita");
        String immagine = request.getParameter("immagine");
        String descrizione = request.getParameter("descrizione");

        String errore = null;

        double prezzo = 0;
        int quantita = 0;

        if (titolo == null || titolo.trim().equals("")
                || autore == null || autore.trim().equals("")
                || categoria == null || categoria.trim().equals("")
                || prezzoParametro == null || prezzoParametro.trim().equals("")
                || quantitaParametro == null || quantitaParametro.trim().equals("")) {

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

        if (errore != null) {
            request.setAttribute("errore", errore);
            request.setAttribute("titolo", titolo);
            request.setAttribute("autore", autore);
            request.setAttribute("categoria", categoria);
            request.setAttribute("prezzo", prezzoParametro);
            request.setAttribute("quantita", quantitaParametro);
            request.setAttribute("immagine", immagine);
            request.setAttribute("descrizione", descrizione);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/nuovo-prodotto.jsp");
            dispatcher.forward(request, response);
            return;
        }

        String stato = "DISPONIBILE";

        if (quantita == 0) {
            stato = "NON_DISPONIBILE";
        }

        Prodotto prodotto = new Prodotto();

        prodotto.setTitolo(titolo.trim());
        prodotto.setAutore(autore.trim());
        prodotto.setCategoria(categoria.trim());
        prodotto.setPrezzo(prezzo);
        prodotto.setQuantita(quantita);
        prodotto.setImmagine(immagine != null ? immagine.trim() : "");
        prodotto.setDescrizione(descrizione != null ? descrizione.trim() : "");
        prodotto.setStato(stato);

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        boolean inserito = prodottoDAO.inserisciProdotto(prodotto);

        if (inserito) {
            response.sendRedirect(request.getContextPath() + "/admin/prodotti");
        } else {
            request.setAttribute("errore", "Errore durante l'inserimento del prodotto.");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/nuovo-prodotto.jsp");
            dispatcher.forward(request, response);
        }
    }
}