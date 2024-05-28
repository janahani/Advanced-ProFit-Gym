
    function validateForm() {
        var firstName = document.getElementById("firstName");
        var lastName = document.getElementById("lastName");
        var age = document.getElementById("age");
        var phoneNumber = document.getElementById("phoneNumber");
        var gender = document.getElementById("gender");
        var email = document.getElementById("email");
        var isValid = true;
  
        // Clear error messages
        var errorMessages = document.querySelectorAll('.error-message');
        errorMessages.forEach(function (errorMessage) {
          errorMessage.textContent = '';
        });
  
        if (firstName.value === '') {
          document.getElementById("fname-error").textContent = 'Please enter a first name.';
          isValid = false;
        }
  
        if (lastName.value === '') {
          document.getElementById("lname-error").textContent = 'Please enter a last name.';
          isValid = false;
        }
  
        if (age.value === '') {
          document.getElementById("age-error").textContent = 'Please enter an age.';
          isValid = false;
        }
  
        if (phoneNumber.value === '') {
          document.getElementById("phoneno-error").textContent = 'Please enter a phone number.';
          isValid = false;
        }
  
        if (gender.value === '') {
          document.getElementById("gender-error").textContent = 'Please select a gender.';
          isValid = false;
        }
  
        if (email.value === '') {
          document.getElementById("email-error").textContent = 'Please enter an email.';
          isValid = false;
        }
  
        return isValid;
    }

