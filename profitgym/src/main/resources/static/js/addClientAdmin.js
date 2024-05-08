<script>
    function validateForm() {
        // Get form inputs
        var firstName = document.getElementById('firstName').value;
        var lastName = document.getElementById('lastName').value;
        var age = document.getElementById('age').value;
        var phoneNumber = document.getElementById('phoneNumber').value;
        var email = document.getElementById('email').value;

        // Validate first name
        if (firstName === '') {
            alert('Please enter first name');
            return false;
        }

        // Validate last name
        if (lastName === '') {
            alert('Please enter last name');
            return false;
        }

        // Validate age
        if (age === '' || isNaN(age)) {
            alert('Please enter a valid age')
            return false;
        }

        // Validate phone number
        if (phoneNumber === '' || isNaN(phoneNumber)) {
            alert('Please enter a valid phone number');
            return false;
        }

        // Validate email
        var emailPattern = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;
        if (!email.match(emailPattern)) {
            alert('Please enter a valid email address');
            return false;
        }

        // If all validations pass, submit the form
        return true;
    }
</script>
