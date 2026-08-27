<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Prodotto" %>
<%@ page import="model.Utente" %>


<%
    ArrayList<Prodotto> prodotti =
            (ArrayList<Prodotto>) request.getAttribute("prodotti");

    String ricerca = (String) request.getAttribute("ricerca");
    String autore = (String) request.getAttribute("autore");
    String categoria = (String) request.getAttribute("categoria");

    Utente utenteLoggato =
            (Utente) session.getAttribute("utenteLoggato");

    if (ricerca == null) {
        ricerca = "";
    }

    if (autore == null) {
        autore = "";
    }

    if (categoria == null) {
        categoria = "";
    }
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Catalogo - BookShop</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/styles/stile.css">
</head>

<body>

    <header class="intestazione-sito">
        <div class="contenitore">
            <h1>BookShop</h1>

            <nav class="menu-principale">
                <a href="${pageContext.request.contextPath}/home">
                    Home
                </a>

                <a href="${pageContext.request.contextPath}/catalogo">
                    Catalogo
                </a>

                <a href="${pageContext.request.contextPath}/carrello">
                    Carrello
                </a>

                <%
                    if (utenteLoggato == null) {
                %>

                    <a href="${pageContext.request.contextPath}/login">
                        Login
                    </a>

                <%
                    } else if ("ADMIN".equals(utenteLoggato.getRuolo())) {
                %>

                    <a href="${pageContext.request.contextPath}/admin/home">
                        Area admin
                    </a>

                    <a href="${pageContext.request.contextPath}/logout">
                        Logout
                    </a>

                <%
                    } else {
                %>

                    <a href="${pageContext.request.contextPath}/storico-ordini">
                        I miei ordini
                    </a>

                    <a href="${pageContext.request.contextPath}/logout">
                        Logout
                    </a>

                <%
                    }
                %>
            </nav>
        </div>
    </header>

    <main class="contenitore contenuto-pagina">

        <h2>Catalogo libri</h2>

        <form method="get"
              action="${pageContext.request.contextPath}/catalogo">

            <label for="ricerca">Cerca libro</label>

            <input
                type="text"
                id="ricerca"
                name="ricerca"
                value="<%= ricerca %>"
                placeholder="Es. Harry Potter">

            <label for="autore">Autore</label>

            <input
                type="text"
                id="autore"
                name="autore"
                value="<%= autore %>"
                placeholder="Es. J.K. Rowling">

            <label for="categoria">Categoria</label>

            <select id="categoria" name="categoria">
                <option value="">Tutte</option>

                <option value="Fantasy"
                    <%= "Fantasy".equals(categoria) ? "selected" : "" %>>
                    Fantasy
                </option>

                <option value="Giallo"
                    <%= "Giallo".equals(categoria) ? "selected" : "" %>>
                    Giallo
                </option>

                <option value="Distopico"
                    <%= "Distopico".equals(categoria) ? "selected" : "" %>>
                    Distopico
                </option>

                <option value="Informatica"
                    <%= "Informatica".equals(categoria) ? "selected" : "" %>>
                    Informatica
                </option>

                <option value="Manga"
                    <%= "Manga".equals(categoria) ? "selected" : "" %>>
                    Manga
                </option>
            </select>

            <br><br>

            <button class="bottone" type="submit">
                Filtra
            </button>

            <a class="bottone"
               href="${pageContext.request.contextPath}/catalogo">
                Reset
            </a>

        </form>

        <hr>

        <%
            if (prodotti == null || prodotti.isEmpty()) {
        %>

            <p>Nessun libro trovato.</p>

        <%
            } else {
        %>

            <section class="griglia-prodotti">

                <%
                    for (Prodotto prodotto : prodotti) {
                %>

                    <article class="card-prodotto">

                        <%
                            String immagineProdotto =
                                    prodotto.getImmagine();

                            if (immagineProdotto == null
                                    || immagineProdotto.trim().equals("")) {
                                immagineProdotto = "placeholder.jpg";
                            }
                        %>

                        <img
                            class="immagine-card-prodotto"
                            src="${pageContext.request.contextPath}/images/prodotti/<%= immagineProdotto %>"
                            alt="<%= prodotto.getTitolo() %>">

                        <h3>
                            <%= prodotto.getTitolo() %>
                        </h3>

                        <p>
                            <strong>Autore:</strong>
                            <%= prodotto.getAutore() %>
                        </p>

                        <p>
                            <strong>Categoria:</strong>
                            <%= prodotto.getCategoria() %>
                        </p>

                        <p>
                            <strong>Prezzo:</strong>
                            € <%= String.format("%.2f", prodotto.getPrezzo()) %>
                        </p>

                        <p>
                            <strong>Disponibili:</strong>
                            <%= prodotto.getQuantita() %>
                        </p>

                        <a class="bottone"
                           href="${pageContext.request.contextPath}/dettaglio-prodotto?id=<%= prodotto.getId() %>">
                            Dettaglio
                        </a>

                    </article>

                <%
                    }
                %>

            </section>

        <%
            }
        %>

    </main>

</body>
</html>