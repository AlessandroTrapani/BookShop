<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Utente" %>

<%
    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>BookShop - Home</title>
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

        <h2>Benvenuto su BookShop</h2>

        <%
            if (utenteLoggato != null) {
        %>

            <p>
                Ciao <strong><%= utenteLoggato.getNome() %></strong>, sei connesso a BookShop.
            </p>

        <%
            }
        %>

        <p>
            BookShop è un e-commerce dedicato alla vendita di libri:
            romanzi, manuali, manga e testi di informatica.
        </p>

        <a class="bottone" href="${pageContext.request.contextPath}/catalogo">
            Vai al catalogo
        </a>

    </main>

</body>
</html>