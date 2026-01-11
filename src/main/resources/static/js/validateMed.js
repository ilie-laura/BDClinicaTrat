

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





function validateMed() {

    const nume = document.getElementById("nume").value.trim();
   const stoc=document.getElementById("stoc").value.trim();
    const pret=document.getElementById("pret").value.trim();

    if (nume.length < 2 || nume.length > 50) {
        showError("Numele trebuie să aibă între 2 și 50 caractere");
        return false;
    }

    if (!onlyLetters(nume)) {
        showError("Numele poate conține doar litere");
        return false;
    }



    if (!isPositiveInteger(stoc)) {
        showError("Stocul trebuie să fie un număr întreg pozitiv");
        return false;
    }


    const stoc2 = parseInt(stoc, 10);

    if (stoc2 <= 0) {
        showError("Stocul trebuie să fie mai mare decât 0");
        return false;
    }
   const pret2=parseFloat(pret);
    if(pret2<=0){
        showError("Pretul trebuie sa fie > 0");
        return false;
    }
    return true; // formular valid
}
