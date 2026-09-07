package control.admin;

import java.io.IOException;
import java.util.ArrayList;

import dao.OrdineDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Ordine;

@WebServlet("/admin/ordini")
public class AdminOrdiniServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String dataInizio = request.getParameter("dataInizio");
        String dataFine = request.getParameter("dataFine");
        String idUtenteParametro = request.getParameter("idUtente");

        int idUtente = 0;
        String errore = null;

        if (idUtenteParametro != null && !idUtenteParametro.trim().equals("")) {
            try {
                idUtente = Integer.parseInt(idUtenteParametro.trim());

                if (idUtente < 0) {
                    errore = "L'id cliente non può essere negativo.";
                }

            } catch (NumberFormatException e) {
                errore = "L'id cliente deve essere un numero.";
            }
        }

        ArrayList<Ordine> ordini = new ArrayList<Ordine>();

        if (errore == null) {

            OrdineDAO ordineDAO = new OrdineDAO();
            ordini = ordineDAO.trovaOrdiniAdminFiltrati(dataInizio, dataFine, idUtente);
        }

        request.setAttribute("ordini", ordini);
        request.setAttribute("dataInizio", dataInizio);
        request.setAttribute("dataFine", dataFine);
        request.setAttribute("idUtente", idUtenteParametro);
        request.setAttribute("errore", errore);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/ordini.jsp");
        dispatcher.forward(request, response);
    }
}