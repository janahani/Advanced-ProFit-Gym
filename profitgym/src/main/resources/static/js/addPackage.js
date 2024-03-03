function showLimitField() {
    var limitField = document.getElementById("limitField");
    limitField.style.display = "block";
}

function hideLimitField() {
    var limitField = document.getElementById("limitField");
    limitField.style.display = "none";
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

    var isLimitedError = document.getElementById("isLimited-error");
    var limitError = document.getElementById("limit-error");
    var freezeLimitError = document.getElementById("freezeLimit-error");
    var titleError = document.getElementById("title-error");
    var monthsError = document.getElementById("months-error");
    var invitationsError = document.getElementById("invitations-error");
    var inbodyError = document.getElementById("inbody-error");
    var ptSessionError = document.getElementById("ptsession-error");
    var priceError = document.getElementById("price-error");

    let isValid = true;

    // Validate visits radio buttons
    var limited = document.getElementById("limited");
    var unlimited = document.getElementById("unlimited");
    var limitDays=document.getElementById("limitDays");
    var freezeLimit = document.getElementById("freezelimit");
    var months = document.getElementById("months");
    var title = document.getElementById("title");
    var invitation = document.getElementById("invitation");
    var inbody= document.getElementById("inbody");
    var ptSession = document.getElementById("ptsession");
    var price = document.getElementById("price");

    if (!limited.checked && !unlimited.checked) {
        isLimitedError.innerHTML="Visits is required";
        isValid=false;
    }

    if(limited.checked && limitDays.value.trim() === "")
    {
        limitError.innerHTML="Visit Limit is Required";
        isValid=false;
    }

    if(freezeLimit.value.trim() === "")
    {
        freezeLimitError.innerHTML="Freeze Limit is Required";
        isValid=false;
    }

    if(title.value.trim() === "")
    {
        titleError.innerHTML=" Package Title is Required";
        isValid=false;
    }

    if(months.value.trim() === "")
    {
        monthsError.innerHTML=" Number of Months is Required";
        isValid=false;
    }

    if(invitation.value.trim() === "")
    {
        invitationsError.innerHTML="Number of Invitations is Required";
        isValid=false;
    }

    if(inbody.value.trim() === "")
    {
        inbodyError.innerHTML="Number of Inbody Sessions is Required";
        isValid=false;
    }

    if(ptSession.value.trim() === "")
    {
        ptSessionError.innerHTML="Number of PT sessions is Required";
        isValid=false;
    }

    if(price.value.trim() === "")
    {
        priceError.innerHTML="Price is Required";
        isValid=false;
    }

    return isValid;



}
