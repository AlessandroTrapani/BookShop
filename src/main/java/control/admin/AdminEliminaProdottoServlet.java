package control.admin;

import java.io.IOException;

import dao.ProdottoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/elimina-prodotto")
public class AdminEliminaProdottoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");

        if (idParametro != null && !idParametro.trim().equals("")) {
            try {
                int idProdotto = Integer.parseInt(idParametro);

                ProdottoDAO prodottoDAO = new ProdottoDAO();
                prodottoDAO.eliminaLogicamente(idProdotto);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/prodotti");
    }
}