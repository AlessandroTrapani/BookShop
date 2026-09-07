package control.admin;

import java.io.IOException;

import dao.OrdineDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Ordine;

@WebServlet("/admin/dettaglio-ordine")
public class AdminDettaglioOrdineServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");

        Ordine ordine = null;
        String errore = null;

        if (idParametro == null || idParametro.trim().equals("")) {
            errore = "Ordine non valido.";
        } else {
            try {
                int idOrdine = Integer.parseInt(idParametro);

                OrdineDAO ordineDAO = new OrdineDAO();
                ordine = ordineDAO.trovaOrdinePerAdmin(idOrdine);

                if (ordine == null) {
                    errore = "Ordine non trovato.";
                }

            } catch (NumberFormatException e) {
                errore = "Id ordine non valido.";
            }
        }

        request.setAttribute("ordine", ordine);
        request.setAttribute("errore", errore);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/dettaglio-ordine.jsp");
        dispatcher.forward(request, response);
    }
}