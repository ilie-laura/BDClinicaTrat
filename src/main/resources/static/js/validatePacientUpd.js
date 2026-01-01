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

// click în afara popupului
document.addEventListener("click", function (e) {
    const popup = document.getElementById("errorPopup");
    if (popup.style.display === "block" && !popup.contains(e.target)) {
        hidePopup();
    }
});


function validatePacientUpd() {

    const nume = document.getElementById("nume").value.trim();
    const prenume = document.getElementById("prenume").value.trim();
   // const sex = document.getElementById("sex").value;
  //  const cnp = document.getElementById("cnp").value.trim();

    // Nume
    if (nume.length < 2 || nume.length>10) {
        showError("Numele trebuie să conțină minim 2 caractere si maxim 10! ");
        return false;
    }

    if (!onlyLetters(nume)) {
        showError("Numele poate conține doar litere");
        return false;
    }

    // Prenume
    if (prenume.length < 2 || prenume.length>10) {
        showError("Prenumele trebuie să conțină minim 2 caractere");
        return false;
    }

    if (!onlyLetters(prenume)) {
        showError("Prenumele poate conține doar litere");
        return false;
    }


    return true; // formular valid
}

function onlyLetters(text) {
    for (let i = 0; i < text.length; i++) {
        const c = text[i].toLowerCase();
        if (c < 'a' || c > 'z') {
            return false;
        }
    }
    return true;
}
