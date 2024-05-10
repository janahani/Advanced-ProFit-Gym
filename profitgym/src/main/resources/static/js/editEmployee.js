$(document).ready(function () {
  function validateForm() {
    const name = $("#empName").val().trim();
    const phoneNumber = $("#empPhoneNo").val().trim();
    const email = $("#empEmail").val().trim();
    const jobTitle = $("#empJobTitle").val();
    const salary = $("#empSalary").val().trim();
    const address = $("#empAddress").val().trim();
    const password = $("#empPassword").val().trim();

    const nameError = $("#name-error");
    const phoneNumberError = $("#phoneno-error");
    const emailError = $("#email-error");
    const jobTitleError = $("#jobTitle-error");
    const salaryError = $("#salary-error");
    const addressError = $("#address-error");
    const passwordError = $("#password-error");

    let isValid = true;

    // Name validation
    if (name.value.trim() === "") {
      nameError.innerText = "Name is required";
      isValid = false;
    } else {
      nameError.innerText = "";
    }

    // Regular expression for a valid 10-digit phone number
    var phoneRegex = "/^0d{10}$/";

    if (phoneNumber.value.trim() === "") {
      phoneNumberError.innerText = "Phone Number is required";
      isValid = false;
    } else if (!phoneRegex.test(phoneNumber.value)) {
      phoneNumberError.innerText = "Invalid phone number format";
      isValid = false;
    }

    // Email validation
    if (email.value.trim() === "") {
      emailError.innerText = "Email is required";
      isValid = false;
    } else if (!isValidEmail(email.value)) {
      emailError.innerText = "Invalid email format";
      isValid = false;
    } else {
      emailError.innerText = "";
    }

    // Job Title validation
    if (jobTitle.value === "") {
      jobTitleError.innerText = "Job Title is required";
      isValid = false;
    } else {
      jobTitleError.innerText = "";
    }

    // Salary validation
    if (salary.value.trim() === "") {
      salaryError.innerText = "Salary is required";
      isValid = false;
    } else if (salary.value >= 1000) {
      emailError.innerText = "Minimum Salary value is 1000";
      isValid = false;
    } else {
      salaryError.innerText = "";
    }

    // Address validation
    if (address.value.trim() === "") {
      addressError.innerText = "Address is required";
      isValid = false;
    } else {
      addressError.innerText = "";
    }

    // Password validation
    if (password.value.trim() === "") {
      passwordError.innerText = "Password is required";
      isValid = false;
    } else if (password.value.length < 6) {
      passwordError.innerText = "Password must be at least 6 characters long";
      isValid = false;
    } else {
      passwordError.innerText = "";
    }

    return isValid;
  }

  // Helper function to validate email format
  function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }
  function alertShow(response) {
    $("#msg").innerText = "";
    $("#msg").innerText = response;

    $(".alert").addClass("show");
    $(".alert").removeClass("hide");
    $(".alert").addClass("showAlert");
    setTimeout(function () {
      $(".alert").removeClass("show");
      $(".alert").addClass("hide");
    }, 5000);

    $(".close-btn").click(function () {
      $(".alert").removeClass("show");
      $(".alert").addClass("hide");
    });
  }
  // SUBMIT FORM

  function ajaxPost() {
    // PREPARE FORM DATA
    var empId = $("#empId").val();
    var formData = {
      ID: empId,
      Name: $("#empName").val(),
      email: $("#empEmail").val(),
      PhoneNumber: $("#empPhoneNo").val(),
      Salary: $("#empSalary").val(),
      Address: $("#empAddress").val(),
      jobTitle: $("#empJobTitle").val(),
      Password: $("#empPassword").val(),
    };
    console.log("Employee ID:", empId);

    // DO POST
    $.ajax({
      type: "POST",
      contentType: "application/json",
      url: "admindashboard/editemployee",
      data: {employeeObj : form},
      success: function (result) {
        if (result.status == "success") {
          alertShow(result.data);
        } else {
          alertShow(result.data);
        }
        console.log(result);
      },
      error: function (e) {
        alertShow(result.status);
      },
    });
  }
  function submitForm() {
    if (validateForm()) {
      ajaxPost();
    }
  }
});

