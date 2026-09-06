<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Prodotto" %>
<%@ page import="model.Utente" %>

<%
    Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
    String errore = (String) request.getAttribute("errore");
    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Modifica prodotto - BookShop</title>
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

        <h2>Modifica prodotto</h2>

        <%
            if (errore != null) {
        %>

            <p class="messaggio-errore"><%= errore %></p>

        <%
            }
        %>

        <%
            if (utenteLoggato != null) {
        %>

            <p>Admin: <strong><%= utenteLoggato.getEmail() %></strong></p>

        <%
            }
        %>

        <%
            if (prodotto == null) {
        %>

            <a class="bottone" href="${pageContext.request.contextPath}/admin/prodotti">
                Torna ai prodotti
            </a>

        <%
            } else {
        %>

            <form id="form-prodotto-admin" method="post" action="${pageContext.request.contextPath}/admin/modifica-prodotto" novalidate>
                <input type="hidden" name="id" value="<%= prodotto.getId() %>">

                <label for="titolo">Titolo libro</label>
                <input 
                    type="text" 
                    id="titolo" 
                    name="titolo" 
                    value="<%= prodotto.getTitolo() %>">
                <p id="errore-titolo-prodotto" class="messaggio-errore-form"></p>

                <label for="autore">Autore</label>
                <input 
                    type="text" 
                    id="autore" 
                    name="autore" 
                    value="<%= prodotto.getAutore() %>">
                <p id="errore-autore-prodotto" class="messaggio-errore-form"></p>

                <label for="categoria">Categoria</label>
                <select id="categoria" name="categoria">
                    <option value="">Seleziona</option>
                    <option value="Fantasy" <%= "Fantasy".equals(prodotto.getCategoria()) ? "selected" : "" %>>Fantasy</option>
                    <option value="Giallo" <%= "Giallo".equals(prodotto.getCategoria()) ? "selected" : "" %>>Giallo</option>
                    <option value="Distopico" <%= "Distopico".equals(prodotto.getCategoria()) ? "selected" : "" %>>Distopico</option>
                    <option value="Informatica" <%= "Informatica".equals(prodotto.getCategoria()) ? "selected" : "" %>>Informatica</option>
                    <option value="Manga" <%= "Manga".equals(prodotto.getCategoria()) ? "selected" : "" %>>Manga</option>
                </select>
                <p id="errore-categoria-prodotto" class="messaggio-errore-form"></p>

                <label for="prezzo">Prezzo</label>
                <input 
                    type="text" 
                    id="prezzo" 
                    name="prezzo" 
                    value="<%= prodotto.getPrezzo() %>">
                <p id="errore-prezzo-prodotto" class="messaggio-errore-form"></p>

                <label for="quantita">Quantità</label>
                <input 
                    type="number" 
                    id="quantita" 
                    name="quantita" 
                    value="<%= prodotto.getQuantita() %>"
                    min="0">
                <p id="errore-quantita-prodotto" class="messaggio-errore-form"></p>

                <label for="immagine">Nome file immagine</label>
                <input 
                    type="text" 
                    id="immagine" 
                    name="immagine" 
                    value="<%= prodotto.getImmagine() != null ? prodotto.getImmagine() : "" %>"
                    placeholder="es. libro-fantasy.jpg">

                <label for="descrizione">Descrizione</label>
                <textarea 
                    id="descrizione" 
                    name="descrizione" 
                    rows="5"><%= prodotto.getDescrizione() != null ? prodotto.getDescrizione() : "" %></textarea>

                <br><br>

                <button class="bottone" type="submit">
                    Salva modifiche
                </button>

                <a class="bottone" href="${pageContext.request.contextPath}/admin/prodotti">
                    Annulla
                </a>
            </form>

        <%
            }
        %>

    </main>

    <script src="${pageContext.request.contextPath}/scripts/validazione-prodotto-admin.js"></script>
</body>
</html>