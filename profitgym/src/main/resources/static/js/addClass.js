function validateForm() {
        var name = document.getElementById("name").value.trim();
        var description = document.getElementById("description").value.trim();
        var file = document.getElementById("myFile").value;
        var days = document.querySelectorAll('input[name="week-days[]"]:checked');
    
        var nameError = document.getElementById("name-error");
        var descriptionError = document.getElementById("description-error");
        var fileError = document.getElementById("file-error");
        var daysError = document.getElementById("days-error");
    
        nameError.innerHTML = "";
        descriptionError.innerHTML = "";
        fileError.innerHTML = "";
        daysError.innerHTML = "";
    
        var isValid = true;
    
        // Validate name
        if (name === "") {
            nameError.innerHTML = "Please enter a Class Name";
            isValid = false;
        }
    
        // Validate description
        if (description === "") {
            descriptionError.innerHTML = "Please enter a Description";
            isValid = false;
        }
    
        // Validate file
        if (file === "") {
            fileError.innerHTML = "Please select a File";
            isValid = false;
        }
    
        // Validate days
        if (days.length === 0) {
            daysError.innerHTML = "Please select at least one day";
            isValid = false;
        }
    
        return isValid;
    }
    


