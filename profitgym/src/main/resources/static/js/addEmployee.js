function clearInputFields() {
  var inputFields = document.querySelectorAll('.add-user-input');
  inputFields.forEach(function(input) {
      input.value = '';
  });
}

window.addEventListener('DOMContentLoaded', (event) => {
  clearInputFields();
});

function validateForm() {
    const name = document.getElementsByName('name').value.trim();
    const phoneNumber = document.getElementsByName('phoneNumber');
    const email = document.getElementsByName('email');
    const jobTitle = document.getElementById('jobs').value;
    const salary = document.getElementsByName('salary');
    const address = document.getElementsByName('address');
    const password = document.getElementsByName('password');
  
    const nameError = document.getElementById('name-error');
    const phoneNumberError = document.getElementById('phoneno-error');
    const emailError = document.getElementById('email-error');
    const jobTitleError = document.getElementById('jobTitle-error');
    const salaryError = document.getElementById('salary-error');
    const addressError = document.getElementById('address-error');
    const passwordError = document.getElementById('password-error');
  
    let isValid = true;
  
    // Name validation
    if (name.value.trim() === '') {
      nameError.innerText = 'Name is required';
      isValid = false;
    } else {
      nameError.innerText = '';
    }

    // Regular expression for a valid 10-digit phone number
    var phoneRegex ='/^0\d{10}$/';

     if (phoneNumber.value.trim() === '') {
        phoneNumberError.innerText = 'Phone Number is required';
         isValid = false;
    } else if (!phoneRegex.test(phoneNumber.value)) {
        phoneNumberError.innerText = 'Invalid phone number format';
         isValid = false;
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
  
    // Job Title validation
    if (jobTitle.value === '') {
      jobTitleError.innerText = 'Job Title is required';
      isValid = false;
    } else {
      jobTitleError.innerText = '';
    }
  
    // Salary validation
    if (salary.value.trim() === '' ) {
      salaryError.innerText = 'Salary is required';
      isValid = false;
    } else if ((salary.value)>=1000) {
        emailError.innerText = 'Minimum Salary value is 1000';
        isValid = false;
      }else {
      salaryError.innerText = '';
    }
  
    // Address validation
    if (address.value.trim() === '') {
      addressError.innerText = 'Address is required';
      isValid = false;
    } else {
      addressError.innerText = '';
    }

    
        // Password validation
        if(password.value.trim() === ''){
            passwordError.innerText = 'Password is required';
            isValid = false;
        } else if (password.value.length < 6) {
            passwordError.innerText = 'Password must be at least 6 characters long';
            isValid = false;
        } else {
            passwordError.innerText = '';
        }
  
    return isValid;
  }
  
  // Helper function to validate email format
  function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }