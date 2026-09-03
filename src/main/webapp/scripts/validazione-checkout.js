document.addEventListener("DOMContentLoaded", function () {
    const formCheckout = document.getElementById("form-checkout");
    const campoEmail = document.getElementById("emailConsegna");
    const campoIndirizzo = document.getElementById("indirizzoSpedizione");
    const campoMetodoPagamento = document.getElementById("metodoPagamento");
    const sezioneCarta = document.getElementById("sezione-carta");
    const messaggioContanti = document.getElementById("messaggio-contanti");
    const campoNumeroCarta = document.getElementById("numeroCarta");
    const campoCvv = document.getElementById("cvv");
    const erroreEmail = document.getElementById("errore-email-checkout");
    const erroreIndirizzo = document.getElementById("errore-indirizzo-checkout");
    const erroreMetodo = document.getElementById("errore-metodo-checkout");
    const erroreNumeroCarta = document.getElementById("errore-numero-carta");
    const erroreCvv = document.getElementById("errore-cvv");

    const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const regexNumeroCarta = /^\d{12,19}$/;
    const regexCvv = /^\d{3}$/;

    function mostraErrore(campo, elementoErrore, messaggio) {
        elementoErrore.textContent = messaggio;
        campo.classList.add("input-errore");
    }

    function rimuoviErrore(campo, elementoErrore) {
        elementoErrore.textContent = "";
        campo.classList.remove("input-errore");
    }

    function validaEmail() {
        const email = campoEmail.value.trim();

        if (email === "") {
            mostraErrore(campoEmail, erroreEmail, "L'email è obbligatoria.");
            return false;
        }

        if (!regexEmail.test(email)) {
            mostraErrore(campoEmail, erroreEmail, "Inserisci un indirizzo email valido.");
            return false;
        }

        rimuoviErrore(campoEmail, erroreEmail);
        return true;
    }

    function validaIndirizzo() {
        const indirizzo = campoIndirizzo.value.trim();

        if (indirizzo === "") {
            mostraErrore(campoIndirizzo, erroreIndirizzo, "L'indirizzo di spedizione è obbligatorio.");
            return false;
        }

        rimuoviErrore(campoIndirizzo, erroreIndirizzo);
        return true;
    }

    function validaMetodoPagamento() {
        const metodo = campoMetodoPagamento.value;

        if (metodo === "") {
            mostraErrore(campoMetodoPagamento, erroreMetodo, "Seleziona un metodo di pagamento.");
            return false;
        }

        rimuoviErrore(campoMetodoPagamento, erroreMetodo);
        return true;
    }

    function validaDatiCarta() {
        const metodo = campoMetodoPagamento.value;

        if (metodo !== "Carta") {
            rimuoviErrore(campoNumeroCarta, erroreNumeroCarta);
            rimuoviErrore(campoCvv, erroreCvv);
            return true;
        }

        let datiCartaValidi = true;

        const numeroCarta = campoNumeroCarta.value.trim();
        const cvv = campoCvv.value.trim();

        if (numeroCarta === "") {
            mostraErrore(campoNumeroCarta, erroreNumeroCarta, "Il numero carta è obbligatorio.");
            datiCartaValidi = false;
        } else if (!regexNumeroCarta.test(numeroCarta)) {
            mostraErrore(campoNumeroCarta, erroreNumeroCarta, "Il numero carta deve contenere da 12 a 19 cifre.");
            datiCartaValidi = false;
        } else {
            rimuoviErrore(campoNumeroCarta, erroreNumeroCarta);
        }

        if (cvv === "") {
            mostraErrore(campoCvv, erroreCvv, "Il CVV è obbligatorio.");
            datiCartaValidi = false;
        } else if (!regexCvv.test(cvv)) {
            mostraErrore(campoCvv, erroreCvv, "Il CVV deve contenere 3 cifre.");
            datiCartaValidi = false;
        } else {
            rimuoviErrore(campoCvv, erroreCvv);
        }

        return datiCartaValidi;
    }

    function aggiornaMetodoPagamento() {
        const metodo = campoMetodoPagamento.value;

        if (metodo === "Carta") {
            sezioneCarta.style.display = "block";
            messaggioContanti.style.display = "none";
        } else if (metodo === "Contanti") {
            sezioneCarta.style.display = "none";
            messaggioContanti.style.display = "block";
            rimuoviErrore(campoNumeroCarta, erroreNumeroCarta);
            rimuoviErrore(campoCvv, erroreCvv);
        } else {
            sezioneCarta.style.display = "none";
            messaggioContanti.style.display = "none";
        }
    }

    campoEmail.addEventListener("blur", function () {
        validaEmail();
    });

    campoIndirizzo.addEventListener("blur", function () {
        validaIndirizzo();
    });

    campoNumeroCarta.addEventListener("blur", function () {
        validaDatiCarta();
    });

    campoCvv.addEventListener("blur", function () {
        validaDatiCarta();
    });

    campoMetodoPagamento.addEventListener("change", function () {
        aggiornaMetodoPagamento();
        validaMetodoPagamento();
    });

    aggiornaMetodoPagamento();

    formCheckout.addEventListener("submit", function (event) {
        const emailValida = validaEmail();
        const indirizzoValido = validaIndirizzo();
        const metodoValido = validaMetodoPagamento();
        const cartaValida = validaDatiCarta();

        if (!emailValida || !indirizzoValido || !metodoValido || !cartaValida) {
            event.preventDefault();
        }
    });
});