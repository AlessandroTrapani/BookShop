package filtro;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Utente;

@WebFilter(urlPatterns = {
        "/checkout",
        "/storico-ordini",
        "/dettaglio-ordine"
})
public class FiltroUtente implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest richiestaHttp = (HttpServletRequest) request;
        HttpServletResponse rispostaHttp = (HttpServletResponse) response;

        HttpSession sessione = richiestaHttp.getSession(false);

        if (sessione == null
                || sessione.getAttribute("utenteLoggato") == null
                || sessione.getAttribute("tokenAccesso") == null) {

            rispostaHttp.sendRedirect(richiestaHttp.getContextPath() + "/login");
            return;
        }

        Utente utenteLoggato = (Utente) sessione.getAttribute("utenteLoggato");

        if ("ADMIN".equals(utenteLoggato.getRuolo())) {

            rispostaHttp.sendRedirect(richiestaHttp.getContextPath() + "/admin/home");
            return;
        }

        if ("UTENTE".equals(utenteLoggato.getRuolo())) {
            chain.doFilter(request, response);
            return;
        }

        rispostaHttp.sendRedirect(richiestaHttp.getContextPath() + "/login");
    }
}