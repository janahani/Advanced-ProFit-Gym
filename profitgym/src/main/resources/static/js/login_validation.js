    function validateForm() {
        const email = document.getElementById('email');
        const password = document.getElementById('password');

        const emailError = document.getElementById('email-error');
        const passwordError = document.getElementById('password-error');

        let isValid = true;

        var errorElements = document.querySelectorAll('.error-message');
        errorElements.forEach(function(element) {
            element.innerText = "";
        });

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


