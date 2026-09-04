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

@WebFilter("/admin/*")
public class FiltroAdmin implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest richiestaHttp = (HttpServletRequest) request;
        HttpServletResponse rispostaHttp = (HttpServletResponse) response;

        HttpSession sessione = richiestaHttp.getSession(false);

        boolean adminAutorizzato = false;

        if (sessione != null
                && sessione.getAttribute("utenteLoggato") != null
                && sessione.getAttribute("tokenAccesso") != null) {

            Utente utenteLoggato = (Utente) sessione.getAttribute("utenteLoggato");

            if ("ADMIN".equals(utenteLoggato.getRuolo())) {
                adminAutorizzato = true;
            }
        }

        if (adminAutorizzato) {
            chain.doFilter(request, response);
        } else {
            rispostaHttp.sendRedirect(richiestaHttp.getContextPath() + "/home");
        }
    }
}