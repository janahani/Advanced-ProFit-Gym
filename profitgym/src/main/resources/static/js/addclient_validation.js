document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('add-client');
    if (form) {
        form.addEventListener('submit', function(event) {
            const isValid = validateForm();
            if (!isValid) {
                event.preventDefault();
            }
        });
    }

    function validateForm() {
        var firstNameInput = document.getElementById("firstName");
        var lastNameInput = document.getElementById("lastName");
        var ageInput = document.getElementById("age");
        var phoneNumberInput = document.getElementById("phoneNumber");
        var weightInput = document.getElementById("weight");
        var heightInput = document.getElementById("height");
        var genderInput = document.getElementById("gender");
        var emailInput = document.getElementById("email");

        var isValid = true;

        if (!firstNameInput.value.trim()) {
            document.getElementById("fname-error").textContent = "Please enter first name.";
            isValid = false;
        } else {
            document.getElementById("fname-error").textContent = "";
        }

        if (!lastNameInput.value.trim()) {
            document.getElementById("lname-error").textContent = "Please enter last name.";
            isValid = false;
        } else {
            document.getElementById("lname-error").textContent = "";
        }

        if (!ageInput.value.trim()) {
            document.getElementById("age-error").textContent = "Please enter age.";
            isValid = false;
        } else {
            document.getElementById("age-error").textContent = "";
        }

        if (!phoneNumberInput.value.trim()) {
            document.getElementById("phoneno-error").textContent = "Please enter phone number.";
            isValid = false;
        } else {
            document.getElementById("phoneno-error").textContent = "";
        }

        if (!weightInput.value.trim()) {
        }

        if (!heightInput.value.trim()) {
        }

        if (!genderInput.value.trim()) {
        }

        if (!emailInput.value.trim()) {
            document.getElementById("email-error").textContent = "Please enter email.";
            isValid = false;
        } else if (!isValidEmail(emailInput.value.trim())) {
            document.getElementById("email-error").textContent = "Please enter a valid email.";
            isValid = false;
        } else {
            document.getElementById("email-error").textContent = "";
        }

        return isValid;
    }

    function isValidEmail(email) {
        return /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email);
    }
});