package control.admin;

import java.io.IOException;
import java.util.ArrayList;

import dao.ProdottoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;

@WebServlet("/admin/prodotti")
public class AdminProdottiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        ArrayList<Prodotto> prodotti = prodottoDAO.trovaTuttiAdmin();

        request.setAttribute("prodotti", prodotti);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/prodotti.jsp");
        dispatcher.forward(request, response);
    }
}