document.addEventListener("DOMContentLoaded", function () {
    const formLogin = document.getElementById("form-login");
    const campoEmail = document.getElementById("email");
    const campoPassword = document.getElementById("password");
    const erroreEmail = document.getElementById("errore-email-login");
    const errorePassword = document.getElementById("errore-password-login");

    const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    function validaEmail() {
        const emailInserita = campoEmail.value.trim();

        if (emailInserita === "") {
            erroreEmail.textContent = "L'email è obbligatoria.";
            campoEmail.classList.add("input-errore");
            return false;
        }

        if (!regexEmail.test(emailInserita)) {
            erroreEmail.textContent = "Inserisci un indirizzo email valido.";
            campoEmail.classList.add("input-errore");
            return false;
        }

        erroreEmail.textContent = "";
        campoEmail.classList.remove("input-errore");
        return true;
    }

    function validaPassword() {
        const passwordInserita = campoPassword.value.trim();

        if (passwordInserita === "") {
            errorePassword.textContent = "La password è obbligatoria.";
            campoPassword.classList.add("input-errore");
            return false;
        }

        errorePassword.textContent = "";
        campoPassword.classList.remove("input-errore");
        return true;
    }

    if (campoEmail != null) {
        campoEmail.addEventListener("blur", function () {
            validaEmail();
        });
    }

    if (campoPassword != null) {
        campoPassword.addEventListener("blur", function () {
            validaPassword();
        });
    }

    if (formLogin != null) {
        formLogin.addEventListener("submit", function (event) {
            const emailValida = validaEmail();
            const passwordValida = validaPassword();

            if (!emailValida || !passwordValida) {
                event.preventDefault();
            }
        });
    }
});