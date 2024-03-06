function validateForm() {
    var fname = document.getElementById("fname").value.trim();
    var lname = document.getElementById("lname").value.trim();
    var age = parseInt(document.getElementById("age").value.trim());
    var gender = document.querySelector('input[name="gender"]:checked');
    var phone = document.getElementById("phone").value.trim();
    var weight = parseFloat(document.getElementById("weight").value.trim());
    var height = parseFloat(document.getElementById("height").value.trim());
    var email = document.getElementById("email").value.trim();
    var password = document.getElementById("password").value.trim();
    var i=true;
    // Clear existing error messages
    var errorElements = document.querySelectorAll('.error-message');
    errorElements.forEach(function(element) {
        element.innerText = "";
    });
    
    // Validate first name
    if (fname === "") {
        document.getElementById("fname-error").innerText = "Please enter your first name";
        i= false;
    }

    // Validate last name
    if (lname === "") {
        document.getElementById("lname-error").innerText = "Please enter your last name";
        i=  false;
    }

    // Validate age
    if (isNaN(age) || age < 16 || age > 100) {
        document.getElementById("age-error").innerText = "Please enter a valid age between 16 and 100";
        i=  false;
    }

    // Validate gender
    if (!gender) {
        document.getElementById("gender-error").innerText = "Please select your gender";
        i=  false;
    }

    // Validate phone number
    if (phone === "" || isNaN(phone)) {
        document.getElementById("phoneno-error").innerText = "Please enter a valid phone number";
        i=  false;
    }

    // Validate weight
    if (isNaN(weight) || weight < 40 || weight > 250) {
        document.getElementById("weight-error").innerText = "Please enter a valid weight between 40 and 250";
        i=  false;
    }

    // Validate height
    if (isNaN(height) || height < 140 || height > 250) {
        document.getElementById("height-error").innerText = "Please enter a valid height between 140 and 250";
        i=  false;
    }

    // Validate email
    var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!email.match(emailPattern)) {
        document.getElementById("email-error").innerText = "Please enter a valid email address";
        i=  false;
    }

    // Validate password
    if (password === "") {
        document.getElementById("password-error").innerText = "Please enter a password";
        i=  false;
    }

return i;
}
