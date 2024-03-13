document.getElementById("update-profile-button").addEventListener("click", function(event) {
    if (!validateForm()) {
        event.preventDefault();
    }
});

document.getElementById("delete-profile-button").addEventListener("click", function(event) {
    if (!confirm("Are you sure you want to delete your profile?")) {
        event.preventDefault();
    }
});

function validateForm() {
    var isValid = true;
    var firstName = document.getElementById("firstname").value.trim();
    var lastName = document.getElementById("lastname").value.trim();
    var phoneNumber = document.getElementById("phone").value.trim();
    var email = document.getElementById("email").value.trim();
    var password = document.getElementById("password").value.trim();
    var confirmPassword = document.getElementById("confirm-password").value.trim();
    var fnameError = document.getElementById("fname-error");
    var lnameError = document.getElementById("lname-error");
    var phoneError = document.getElementById("phoneno-error");
    var emailError = document.getElementById("email-error");
    var passwordError = document.getElementById("password-error");
    var confirmPasswordError = document.getElementById("confirmpassword-error");
    
    // Clear previous error messages
    fnameError.innerHTML = "";
    lnameError.innerHTML = "";
    phoneError.innerHTML = "";
    emailError.innerHTML = "";
    passwordError.innerHTML="";
    confirmPasswordError.innerHTML="";

    // Validate first name
    if (firstName === "") {
        fnameError.innerHTML = "First name is required";
        isValid = false;
    }

    // Validate last name
    if (lastName === "") {
        lnameError.innerHTML = "Last name is required";
        isValid = false;
    }

    // Validate phone number
    if (phoneNumber === "") {
        phoneError.innerHTML = "Phone number is required";
        isValid = false;
    } else if (!/^\d{11}$/.test(phoneNumber)) {
        phoneError.innerHTML = "Phone number must be 11 digits";
        isValid = false;
    }

    // Validate email
    if (email === "") {
        emailError.innerHTML = "Email is required";
        isValid = false;
    } else if (!/^\S+@\S+\.\S+$/.test(email)) {
        emailError.innerHTML = "Invalid email address";
        isValid = false;
    }

    if (password === "") {

        passwordError.innerHTML = "Password is required";
        isValid = false;
    }

    if (confirmPassword === "") {
        confirmPasswordError.innerHTML = "Confirm password is required";
        isValid = false;
    }
    // Validate password
    if (password !== confirmPassword) {
        confirmPasswordError.innerHTML = "Passwords do not match";
        isValid = false;
    }

    return isValid;
}