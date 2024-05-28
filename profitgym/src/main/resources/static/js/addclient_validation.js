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
        const fname = document.getElementById('firstName');
        const lname = document.getElementById('lastName');
        const age = document.getElementById('age');
        const gender = document.querySelector('select[name="gender"]');
        const email = document.getElementById('email');
        const phoneno = document.getElementById('phoneNumber');

        const fnameError = document.getElementById('fname-error');
        const lnameError = document.getElementById('lname-error');
        const ageError = document.getElementById('age-error');
        const genderError = document.getElementById('gender-error');
        const emailError = document.getElementById('email-error');
        const phonenoError = document.getElementById('phoneno-error');

        let isValid = true;

        // First Name validation
        if (fname.value.trim() === '') {
            fnameError.innerText = 'First Name is required';
            isValid = false;
        } else {
            fnameError.innerText = '';
        }

        // Last Name validation
        if (lname.value.trim() === '') {
            lnameError.innerText = 'Last Name is required';
            isValid = false;
        } else {
            lnameError.innerText = '';
        }

        // Age validation
        if (age.value.trim() === '' || isNaN(age.value) || age.value <= 0) {
            ageError.innerText = 'Age is required and must be a positive number';
            isValid = false;
        } else if (age.value < 16 || age.value > 100) {
            ageError.innerText = 'Age must be between 16 and 100';
            isValid = false;
        } else {
            ageError.innerText = '';
        }

        // Gender validation
        if (gender.value.trim() === '') {
            genderError.innerText = 'Gender is required';
            isValid = false;
        } else {
            genderError.innerText = '';
        }

        // Email validation
        if (email.value.trim() === '') {
            emailError.innerText = 'Email is required';
            isValid = false;
        } else if (!isValidEmail(email.value)) {
            emailError.innerText = 'Invalid email format';
            isValid = false;
        } else {
            emailError.innerText = '';
        }

        // Phone Number validation
        const phoneRegex = /^0\d{10}$/;
        if (phoneno.value.trim() === '') {
            phonenoError.innerText = 'Phone Number is required';
            isValid = false;
        } else if (!phoneRegex.test(phoneno.value)) {
            phonenoError.innerText = 'Invalid phone number format';
            isValid = false;
        } else {
            phonenoError.innerText = '';
        }

        return isValid;
    }

    // Helper function to validate email format
    function isValidEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }
});
