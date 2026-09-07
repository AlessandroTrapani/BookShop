<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Ordine" %>
<%@ page import="model.Utente" %>

<%
    ArrayList<Ordine> ordini = (ArrayList<Ordine>) request.getAttribute("ordini");
    String errore = (String) request.getAttribute("errore");
    String dataInizio = (String) request.getAttribute("dataInizio");
    String dataFine = (String) request.getAttribute("dataFine");
    String idUtente = (String) request.getAttribute("idUtente");
    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

    if (dataInizio == null) {
        dataInizio = "";
    }

    if (dataFine == null) {
        dataFine = "";
    }

    if (idUtente == null) {
        idUtente = "";
    }
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Gestione ordini - BookShop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/stile.css">
</head>
<body>
    <header class="intestazione-sito">
        <div class="contenitore">
            <h1>BookShop - Area Admin</h1>

            <nav class="menu-principale">
                <a href="${pageContext.request.contextPath}/admin/home">Dashboard</a>
                <a href="${pageContext.request.contextPath}/admin/prodotti">Prodotti</a>
                <a href="${pageContext.request.contextPath}/admin/ordini">Ordini</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </nav>
        </div>
    </header>

    <main class="contenitore contenuto-pagina">

        <h2>Gestione ordini</h2>

        <%
            if (utenteLoggato != null) {
        %>

            <p>
                Amministratore: <strong><%= utenteLoggato.getEmail() %></strong>
            </p>

        <%
            }
        %>

        <%
            if (errore != null) {
        %>

            <p class="messaggio-errore"><%= errore %></p>

        <%
            }
        %>

        <section class="box-info">
            <h3>Filtra ordini</h3>

            <form method="get" action="${pageContext.request.contextPath}/admin/ordini">
                <label for="dataInizio">Data inizio</label>
                <input 
                    type="date" 
                    id="dataInizio" 
                    name="dataInizio" 
                    value="<%= dataInizio %>">

                <label for="dataFine">Data fine</label>
                <input 
                    type="date" 
                    id="dataFine" 
                    name="dataFine" 
                    value="<%= dataFine %>">

                <label for="idUtente">ID cliente</label>
                <input 
                    type="number" 
                    id="idUtente" 
                    name="idUtente" 
                    value="<%= idUtente %>"
                    min="1"
                    placeholder="Es. 2">

                <br><br>

                <button class="bottone" type="submit">
                    Filtra
                </button>

                <a class="bottone" href="${pageContext.request.contextPath}/admin/ordini">
                    Reset
                </a>
            </form>
        </section>

        <%
            if (ordini == null || ordini.isEmpty()) {
        %>

            <p>Nessun ordine presente.</p>

        <%
            } else {
        %>

            <table class="tabella-carrello">
                <thead>
                    <tr>
                        <th>ID ordine</th>
                        <th>ID cliente</th>
                        <th>Data</th>
                        <th>Email consegna</th>
                        <th>Totale</th>
                        <th>Metodo pagamento</th>
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
                            <td><%= ordine.getIdUtente() %></td>
                            <td><%= ordine.getDataOrdine() %></td>
                            <td><%= ordine.getEmailConsegna() %></td>
                            <td>€ <%= ordine.getTotale() %></td>
                            <td><%= ordine.getMetodoPagamento() %></td>
                            <td><%= ordine.getStato() %></td>
                            <td>
                                <a class="bottone" href="${pageContext.request.contextPath}/admin/dettaglio-ordine?id=<%= ordine.getId() %>">
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