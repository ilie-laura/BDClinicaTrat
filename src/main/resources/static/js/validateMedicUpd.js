
function showError(message) {
    const popup = document.getElementById("errorPopup");
    const overlay = document.getElementById("errorOverlay");
    const msg = document.getElementById("errorMessage");

    msg.textContent = message;
    popup.style.display = "block";
    overlay.style.display = "block";
}

function hidePopup() {
    document.getElementById("errorPopup").style.display = "none";
    document.getElementById("errorOverlay").style.display = "none";
}

document.addEventListener("click", function (e) {
    const popup = document.getElementById("errorPopup");
    if (popup.style.display === "block" && !popup.contains(e.target)) {
        hidePopup();
    }
});



function onlyLetters(text) {
    if (text.length === 0) return false;

    for (let i = 0; i < text.length; i++) {
        const c = text[i].toLowerCase();
        if (c < 'a' || c > 'z') {
            return false;
        }
    }
    return true;
}

function isPositiveInteger(text) {
    if (text.length === 0) return false;

    for (let i = 0; i < text.length; i++) {
        if (text[i] < '0' || text[i] > '9') {
            return false;
        }
    }
    return true;
}



function validateMedicUpd() {

    const nume = document.getElementById("nume").value.trim();
    const prenume = document.getElementById("prenume").value.trim();
    const salariuText = document.getElementById("salariu").value.trim();
    const specializare = document.getElementById("specializare").value.trim();

    // NUME
    if (nume.length < 2 || nume.length > 10) {
        showError("Numele trebuie să aibă între 2 și 10 caractere");
        return false;
    }

    if (!onlyLetters(nume)) {
        showError("Numele poate conține doar litere");
        return false;
    }


    if (prenume.length < 2 || prenume.length > 10) {
        showError("Prenumele trebuie să aibă între 2 și 10 caractere");
        return false;
    }

    if (!onlyLetters(prenume)) {
        showError("Prenumele poate conține doar litere");
        return false;
    }


    if (specializare.length < 2 || !onlyLetters(specializare)) {
        showError("Specializarea trebuie să conțină minim 2 litere");
        return false;
    }


    if (!isPositiveInteger(salariuText)) {
        showError("Salariul trebuie să fie un număr întreg pozitiv");
        return false;
    }


    const salariu = parseInt(salariuText, 10);

    if (salariu <= 0) {
        showError("Salariul trebuie să fie mai mare decât 0");
        return false;
    }

    return true; // formular valid
}
