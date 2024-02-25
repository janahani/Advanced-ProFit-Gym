function validateForm() {
    // Get form elements
    var packageName = document.getElementById("package-name");
    var minMonths = document.getElementById("package-min-months");
    var sessions = document.getElementById("package-sessions");
    var packagePrice = document.getElementById("package-price");

    // Get error elements
    var packageNameError = document.getElementById("package-name-error");
    var minMonthsError = document.getElementById("package-min-months-error");
    var sessionsError = document.getElementById("package-sessions-error");
    var packagePriceError = document.getElementById("package-price-error");

    // Reset error messages
    packageNameError.innerHTML = "";
    minMonthsError.innerHTML = "";
    sessionsError.innerHTML = "";
    packagePriceError.innerHTML = "";

    let isValid = true;

    // Validate package name
    if (packageName.value.trim() === "") {
        packageNameError.innerHTML = "PT Package Name is required";
        isValid = false;
    }

    // Validate minimum membership months
    if (minMonths.value.trim() === "" ) {
        minMonthsError.innerHTML = "Minimum Membership Months is required";
        isValid = false;
    }else if(parseInt(minMonths.value) <= 0){
        minMonthsError.innerHTML = "Minimum Membership Months must be a positive number";
        isValid = false;
    }

    // Validate number of sessions
    if (sessions.value.trim() === "") {
        sessionsError.innerHTML = "Number of Sessions is Required";
        isValid = false;
    }else if(parseInt(sessions.value) <= 0){
        sessionsError.innerHTML = "Number of Sessions must be a positive number";
        isValid = false;
    }

    // Validate package price
    if (packagePrice.value.trim() === "") {
        packagePriceError.innerHTML = "PT Package Price is required";
        isValid = false;
    }else if(parseFloat(packagePrice.value) <= 0){
        packagePriceError.innerHTML = "Package Price must be a positive number";
        isValid = false;
    }

    return isValid;
}