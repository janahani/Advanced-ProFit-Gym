function validateForm() {
    var classID = document.getElementsByName("ClassID")[0].value;
    var coachID = document.getElementsByName("CoachID")[0].value;
    var startTime = document.getElementsByName("startTime")[0].value;
    var endTime = document.getElementsByName("endTime")[0].value;
    var isFree = document.getElementsByName("isFree")[0].value;
    var price = document.getElementsByName("price")[0].value;
    var numOfAttendants = document.getElementsByName("no-of-attendants")[0].value;
    var classDay = document.getElementsByName("ClassDay")[0].value;

    var classIDError = document.getElementById("classID-error");
    var coachIDError = document.getElementById("coachID-error");
    var startTimeError = document.getElementById("startTime-error");
    var endTimeError = document.getElementById("endTime-error");
    var priceError = document.getElementById("price-error");
    var numOfAttendantsError = document.getElementById("numOfAttendants-error");
    var classDayError = document.getElementById("classDay-error");
    classIDError.innerHTML = "";
    coachIDError.innerHTML = "";
    startTimeError.innerHTML = "";
    endTimeError.innerHTML = "";
    priceError.innerHTML = "";
    numOfAttendantsError.innerHTML = "";
    classDayError.innerHTML = "";

    var isValid = true;

    // Validate Class
    if (classID === "") {
        classIDError.innerHTML = "Please select a Class";
        isValid = false;
    }

    // Validate Coach
    if (coachID === "") {
        coachIDError.innerHTML = "Please select a Coach";
        isValid = false;
    }

    // Validate Start Time
    if (startTime === "") {
        startTimeError.innerHTML = "Please enter a Start Time";
        isValid = false;
    }

    // Validate End Time
    if (endTime === "") {
        endTimeError.innerHTML = "Please enter an End Time";
        isValid = false;
    }

    // Validate Price if not free
    if (isFree === "no" && price === "") {
        priceError.innerHTML = "Please enter a Price";
        isValid = false;
    }

    // Validate Number of Attendants
    if (numOfAttendants === "") {
        numOfAttendantsError.innerHTML = "Please enter the Number of Attendants";
        isValid = false;
    }

    // Validate Class Day
    if (classDay === "") {
        classDayError.innerHTML = "Please select a Class Day";
        isValid = false;
    }

    return isValid;
}
