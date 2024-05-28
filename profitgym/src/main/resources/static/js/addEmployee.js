function validateAndSubmitForm() {
  var nameInput = document.getElementById("name");
  var emailInput = document.getElementById("email");
  var phoneInput = document.getElementById("phoneno");
  var jobTitleInput = document.getElementById("jobTitle");
  var salaryInput = document.getElementById("salary");
  var addressInput = document.getElementById("address");

  var isValid = true;

  // Validate name
  if (!nameInput.value.trim()) {
    document.getElementById("name-error").textContent = "Please enter a name.";
    isValid = false;
  } else {
    document.getElementById("name-error").textContent = "";
  }

  // Validate email
  if (!emailInput.value.trim()) {
    document.getElementById("email-error").textContent = "Please enter an email.";
    isValid = false;
  } else if (!isValidEmail(emailInput.value.trim())) {
    document.getElementById("email-error").textContent = "Please enter a valid email.";
    isValid = false;
  } else {
    document.getElementById("email-error").textContent = "";
  }

  // Validate phone number
  if (!phoneInput.value.trim()) {
    document.getElementById("phoneno-error").textContent = "Please enter a phone number.";
    isValid = false;
  } else if (!isValidPhoneNumber(phoneInput.value.trim())) {
    document.getElementById("phoneno-error").textContent = "Please enter a valid phone number.";
    isValid = false;
  } else {
    document.getElementById("phoneno-error").textContent = "";
  }

  // Validate job title
  if (!jobTitleInput.value) {
    document.getElementById("jobTitle-error").textContent = "Please select a job title.";
    isValid = false;
  } else {
    document.getElementById("jobTitle-error").textContent = "";
  }

  // Validate salary
  if (!salaryInput.value.trim()) {
    document.getElementById("salary-error").textContent = "Please enter a salary.";
    isValid = false;
  } else if (isNaN(parseFloat(salaryInput.value.trim()))) {
    document.getElementById("salary-error").textContent = "Please enter a valid number for the salary.";
    isValid = false;
  } else {
    document.getElementById("salary-error").textContent = "";
  }

  // Validate address
  if (!addressInput.value.trim()) {
    document.getElementById("address-error").textContent = "Please enter an address.";
    isValid = false;
  } else {
    document.getElementById("address-error").textContent = "";
  }

  return isValid;
}

function isValidEmail(email) {
  return /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email);
}

function isValidPhoneNumber(phoneNumber) {
  return /^\d{10}$/.test(phoneNumber);
}