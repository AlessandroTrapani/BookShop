document.addEventListener("DOMContentLoaded", function () {

    const formProdotto = document.getElementById("form-prodotto-admin");

    if (formProdotto == null) {
        return;
    }

    const campoTitolo = document.getElementById("titolo");
    const campoAutore = document.getElementById("autore");
    const campoCategoria = document.getElementById("categoria");
    const campoPrezzo = document.getElementById("prezzo");
    const campoQuantita = document.getElementById("quantita");

    const erroreTitolo = document.getElementById("errore-titolo-prodotto");
    const erroreAutore = document.getElementById("errore-autore-prodotto");
    const erroreCategoria = document.getElementById("errore-categoria-prodotto");
    const errorePrezzo = document.getElementById("errore-prezzo-prodotto");
    const erroreQuantita = document.getElementById("errore-quantita-prodotto");

    function mostraErrore(campo, elementoErrore, messaggio) {
        elementoErrore.textContent = messaggio;
        campo.classList.add("input-errore");
    }

    function rimuoviErrore(campo, elementoErrore) {
        elementoErrore.textContent = "";
        campo.classList.remove("input-errore");
    }

    function validaTitolo() {
        const titolo = campoTitolo.value.trim();

        if (titolo === "") {
            mostraErrore(campoTitolo, erroreTitolo, "Il titolo del libro è obbligatorio.");
            return false;
        }

        if (titolo.length < 2) {
            mostraErrore(campoTitolo, erroreTitolo, "Il titolo deve contenere almeno 2 caratteri.");
            return false;
        }

        rimuoviErrore(campoTitolo, erroreTitolo);
        return true;
    }

    function validaAutore() {
        const autore = campoAutore.value.trim();

        if (autore === "") {
            mostraErrore(campoAutore, erroreAutore, "L'autore è obbligatorio.");
            return false;
        }

        if (autore.length < 2) {
            mostraErrore(campoAutore, erroreAutore, "L'autore deve contenere almeno 2 caratteri.");
            return false;
        }

        rimuoviErrore(campoAutore, erroreAutore);
        return true;
    }

    function validaCategoria() {
        const categoria = campoCategoria.value;

        if (categoria === "") {
            mostraErrore(campoCategoria, erroreCategoria, "Seleziona una categoria.");
            return false;
        }

        rimuoviErrore(campoCategoria, erroreCategoria);
        return true;
    }

    function validaPrezzo() {
        const prezzoTesto = campoPrezzo.value.trim().replace(",", ".");
        const prezzo = parseFloat(prezzoTesto);

        if (prezzoTesto === "") {
            mostraErrore(campoPrezzo, errorePrezzo, "Il prezzo è obbligatorio.");
            return false;
        }

        if (isNaN(prezzo)) {
            mostraErrore(campoPrezzo, errorePrezzo, "Il prezzo deve essere un numero.");
            return false;
        }

        if (prezzo <= 0) {
            mostraErrore(campoPrezzo, errorePrezzo, "Il prezzo deve essere maggiore di zero.");
            return false;
        }

        rimuoviErrore(campoPrezzo, errorePrezzo);
        return true;
    }

    function validaQuantita() {
        const quantitaTesto = campoQuantita.value.trim();
        const quantita = parseInt(quantitaTesto, 10);

        if (quantitaTesto === "") {
            mostraErrore(campoQuantita, erroreQuantita, "La quantità è obbligatoria.");
            return false;
        }

        if (isNaN(quantita)) {
            mostraErrore(campoQuantita, erroreQuantita, "La quantità deve essere un numero intero.");
            return false;
        }

        if (quantita < 0) {
            mostraErrore(campoQuantita, erroreQuantita, "La quantità non può essere negativa.");
            return false;
        }

        rimuoviErrore(campoQuantita, erroreQuantita);
        return true;
    }

    campoTitolo.addEventListener("blur", validaTitolo);
    campoAutore.addEventListener("blur", validaAutore);
    campoCategoria.addEventListener("change", validaCategoria);
    campoPrezzo.addEventListener("blur", validaPrezzo);
    campoQuantita.addEventListener("blur", validaQuantita);

    formProdotto.addEventListener("submit", function (event) {
        const titoloValido = validaTitolo();
        const autoreValido = validaAutore();
        const categoriaValida = validaCategoria();
        const prezzoValido = validaPrezzo();
        const quantitaValida = validaQuantita();

        if (!titoloValido || !autoreValido || !categoriaValida || !prezzoValido || !quantitaValida) {
            event.preventDefault();
        }
    });
});