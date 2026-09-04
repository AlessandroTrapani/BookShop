<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Utente" %>

<%
    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Admin - BookShop</title>
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

        <h2>Dashboard amministratore</h2>

        <%
            if (utenteLoggato != null) {
        %>

            <p>
                Benvenuto, <strong><%= utenteLoggato.getNome() %></strong>.
            </p>

        <%
            }
        %>

        <p>
            Da questa area è possibile gestire i prodotti del catalogo
            e gli ordini effettuati dagli utenti.
        </p>

        <section class="griglia-prodotti">
            <article class="card-prodotto">
                <h3>Gestione prodotti</h3>
                <p>
                    Visualizza, inserisce, modifica ed elimina logicamente
                    i prodotti presenti nel catalogo.
                </p>
                <a class="bottone" href="${pageContext.request.contextPath}/admin/prodotti">
                    Vai ai prodotti
                </a>
            </article>

            <article class="card-prodotto">
                <h3>Gestione ordini</h3>
                <p>
                    Visualizza gli ordini degli utenti, consulta i dettagli
                    e aggiorna lo stato degli ordini.
                </p>
                <a class="bottone" href="${pageContext.request.contextPath}/admin/ordini">
                    Vai agli ordini
                </a>
            </article>
        </section>

    </main>
</body>
</html>