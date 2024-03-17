var hide=true;
function showLimitField() {
    var limitField = document.getElementById("limitField");
    limitField.style.display = "block";
    hide=false; 
}

function hideLimitField() {
    var limitField = document.getElementById("limitField");
    limitField.style.display = "none";
    hide=true;
}

window.addEventListener('DOMContentLoaded', (event) => {
    clearInputFields();
});

function clearInputFields() {
    var inputFields = document.querySelectorAll('.add-user-input');
    inputFields.forEach(function(input) {
        input.value = '';
    });
}

function validateForm() {
    
    var title = document.getElementById("title").value.trim();
    var numOfMonths = document.getElementById("numOfMonths").value;
    var visitsLimit = document.getElementById("visitsLimit").value;
    var freezeLimit = document.getElementById("freezeLimit").value;
    var numOfInvitations = document.getElementById("numOfInvitations").value;
    var numOfInbodySessions = document.getElementById("numOfInbodySessions").value;
    var numOfPTSessions = document.getElementById("numOfPTSessions").value;
    var price = document.getElementById("price").value;

    
    var titleError = document.getElementById("titleError");
    var numOfMonthsError = document.getElementById("numOfMonthsError");
    var visitsLimitError = document.getElementById("visitsLimitError");
    var freezeLimitError = document.getElementById("freezeLimitError");
    var numOfInvitationsError = document.getElementById("numOfInvitationsError");
    var numOfInbodySessionsError = document.getElementById("numOfInbodySessionsError");
    var numOfPTSessionsError = document.getElementById("numOfPTSessionsError");
    var priceError = document.getElementById("priceError");

    
    titleError.innerHTML = "";
    numOfMonthsError.innerHTML = "";
    visitsLimitError.innerHTML = "";
    freezeLimitError.innerHTML = "";
    numOfInvitationsError.innerHTML = "";
    numOfInbodySessionsError.innerHTML = "";
    numOfPTSessionsError.innerHTML = "";
    priceError.innerHTML = "";

    var isValid = true;

    if (title === "") {
        titleError.innerHTML = "Please enter a Package Title";
        isValid = false;
    }
    if (numOfMonths <= 0) {
        numOfMonthsError.innerHTML = "Please enter a valid Number of Months";
        isValid = false;
    }

    if (visitsLimit < 0) {
        visitsLimitError.innerHTML = "Please enter a valid Visit Limit";
        isValid = false;
    }


    if (freezeLimit <= 0) {
        freezeLimitError.innerHTML = "Please enter a valid Freeze Limit";
        isValid = false;
    }
    if (numOfInvitations <= 0) {
        numOfInvitationsError.innerHTML = "Please enter a valid Number of Invitations";
        isValid = false;
    }
    if (numOfInbodySessions <= 0) {
        numOfInbodySessionsError.innerHTML = "Please enter a valid Number of Inbody Sessions";
        isValid = false;
    }
    if (numOfPTSessions <= 0) {
        numOfPTSessionsError.innerHTML = "Please enter a valid Number of PT Sessions";
        isValid = false;
    }
    if (price <= 0) {
        priceError.innerHTML = "Please enter a valid Price";
        isValid = false;
    }

    return isValid;
}