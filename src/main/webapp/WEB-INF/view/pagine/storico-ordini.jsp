<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Ordine" %>
<%@ page import="model.Utente" %>

<%
    ArrayList<Ordine> ordini = (ArrayList<Ordine>) request.getAttribute("ordini");
    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
    String messaggioSuccesso = (String) session.getAttribute("messaggioSuccesso");
    session.removeAttribute("messaggioSuccesso");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Storico ordini - BookShop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/stile.css">
</head>
<body>

    <header class="intestazione-sito">
        <div class="contenitore">
            <h1>BookShop</h1>

            <nav class="menu-principale">
                <a href="${pageContext.request.contextPath}/home">Home</a>
                <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
                <a href="${pageContext.request.contextPath}/carrello">Carrello</a>
                <a href="${pageContext.request.contextPath}/storico-ordini">I miei ordini</a>

                <%
                    if (utenteLoggato != null) {
                %>

                    <a href="${pageContext.request.contextPath}/logout">Logout</a>

                <%
                    } else {
                %>

                    <a href="${pageContext.request.contextPath}/login">Login</a>

                <%
                    }
                %>
            </nav>
        </div>
    </header>

    <main class="contenitore contenuto-pagina">

        <h2>I miei ordini</h2>

        <%
            if (messaggioSuccesso != null) {
        %>

            <p class="messaggio-successo"><%= messaggioSuccesso %></p>

        <%
            }
        %>

        <%
            if (ordini == null || ordini.isEmpty()) {
        %>

            <p>Non hai ancora effettuato ordini.</p>

            <a class="bottone" href="${pageContext.request.contextPath}/catalogo">
                Vai al catalogo
            </a>

        <%
            } else {
        %>

            <table class="tabella-carrello">
                <thead>
                    <tr>
                        <th>ID ordine</th>
                        <th>Data</th>
                        <th>Totale</th>
                        <th>Stato</th>
                        <th>Azioni</th>
                    </tr>
                </thead>

                <tbody>
                    <%
                        for (Ordine ordine : ordini) {
                    %>

                        <tr>
                            <td>#<%= ordine.getId() %></td>
                            <td><%= ordine.getDataOrdine() %></td>
                            <td>€ <%= ordine.getTotale() %></td>
                            <td><%= ordine.getStato() %></td>
                            <td>
                                <a class="bottone" href="${pageContext.request.contextPath}/dettaglio-ordine?id=<%= ordine.getId() %>">
                                    Dettaglio
                                </a>
                            </td>
                        </tr>

                    <%
                        }
                    %>
                </tbody>
            </table>

        <%
            }
        %>

    </main>

</body>
</html>