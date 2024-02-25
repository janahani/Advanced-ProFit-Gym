const form = document.getElementById("profile-settings-form");
const username = document.getElementById("username");
const email = document.getElementById("email");
const phoneno = document.getElementById("phoneno");
const password = document.getElementById("password");
const confirmPassword = document.getElementById("confirm-password");
const usernameError = document.getElementById("username-error");
const emailError = document.getElementById("email-error");
const phonenoError = document.getElementById("phoneno-error");
const passwordError = document.getElementById("password-error");
const confpasserror = document.getElementById("confirm-password-error");

const currentEmail = "example@example.com";
const currentPhoneNo = "01234567890";
const currentPassword = "InitialPassword123";

form.onsubmit = function () {
  let isValid = true;

  if (email.value.trim() !== currentEmail && email.value.trim() !== "") {
    if (!email.value.includes("@")) {
      emailError.textContent = "Invalid email address";
      isValid = false;
    } else if (
      !/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/.test(email.value)
    ) {
      emailError.textContent = "Invalid email format";
      isValid = false;
    }
  }

  if (phoneno.value.trim() !== currentPhoneNo && phoneno.value.trim() !== "") {
    if (!/^\d{11}$/.test(phoneno.value.trim())) {
      phonenoError.textContent =
        "Phone number must be 11 characters & only contain numbers";
      isValid = false;
    } else if (!/^0(12|11|10|15)\d{8}$/.test(phoneno.value.trim())) {
      phonenoError.textContent =
        "Phone number must start with '0' followed by '12', '11', '10', or '15'";
      isValid = false;
    } else {
      currentPhoneNo = phoneno.value.trim(); // lehad ma neaml database
      phoneno.placeholder = currentPhoneNo;
    }
  } else if (phoneno.value.trim() === "" && isValid) {
    phonenoError.textContent = "";
  }

  if (password.value !== currentPassword && password.value.trim() !== "") {
    if (password.value.length < 8) {
      passwordError.textContent = "Password must be at least 8 characters long";
      isValid = false;
    } else if (!/^(?=.*[A-Z])(?=.*\d)/.test(password.value)) {
      passwordError.textContent =
        "Password must contain at least one uppercase letter and a number";
      isValid = false;
    }
  }

  if (isValid) {
    if (password.value !== confirmPassword.value) {
      confpasserror.textContent = "Passwords do not match";
      isValid = false;
    }
  }

  if (!isValid) {
    return false; // to prevent form submission
  }
};
