document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('login-form');
    form.addEventListener('submit', function(event) {
        const isValid = validateForm();
        if (!isValid) {
            event.preventDefault();
        }
    });

    function validateForm() {
        const email = document.getElementById('email');
        const password = document.getElementById('password');

        const emailError = document.getElementById('email-error');
        const passwordError = document.getElementById('password-error');

        let isValid = true;

        // Email validation
        if (email.value.trim() === '') {
            emailError.innerText = 'Email is required';
            isValid = false;
        }else {
            emailError.innerText = '';
        }

        // Password validation
        if(password.value.trim() === ''){
            passwordError.innerText = 'Password is required';
            isValid = false;
        } else {
            passwordError.innerText = '';
        }

        return isValid;
    }
});

