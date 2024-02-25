const freezeButton = document.getElementById("freeze-button");
const freezeWeeksInput = document.getElementById("freeze-weeks");
let remainingFreezeAttempts; // To be fetched from the database later on

const modal = document.getElementById("myModal");
const confirmationText = document.getElementById("confirmation-text");
const confirmButton = document.getElementById("confirm-button");
const cancelButton = document.getElementById("cancel-button");
const closeBtn = document.querySelector(".close-popup");
const errorMessage = document.getElementById("error-message");
const modalMessage = document.getElementById("modal-message");

function showModal(message) {
    confirmationText.textContent = message;
    modal.style.display = "block";
    confirmButton.style.display = "block";
    cancelButton.style.display = "block";
    closeBtn.style.display = "block";
    modalMessage.style.display = "none";
}

function resetModal() {
    modal.style.display = "none";
    confirmationText.textContent = '';
    confirmButton.style.display = "none";
    cancelButton.style.display = "none";
    closeBtn.style.display = "none";
    modalMessage.style.display = "none";
}

let isFrozen = false; // Track if the freeze button is already pressed

freezeButton.addEventListener("click", function() {
    const freezeWeeks = freezeWeeksInput.value;

    if (isFrozen) {
        // Show modal message instead of the whole modal
        showModal("Your request is submitted, and you will receive a confirmation email.");
        confirmButton.style.display = "none";
        cancelButton.style.display = "none";
    } else if (freezeWeeks !== '' && parseInt(freezeWeeks) >= 1 && parseInt(freezeWeeks) <= remainingFreezeAttempts) {
        showModal(`Are you sure you want to freeze ${freezeWeeks} weeks?`);
        isFrozen = true; // Set the freeze button state to pressed
    } else {
        errorMessage.style.display = "block";
        modal.style.display = "none";
    }
});

closeBtn.addEventListener("click", function() {
    resetModal();
});

confirmButton.addEventListener("click", function() {
    if (confirmationText.textContent) {
        resetModal();

        // sends a request to the server to handle the freeze
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "reqfreeze.php", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                console.log(xhr.responseText);
            }
        };

        xhr.send("action=freezeMembership&freezeWeeks=" + freezeWeeks);

        modal.style.display = "block";
        modalMessage.style.display = "block";
        modalMessage.textContent = "Your request is submitted, and you will receive a confirmation email.";

        setTimeout(function() {
            modal.style.display = "none";
            modalMessage.style.display = "none";
        }, 2000); // model will hide automatically after 2 seconds
    }
});

cancelButton.addEventListener("click", function() {
    if (confirmationText.textContent) {
        resetModal();

        modal.style.display = "block";
        modalMessage.style.display = "block";
        modalMessage.textContent = "No request submitted.";

        setTimeout(function() {
            modal.style.display = "none";
            modalMessage.style.display = "none";
        }, 2000);
    }
});

function sendFreezeRequest(freezeWeeks) {
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "reqfreeze.php", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
        }
    };

    xhr.send("action=freezeMembership&freezeWeeks=" + freezeWeeks);
}

// Fetch initial freeze info when the page loads
document.addEventListener("DOMContentLoaded", function () {
    fetchRemainingFreezeAttempts();
});

function fetchRemainingFreezeAttempts() {
    fetch("reqfreeze.php")
        .then(response => response.json())
        .then(data => {
            remainingFreezeAttempts = data.remainingFreezeAttempts;
            document.getElementById("actual-rem").textContent = `${remainingFreezeAttempts} Weeks Out of ${remainingFreezeAttempts} Left`;
        })
        .catch(error => console.error("Error fetching freeze info:", error));
}