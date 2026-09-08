package control.admin;

import java.io.IOException;

import dao.OrdineDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/aggiorna-stato-ordine")
public class AdminAggiornaStatoOrdineServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        String stato = request.getParameter("stato");

        int idOrdine = 0;

        if (idParametro != null && !idParametro.trim().equals("")) {
            try {
                idOrdine = Integer.parseInt(idParametro);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (idOrdine > 0 && statoValido(stato)) {
            OrdineDAO ordineDAO = new OrdineDAO();
            ordineDAO.aggiornaStatoOrdine(idOrdine, stato);

            request.getSession().setAttribute("messaggioSuccesso", "Stato ordine aggiornato correttamente.");
        } else {
            request.getSession().setAttribute("messaggioErrore", "Stato ordine non valido.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/dettaglio-ordine?id=" + idOrdine);
    }

    private boolean statoValido(String stato) {
        return "IN_ELABORAZIONE".equals(stato)
                || "SPEDITO".equals(stato)
                || "COMPLETATO".equals(stato)
                || "ANNULLATO".equals(stato);
    }
}