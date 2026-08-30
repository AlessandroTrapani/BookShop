<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Utente" %>

<%
    String errore = (String) request.getAttribute("errore");
    String email = (String) request.getAttribute("email");
    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

    if (email == null) {
        email = "";
    }
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Login - BookShop</title>
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
                    if (utenteLoggato != null) {
                %>

                    <a href="${pageContext.request.contextPath}/storico-ordini">I miei ordini</a>
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

        <h2>Login</h2>

        <%
            if (errore != null) {
        %>

            <p class="messaggio-errore"><%= errore %></p>

        <%
            }
        %>

        <form id="form-login" method="post" action="${pageContext.request.contextPath}/login" novalidate>

            <label for="email">Email</label>

            <input
                type="email"
                id="email"
                name="email"
                value="<%= email %>">

            <p id="errore-email-login" class="messaggio-errore-form"></p>

            <label for="password">Password</label>

            <input
                type="password"
                id="password"
                name="password">

            <p id="errore-password-login" class="messaggio-errore-form"></p>

            <button class="bottone" type="submit">
                Accedi
            </button>

        </form>

        <p>
            Non hai ancora un account?
            <a href="${pageContext.request.contextPath}/registrazione">Registrati</a>
        </p>

        <section class="box-info">
            <h3>Utenti di test</h3>
            <p><strong>Utente:</strong> utente@bookshop.it / utente</p>
            <p><strong>Admin:</strong> admin@bookshop.it / admin</p>
        </section>

    </main>

    <script src="${pageContext.request.contextPath}/scripts/validazione-login.js"></script>

</body>
</html>