<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Prodotto" %>
<%@ page import="model.Utente" %>
<%@ page import="model.Carrello" %>

<%
    Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
    String errore = (String) request.getAttribute("errore");
    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
    Carrello carrello = (Carrello) session.getAttribute("carrello");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Dettaglio prodotto - BookShop</title>
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

                <%
                    if (utenteLoggato == null) {
                %>

                    <a href="${pageContext.request.contextPath}/login">Login</a>

                <%
                    } else if ("ADMIN".equals(utenteLoggato.getRuolo())) {
                %>

                    <a href="${pageContext.request.contextPath}/admin/home">Area admin</a>
                    <a href="${pageContext.request.contextPath}/logout">Logout</a>

                <%
                    } else {
                %>

                    <a href="${pageContext.request.contextPath}/storico-ordini">I miei ordini</a>
                    <a href="${pageContext.request.contextPath}/logout">Logout</a>

                <%
                    }
                %>
            </nav>
        </div>
    </header>

    <main class="contenitore contenuto-pagina">

        <%
            if (errore != null) {
        %>

            <h2>Errore</h2>

            <p class="messaggio-errore">
                <%= errore %>
            </p>

            <a class="bottone" href="${pageContext.request.contextPath}/catalogo">
                Torna al catalogo
            </a>

        <%
            } else if (prodotto != null) {
        %>

            <section class="dettaglio-prodotto">

                <%
                    String immagineProdotto = prodotto.getImmagine();

                    if (immagineProdotto == null || immagineProdotto.trim().equals("")) {
                        immagineProdotto = "placeholder.jpg";
                    }
                %>

                <img
                    class="immagine-prodotto"
                    src="${pageContext.request.contextPath}/images/prodotti/<%= immagineProdotto %>"
                    alt="<%= prodotto.getTitolo() %>">

                <div class="informazioni-prodotto">

                    <h2><%= prodotto.getTitolo() %></h2>

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
                        <strong>Quantità disponibile:</strong>
                        <%= prodotto.getQuantita() %>
                    </p>

                    <%
                        if (prodotto.getDescrizione() != null
                                && !prodotto.getDescrizione().trim().equals("")) {
                    %>

                        <p>
                            <%= prodotto.getDescrizione() %>
                        </p>

                    <%
                        }
                    %>

                    <%
                        int quantitaGiaNelCarrello = 0;

                        if (carrello != null) {
                            quantitaGiaNelCarrello =
                                    carrello.getQuantitaProdotto(prodotto.getId());
                        }

                        int quantitaAggiungibile =
                                prodotto.getQuantita() - quantitaGiaNelCarrello;
                    %>

                    <%
                        if (utenteLoggato != null
                                && "ADMIN".equals(utenteLoggato.getRuolo())) {
                    %>

                        <p class="box-info">
                            Stai visualizzando il prodotto come amministratore.
                            Per modificare il prodotto usa l'area admin.
                        </p>

                        <a class="bottone"
                           href="${pageContext.request.contextPath}/admin/modifica-prodotto?id=<%= prodotto.getId() %>">
                            Modifica in area admin
                        </a>

                    <%
                        } else if (quantitaAggiungibile > 0) {
                    %>

                        <form method="post" action="${pageContext.request.contextPath}/carrello">

                            <input type="hidden" name="azione" value="aggiungi">

                            <input type="hidden"
                                   name="idProdotto"
                                   value="<%= prodotto.getId() %>">

                            <label for="quantita">Quantità</label>

                            <input
                                type="number"
                                id="quantita"
                                name="quantita"
                                value="1"
                                min="1"
                                max="<%= quantitaAggiungibile %>">

                            <p>
                                Puoi aggiungere ancora massimo
                                <strong><%= quantitaAggiungibile %></strong>
                                copie.
                            </p>

                            <button class="bottone" type="submit">
                                Aggiungi al carrello
                            </button>

                        </form>

                    <%
                        } else {
                    %>

                        <p class="messaggio-errore">
                            Hai già aggiunto al carrello tutta la quantità disponibile per questo prodotto.
                        </p>

                    <%
                        }
                    %>

                    <br>

                    <a class="bottone" href="${pageContext.request.contextPath}/catalogo">
                        Torna al catalogo
                    </a>

                </div>

            </section>

        <%
            }
        %>

    </main>

</body>
</html>