<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Utente" %>

<%
    String errore = (String) request.getAttribute("errore");
    String titolo = (String) request.getAttribute("titolo");
    String autore = (String) request.getAttribute("autore");
    String categoria = (String) request.getAttribute("categoria");
    String prezzo = (String) request.getAttribute("prezzo");
    String quantita = (String) request.getAttribute("quantita");
    String immagine = (String) request.getAttribute("immagine");
    String descrizione = (String) request.getAttribute("descrizione");
    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

    if (titolo == null) {
        titolo = "";
    }

    if (autore == null) {
        autore = "";
    }

    if (categoria == null) {
        categoria = "";
    }

    if (prezzo == null) {
        prezzo = "";
    }

    if (quantita == null) {
        quantita = "";
    }

    if (immagine == null) {
        immagine = "";
    }

    if (descrizione == null) {
        descrizione = "";
    }
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Nuovo prodotto - BookShop</title>
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

        <h2>Nuovo prodotto</h2>

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

        <form id="form-prodotto-admin" method="post" action="${pageContext.request.contextPath}/admin/nuovo-prodotto" novalidate>
            <label for="titolo">Titolo libro</label>
            <input 
                type="text" 
                id="titolo" 
                name="titolo" 
                value="<%= titolo %>">
            <p id="errore-titolo-prodotto" class="messaggio-errore-form"></p>

            <label for="autore">Autore</label>
            <input 
                type="text" 
                id="autore" 
                name="autore" 
                value="<%= autore %>">
            <p id="errore-autore-prodotto" class="messaggio-errore-form"></p>

            <label for="categoria">Categoria</label>
            <select id="categoria" name="categoria">
                <option value="">Seleziona</option>
                <option value="Fantasy" <%= "Fantasy".equals(categoria) ? "selected" : "" %>>Fantasy</option>
                <option value="Giallo" <%= "Giallo".equals(categoria) ? "selected" : "" %>>Giallo</option>
                <option value="Distopico" <%= "Distopico".equals(categoria) ? "selected" : "" %>>Distopico</option>
                <option value="Informatica" <%= "Informatica".equals(categoria) ? "selected" : "" %>>Informatica</option>
                <option value="Manga" <%= "Manga".equals(categoria) ? "selected" : "" %>>Manga</option>
            </select>
            <p id="errore-categoria-prodotto" class="messaggio-errore-form"></p>

            <label for="prezzo">Prezzo</label>
            <input 
                type="text" 
                id="prezzo" 
                name="prezzo" 
                value="<%= prezzo %>">
            <p id="errore-prezzo-prodotto" class="messaggio-errore-form"></p>

            <label for="quantita">Quantità</label>
            <input 
                type="number" 
                id="quantita" 
                name="quantita" 
                value="<%= quantita %>"
                min="0">
            <p id="errore-quantita-prodotto" class="messaggio-errore-form"></p>

            <label for="immagine">Nome file immagine</label>
            <input 
                type="text" 
                id="immagine" 
                name="immagine" 
                value="<%= immagine %>"
                placeholder="es. libro-fantasy.jpg">

            <label for="descrizione">Descrizione</label>
            <textarea 
                id="descrizione" 
                name="descrizione" 
                rows="5"><%= descrizione %></textarea>

            <br><br>

            <button class="bottone" type="submit">
                Salva prodotto
            </button>

            <a class="bottone" href="${pageContext.request.contextPath}/admin/prodotti">
                Annulla
            </a>
        </form>

    </main>

    <script src="${pageContext.request.contextPath}/scripts/validazione-prodotto-admin.js"></script>
</body>
</html>