document.addEventListener("DOMContentLoaded", function () {
    const formRegistrazione = document.getElementById("form-registrazione");
    const campoNome = document.getElementById("nome");
    const campoCognome = document.getElementById("cognome");
    const campoEmail = document.getElementById("email");
    const campoPassword = document.getElementById("password");
    const campoConfermaPassword = document.getElementById("confermaPassword");

    const erroreNome = document.getElementById("errore-nome-registrazione");
    const erroreCognome = document.getElementById("errore-cognome-registrazione");
    const erroreEmail = document.getElementById("errore-email-registrazione");
    const errorePassword = document.getElementById("errore-password-registrazione");
    const erroreConfermaPassword = document.getElementById("errore-conferma-password-registrazione");

    let emailDisponibile = false;

    const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const regexNome = /^[A-Za-zÀ-ÿ' -]+$/;

    function mostraErrore(campo, elementoErrore, messaggio) {
        elementoErrore.textContent = messaggio;
        campo.classList.add("input-errore");
    }

    function rimuoviErrore(campo, elementoErrore) {
        elementoErrore.textContent = "";
        campo.classList.remove("input-errore");
    }

    function validaNome() {
        const nome = campoNome.value.trim();

        if (nome === "") {
            mostraErrore(campoNome, erroreNome, "Il nome è obbligatorio.");
            return false;
        }

        if (nome.length < 2) {
            mostraErrore(campoNome, erroreNome, "Il nome deve contenere almeno 2 caratteri.");
            return false;
        }

        if (!regexNome.test(nome)) {
            mostraErrore(campoNome, erroreNome, "Il nome può contenere solo lettere.");
            return false;
        }

        rimuoviErrore(campoNome, erroreNome);
        return true;
    }

    function validaCognome() {
        const cognome = campoCognome.value.trim();

        if (cognome === "") {
            mostraErrore(campoCognome, erroreCognome, "Il cognome è obbligatorio.");
            return false;
        }

        if (cognome.length < 2) {
            mostraErrore(campoCognome, erroreCognome, "Il cognome deve contenere almeno 2 caratteri.");
            return false;
        }

        if (!regexNome.test(cognome)) {
            mostraErrore(campoCognome, erroreCognome, "Il cognome può contenere solo lettere.");
            return false;
        }

        rimuoviErrore(campoCognome, erroreCognome);
        return true;
    }

    function validaFormatoEmail() {
        const emailInserita = campoEmail.value.trim();

        if (emailInserita === "") {
            mostraErrore(campoEmail, erroreEmail, "L'email è obbligatoria.");
            emailDisponibile = false;
            return false;
        }

        if (!regexEmail.test(emailInserita)) {
            mostraErrore(campoEmail, erroreEmail, "Inserisci un indirizzo email valido.");
            emailDisponibile = false;
            return false;
        }

        rimuoviErrore(campoEmail, erroreEmail);
        return true;
    }

    function verificaEmailAjax() {
        const emailInserita = campoEmail.value.trim();

        if (!validaFormatoEmail()) {
            return;
        }

        fetch("verifica-email?email=" + encodeURIComponent(emailInserita))
            .then(function (response) {
                return response.json();
            })
            .then(function (dati) {
                if (dati.valida) {
                    emailDisponibile = true;
                    rimuoviErrore(campoEmail, erroreEmail);
                } else {
                    emailDisponibile = false;
                    mostraErrore(campoEmail, erroreEmail, dati.messaggio);
                }
            })
            .catch(function () {
                emailDisponibile = false;
                mostraErrore(campoEmail, erroreEmail, "Errore durante la verifica dell'email.");
            });
    }

    function validaPassword() {
        const password = campoPassword.value.trim();

        if (password === "") {
            mostraErrore(campoPassword, errorePassword, "La password è obbligatoria.");
            return false;
        }

        if (password.length < 5) {
            mostraErrore(campoPassword, errorePassword, "La password deve contenere almeno 5 caratteri.");
            return false;
        }

        rimuoviErrore(campoPassword, errorePassword);
        return true;
    }

    function validaConfermaPassword() {
        const password = campoPassword.value.trim();
        const confermaPassword = campoConfermaPassword.value.trim();

        if (confermaPassword === "") {
            mostraErrore(campoConfermaPassword, erroreConfermaPassword, "La conferma password è obbligatoria.");
            return false;
        }

        if (password !== confermaPassword) {
            mostraErrore(campoConfermaPassword, erroreConfermaPassword, "Le password non coincidono.");
            return false;
        }

        rimuoviErrore(campoConfermaPassword, erroreConfermaPassword);
        return true;
    }

    campoNome.addEventListener("blur", validaNome);
    campoCognome.addEventListener("blur", validaCognome);
    campoEmail.addEventListener("blur", verificaEmailAjax);

    campoPassword.addEventListener("blur", function () {
        validaPassword();

        if (campoConfermaPassword.value.trim() !== "") {
            validaConfermaPassword();
        }
    });

    campoConfermaPassword.addEventListener("blur", validaConfermaPassword);

    campoEmail.addEventListener("input", function () {
        emailDisponibile = false;
    });

    formRegistrazione.addEventListener("submit", function (event) {
        const nomeValido = validaNome();
        const cognomeValido = validaCognome();
        const emailFormatoValido = validaFormatoEmail();
        const passwordValida = validaPassword();
        const confermaPasswordValida = validaConfermaPassword();

        if (!nomeValido
                || !cognomeValido
                || !emailFormatoValido
                || !emailDisponibile
                || !passwordValida
                || !confermaPasswordValida) {

            event.preventDefault();

            if (emailFormatoValido && !emailDisponibile) {
                verificaEmailAjax();
            }
        }
    });
});