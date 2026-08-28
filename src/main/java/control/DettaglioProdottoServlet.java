package control;

import java.io.IOException;

import dao.ProdottoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;

@WebServlet("/dettaglio-prodotto")
public class DettaglioProdottoServlet extends HttpServlet {

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
                int id = Integer.parseInt(idParametro);

                ProdottoDAO prodottoDAO = new ProdottoDAO();
                prodotto = prodottoDAO.trovaPerId(id);

                if (prodotto == null) {
                    errore = "Prodotto non trovato.";

                } else if (!"DISPONIBILE".equals(prodotto.getStato()) || prodotto.getQuantita() <= 0) {
                    errore = "Prodotto non disponibile.";
                    prodotto = null;
                }

            } catch (NumberFormatException e) {
                errore = "Id prodotto non valido.";
            }
        }

        request.setAttribute("prodotto", prodotto);
        request.setAttribute("errore", errore);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/pagine/dettaglio-prodotto.jsp");
        dispatcher.forward(request, response);
    }
}